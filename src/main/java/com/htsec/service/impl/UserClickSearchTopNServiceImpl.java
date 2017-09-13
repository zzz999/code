package com.htsec.service.impl;

import com.google.gson.Gson;
import com.htsec.service.UserClickSearchTopNService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : 功能统计第一页结果集MVC业务类.
 * Date : 2017/3/30 10:01
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Service
public class UserClickSearchTopNServiceImpl implements UserClickSearchTopNService {
    static Gson gson = new Gson();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    static Logger logger = Logger.getLogger(UserClickSearchTopNServiceImpl.class);
    static final String queryTotal = "select count(1) as count, sum(`clickCount`) as summation from `ubs_search_topn_basic` where`clickKey` like :clickKey and  `clickType` = :clickType and `timestamp` between :startDate and :endDate";
    static final String sql = "select clickKey, sum(`clickCount`) as count, sum(`clickCount`) / :summation * 100 as percent from `ubs_search_topn_basic` where `clickKey` like :clickKey and `clickType` = :clickType and `timestamp` between :startDate and :endDate group by `clickKey` order by sum(`clickCount`) desc limit :offset, :fetchSize";

    /**
     * 调用实现定义的mysql存储过程并获取热搜结果.
     *
     * @return 热搜结果
     */
    @Override
    public String callUbsSearchTopNStat() throws Exception {
        Map<String, Object> out = this.simpleJdbcCall.execute();
        String json = "{\"ubsSearchOverview\":" + gson.toJson(out.get("#result-set-1"))
                + ", \"ubsSearchTrend\":" + gson.toJson(out.get("#result-set-2"))
                + ", \"ubsSearchTopN\":" + gson.toJson(out.get("#result-set-3")) + "}";

        return json;
    }

    @Override
    public String callUbsSearchFuncDetail(String name, Date start, Date end, long offset, long fetchSize) throws Exception {
        return callUbsSearchDetailByClickType(name, "CFSearch", start, end, offset, fetchSize);
    }

    @Override
    public String callUbsSearchStockDetail(String name, Date start, Date end, long offset, long fetchSize) throws Exception {
        return callUbsSearchDetailByClickType(name, "CSSearch", start, end, offset, fetchSize);
    }

    /**
     * 通过类型查询详细数据.
     *
     * @param clickType
     * @param start
     * @param end
     * @param offset
     * @param fetchSize
     * @return
     */
    private String callUbsSearchDetailByClickType(String name, String clickType, Date start, Date end, long offset, long fetchSize) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("clickType", clickType);
        namedParameters.put("startDate", start);
        namedParameters.put("endDate", end);
        namedParameters.put("clickKey", "%" + (StringUtils.isNotEmpty(name) && StringUtils.isNotBlank(name) ? name : "") + "%");

        Map<String, Object> total = this.namedParameterJdbcTemplate.queryForMap(queryTotal, namedParameters);
        Object count = total.get("count");

        if (count == null || String.valueOf(count) == "0") return "";

        namedParameters.put("summation", total.get("summation"));
        namedParameters.put("offset", offset);
        namedParameters.put("fetchSize", fetchSize);

        List<Map<String, Object>> retVal = this.namedParameterJdbcTemplate.queryForList(sql, namedParameters);

        return "{\"totalnum\":" + count + ", \"datalist\":" + gson.toJson(retVal) + "}";
    }

    @Autowired
    public void setUbsDataSource(DataSource ubsDataSource) {
        this.jdbcTemplate = new JdbcTemplate(ubsDataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ubsDataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("ubs_search_topn_stat");
    }
}