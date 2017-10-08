package com.htsec.controller;


import com.htsec.commons.utils.CodeHelper;
import com.htsec.security.AESUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 收益构成
 */
@Controller
public class UserIncomeComposite {
    private static final Logger logger = Logger.getLogger(UserIncomeComposite.class);
    private static AESUtil aes = new AESUtil();


    @RequestMapping(value = "/incomeComposite", method = RequestMethod.GET)
    public void userincomeComposite(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        String requestQ= null;
        try {
            requestQ = aes.decrypt(requestQueryString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("stock",10000.00);
        result.put("financing",-1000.00);
        result.put("fund",5000.01);

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
