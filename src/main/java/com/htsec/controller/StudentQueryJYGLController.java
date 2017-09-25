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
 * Created by bernard on 2017/9/25.
 */
@Controller
public class StudentQueryJYGLController {
    private static final Logger logger = Logger.getLogger(StudentQueryJYGLController.class);
    @RequestMapping(value = "/queryJYGL", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response){
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        JSONObject result = new JSONObject();
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
