package com.htsec.service.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.htsec.commons.utils.SpringContextHolder;
import com.htsec.hbase.utils.HBaseUtil;
import com.htsec.service.UserClickSearchTopNService;
import com.htsec.service.dto.KV;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Description : TODO
 * Date : 2017/3/30 14:03
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
@ActiveProfiles("development")
public class UserClickSearchTopNServiceTest extends AbstractJUnit4SpringContextTests {
    List<KV> original = Lists.newArrayList();
    Gson gson = new Gson();
    @Autowired
    ApplicationContext context;
    @Autowired
    UserClickSearchTopNService searchTopNService;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Ignore
    public void callUbsSearchTopNStat() throws Exception {
        Map<String, Object> out = this.simpleJdbcCall.execute();
        System.out.println(out.get("#result-set-1"));
    }

    @Test
    @Ignore
    public void callUbsSearchFuncDetail() throws Exception {
        Date start = new Date(DateTime.parse("2017-04-23").getMillis());
        Date end = new Date(DateTime.parse("2017-04-24").getMillis());
        System.out.println(searchTopNService.callUbsSearchFuncDetail("新", start, end, 1, 10));
        System.out.println(searchTopNService.callUbsSearchStockDetail("金", start, end, 1, 10));
    }

    @Autowired
    public void setUbsDataSource(DataSource ubsDataSource) {
        this.jdbcTemplate = new JdbcTemplate(ubsDataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("ubs_search_topn_stat");
    }

}
