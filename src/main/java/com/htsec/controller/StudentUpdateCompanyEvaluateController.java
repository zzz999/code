package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class StudentUpdateCompanyEvaluateController {
    private static final Logger logger = Logger.getLogger(StudentUpdateCompanyEvaluateController.class);
    @RequestMapping(value = "/updateCompanyInfo", method = RequestMethod.GET)
    public void queryCompanyInfo(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();

        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        String code =queryObj.getString("code");
        BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo ==null){
            result.put("result","false");
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        JSONArray evluate=queryObj.getJSONArray("evaluateInfo");
        for(Object obj: evluate){
            JSONObject json =(JSONObject) obj;
            String companyName=json.getString("companyName");
            String type = json.getString("type");
            bankInfo.getCompanyEvaluateInfo().put(companyName,type);

        }
        result.put("result","true");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
