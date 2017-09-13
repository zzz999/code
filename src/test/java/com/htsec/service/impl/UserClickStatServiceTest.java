package com.htsec.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.htsec.service.UserClickStatService;
import com.htsec.service.dto.KV;
import com.htsec.service.dto.ClickStatOverview;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

/**
 * Description : TODO
 * Date : 2017/3/30 14:03
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
@ActiveProfiles("development")
public class UserClickStatServiceTest extends AbstractJUnit4SpringContextTests {
    List<KV> original = Lists.newArrayList();
    Gson gson = new Gson();
    @Autowired
    ApplicationContext context;
    @Autowired
    UserClickStatService statService;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 30; i++) {
            original.add(new KV("test", "day", String.valueOf(i / 10), String.valueOf((long) Math.random() * 30)));
        }
    }

    @After
    public void tearDown() throws Exception {
        original = null;
    }

    @Test
    @Ignore
    public void testGroupByCombation() {
        Map<String, Long> res = original.stream().collect(groupingBy(KV::getKey, summingLong(KV::getVaule)));
        for (Map.Entry<String, Long> obj : res.entrySet()) {
            System.out.println("Key : " + obj.getKey() + ", Value : " + obj.getValue());
        }
        System.out.println("**************before sort*******************");
        groupByValueAndSort(res, true, 2);
    }

    @Test
    @Ignore
    public void testGson() {
        List<Map<String, String>> list = Lists.newArrayList();

        Map<String, String> source1 = Maps.newHashMap();
        source1.put("period", "cur");
        source1.put("clickTime", "2017-03-30");
        source1.put("cnt", "18885");

        Map<String, String> source2 = Maps.newHashMap();
        source2.put("period", "cur1");
        source2.put("clickTime", "2017-03-29");
        source2.put("cnt", "1");

        list.add(source1);
        list.add(source2);

        System.out.println(gson.toJson(list));

        ClickStatOverview pvUvStat = new ClickStatOverview();
        pvUvStat.setPvToday(20);
        pvUvStat.setPvPreday(19);
        pvUvStat.setUvToday(2);
        pvUvStat.setUvPreday(1);
        System.out.println(gson.toJson(pvUvStat));
    }

    @Test
    @Ignore
    public void findUserClickStatByUnitAndPeriod() throws Exception {
        String json = statService.findUserClickStatByUnitAndPeriod("day", 2);
        System.out.println(json);
    }

    /**
     * 对HashMap进行排序然后返回前10.
     *
     * @param original 原始Map集合
     * @param asc      是否升序排列
     * @param topN     返回前多少行?
     */
    public void groupByValueAndSort(Map<String, Long> original, boolean asc, int topN) {
        List<Map.Entry<String, Long>> list = new ArrayList<>(original.entrySet());

        Collections.sort(list, (o1, o2) -> {
            if (asc) return o1.getValue().compareTo(o2.getValue());
            else return o2.getValue().compareTo(o1.getValue());
        });

        List<Map.Entry<String, Long>> subList = list.subList(0, topN);

        for (Map.Entry<String, Long> obj : subList) {
            System.out.println("Key : " + obj.getKey() + ", Value : " + obj.getValue());
        }
    }
}
