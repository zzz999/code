package com.htsec.controller;

import com.htsec.Student.init.bean.CompanyInfo;
import com.htsec.Student.process.TeacherInitManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class StudentQueryCompanyInfoController {
    private static final Logger logger = Logger.getLogger(StudentQueryCompanyInfoController.class);
    @RequestMapping(value = "/queryCompanyInfo", method = RequestMethod.GET)
    public void queryCompanyInfo(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();

        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        JSONObject result;
        CompanyInfo info = TeacherInitManager.getCompanyInfoHashMap().get(requestJson.getString("time"));
        if(info!=null){
            result = JSONObject.fromObject(info);
        }else {
            result = new JSONObject();
        }
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/queryCompanyNames", method = RequestMethod.GET)
    public void queryCompanyNames(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        result.put("companyNames",TeacherInitManager.getCompanyNames());
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
