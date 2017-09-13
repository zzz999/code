package com.htsec.controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.htsec.service.UserClickDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description : 功能统计-公共字典服务(比如获取版本号、渠道、一级菜单、二级菜单下拉列表).
 * Date : 2017/4/5 14:10
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Controller
public class UserClickDictionaryController {
    @Autowired
    private UserClickDictionaryService userClickDictionaryService;
    private JdbcTemplate jdbcTemplate;
    Gson gson = new Gson();

    /**
     * 获取所有版本号请求.
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/dict/versions", method = RequestMethod.GET)
    public void queryVersions(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> channels = userClickDictionaryService.queryVersions();

        String json = gson.toJson(channels);
        writeJsonStrAsResponse(json, response);
    }

    /**
     * 获取所有渠道.
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/dict/channels", method = RequestMethod.GET)
    public void queryChannels(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> channels = userClickDictionaryService.queryChannels();

        String json = gson.toJson(channels);
        writeJsonStrAsResponse(json, response);
    }



    public static String getChannels() throws IOException {
        List<Map<String, Object>> channels = new UserClickDictionaryController().jdbcTemplate.queryForList("select id as channelId, name as channelName from `ubs_dict_channel`");
        String json =new Gson().toJson(channels);

       return json;
    }


    /**
     * 获取一类菜单.
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/dict/clickType", method = RequestMethod.GET)
    public void findClickType(HttpServletResponse response) throws IOException {
        List<Object> res = Lists.newArrayList();
        List<Map<String, Object>> clickTypeList = this.jdbcTemplate.queryForList("select distinct subtypename from t_total_clicksubtype");
        for (Map<String, Object> clickType : clickTypeList) {
            res.addAll(clickType.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
        }
        writeJsonStrAsResponse(gson.toJson(res), response);
    }

    @Autowired
    public void setUbsDataSource(DataSource ubsDataSource) {
        this.jdbcTemplate = new JdbcTemplate(ubsDataSource);
    }

    /**
     * 使用json作为返回.
     *
     * @param response
     * @param json
     * @throws IOException
     */
    public void writeJsonStrAsResponse(String json, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
