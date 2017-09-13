package com.htsec.controller;

import com.htsec.service.UserRegisterService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Description : 注册用户MVC控制类.
 * Date : 2017/3/30 9:59
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Controller
public class UserRegisterController {
    @Autowired
    private UserRegisterService userRegisterService;
    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 查询热搜结果.
     */
    @RequestMapping(value = "/userRegister", method = RequestMethod.GET)
    public void userRegister(HttpServletResponse response) throws Exception {
        String json = userRegisterService.callUserRegisterStat();
        writeJsonStrAsResponse(json, response);
    }

    /**
     * 查询热搜功能详细结果.
     */
    @RequestMapping(value = "/userRegisterDetail", method = RequestMethod.GET)
    public void userRegisterDetail(@RequestParam("start") String start,
                                   @RequestParam("end") String end,
                                   @RequestParam("offset") long offset,
                                   @RequestParam("fetchSize") long fetchSize,
                                   HttpServletResponse response) throws Exception {
        Date startDate = new Date(DateTime.parse(start).withTimeAtStartOfDay().getMillis());
        Date endDate = new Date(DateTime.parse(end).withTimeAtStartOfDay().getMillis());

        String json = userRegisterService.callUserRegisterDetail(startDate, endDate, offset, fetchSize);
        writeJsonStrAsResponse(json, response);
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
