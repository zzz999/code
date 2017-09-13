package com.htsec.service.impl;

import com.google.gson.Gson;
import com.htsec.service.UserClickDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Description : TODO
 * Date : 2017/5/10 16:33
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Service
public class UserClickDictionaryServiceImpl implements UserClickDictionaryService {
    private JdbcTemplate jdbcTemplate;


    public List<Map<String, Object>> queryChannels() throws IOException {
        return this.jdbcTemplate.queryForList("select id as channelId, name as channelName from `ubs_dict_channel`");
    }

    public String queryChannelsString() throws IOException {
        String json =new Gson().toJson(this.jdbcTemplate.queryForList("select id as channelId, name as channelName from `ubs_dict_channel`"));
        return json;
    }


    public List<Map<String, Object>> queryVersions() throws IOException {
        return this.jdbcTemplate.queryForList("select t1.`name` as platformName, t2.version from `ubs_dict_platform` t1 join `ubs_dict_version` t2 on t1.id = t2.platformid");
    }

    @Autowired
    public void setUbsDataSource(DataSource ubsDataSource) {
        this.jdbcTemplate = new JdbcTemplate(ubsDataSource);
    }
}
