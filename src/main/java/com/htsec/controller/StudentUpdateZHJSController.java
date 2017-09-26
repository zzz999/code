package com.htsec.controller;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class StudentUpdateZHJSController {
    private static final Logger logger = Logger.getLogger(StudentUpdateZHJSController.class);

    @RequestMapping(value = "/updateZHJS", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String account =requestJson.getString("account");
        String zhCount =requestJson.getString("zhCount");
        JSONObject result = new JSONObject();
        result.put("result","ok");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
