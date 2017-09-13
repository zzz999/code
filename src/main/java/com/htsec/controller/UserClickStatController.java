package com.htsec.controller;

import com.htsec.service.UserClickStatService;
import org.apache.hadoop.hbase.exceptions.IllegalArgumentIOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Description : 功能统计第一页结果集MVC控制类.
 * Date : 2017/3/30 9:59
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Controller
public class UserClickStatController {
    @Autowired
    private UserClickStatService userClickStatService;
    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 通过传入统计维度以及周期返回结果.
     */
    @RequestMapping(value = "/clickStat", method = RequestMethod.GET)
    public void findUserClickStatByUnitAndPeriod(@RequestParam("type") String type, HttpServletResponse response) throws Exception {
        String[] params = type.split("-");
        if (params.length == 0) throw new IllegalArgumentIOException("前端传参不正确,无返回结果.");

        int period = 1;
        if (params.length > 1) {
            //如果是今天或者昨天 都是获取 前一天的环比数据 默认为1
            period = Integer.parseInt(params[1]);
        }
        String json = userClickStatService.findUserClickStatByUnitAndPeriod(params[0], period);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
