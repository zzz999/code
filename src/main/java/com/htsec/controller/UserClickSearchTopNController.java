package com.htsec.controller;

import com.htsec.service.UserClickSearchTopNService;
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
 * Description : 热搜功能MVC控制类.
 * Date : 2017/3/30 9:59
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Controller
public class UserClickSearchTopNController {
    @Autowired
    private UserClickSearchTopNService searchTopNService;
    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 查询热搜结果.
     */
    @RequestMapping(value = "/searchTopN", method = RequestMethod.GET)
    public void userClickSearchTopN(HttpServletResponse response) throws Exception {
        String json = searchTopNService.callUbsSearchTopNStat();
        writeJsonStrAsResponse(json, response);
    }

    /**
     * 查询热搜功能详细结果.
     */
    @RequestMapping(value = "/searchFuncDetail", method = RequestMethod.GET)
    public void userClickSearchFuncDetail(@RequestParam("interval") int interval,
                                          @RequestParam("offset") long offset,
                                          @RequestParam("fetchSize") long fetchSize,
                                          HttpServletResponse response) throws Exception {

        Date start = new Date(DateTime.now().minusDays(interval).withTimeAtStartOfDay().getMillis());
        Date end = new Date(DateTime.now().withTimeAtStartOfDay().getMillis());

        String json = searchTopNService.callUbsSearchFuncDetail("", start, end, offset, fetchSize);
        writeJsonStrAsResponse(json, response);
    }

    /**
     * 查询热搜股票详细结果.
     */
    @RequestMapping(value = "/searchStockDetail", method = RequestMethod.GET)
    public void userClickSearchStockDetail(@RequestParam("interval") int interval,
                                           @RequestParam("offset") long offset,
                                           @RequestParam("fetchSize") long fetchSize,
                                           HttpServletResponse response) throws Exception {

        Date start = new Date(DateTime.now().minusDays(interval).dayOfMonth().roundFloorCopy().getMillis());
        Date end = new Date(DateTime.now().dayOfMonth().roundFloorCopy().getMillis());

        String json = searchTopNService.callUbsSearchStockDetail("", start, end, offset, fetchSize);
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
