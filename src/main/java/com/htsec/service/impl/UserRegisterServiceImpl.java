package com.htsec.service.impl;

import com.google.gson.Gson;
import com.htsec.service.UserRegisterService;
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
public class UserRegisterServiceImpl implements UserRegisterService {
    static Gson gson = new Gson();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    static Logger logger = Logger.getLogger(UserRegisterServiceImpl.class);
    static final String queryTotal = "SELECT count(1) AS count FROM `ubs_register_stat` WHERE `timestamp` BETWEEN :startDate AND :endDate";
    static final String sql = "SELECT `newly_onekey`, `newly_manual`, `newly_total`, date_format(`timestamp`, '%Y-%m-%d') AS `timestamp` FROM `ubs_register_stat` WHERE `timestamp` BETWEEN :startDate AND :endDate limit :offset, :fetchSize ORDER BY `timestamp` DESC ";

    /**
     * 调用实现定义的mysql存储过程并获取热搜结果.
     *
     * @return 热搜结果
     */
    @Override
    public String callUserRegisterStat() throws Exception {
        Map<String, Object> out = this.simpleJdbcCall.execute();

        return "{\"userRegisterNewly\":" + gson.toJson(out.get("#result-set-1")) + ", \"userRegisterCumulative\":" + gson.toJson(out.get("#result-set-2")) + ", \"userRegisterOverview\":" + gson.toJson(out.get("#result-set-3")) + "}";
    }

    /**
     * 通过时间查询详细数据.
     *
     * @param start
     * @param end
     * @param offset
     * @param fetchSize
     * @return
     */
    @Override
    public String callUserRegisterDetail(Date start, Date end, long offset, long fetchSize) throws Exception {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("startDate", start);
        namedParameters.put("endDate", end);

        Map<String, Object> total = this.namedParameterJdbcTemplate.queryForMap(queryTotal, namedParameters);
        Object count = total.get("count");

        if (count == null || String.valueOf(count) == "0") return "";

        namedParameters.put("offset", offset);
        namedParameters.put("fetchSize", fetchSize);

        List<Map<String, Object>> retVal = this.namedParameterJdbcTemplate.queryForList(sql, namedParameters);

        return "{\"totalnum\":" + count + ", \"datalist\":" + gson.toJson(retVal) + "}";
    }

    @Autowired
    public void setUbsDataSource(DataSource ubsDataSource) {
        this.jdbcTemplate = new JdbcTemplate(ubsDataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ubsDataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("ubs_register_stat");
    }
}