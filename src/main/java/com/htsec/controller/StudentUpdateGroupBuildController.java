package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class StudentUpdateGroupBuildController {
    private static final Logger logger = Logger.getLogger(StudentUpdateGroupBuildController.class);

    @RequestMapping(value = "/queryGroupInfo", method = RequestMethod.GET)
    public void updateQDJS(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        JSONObject result = new JSONObject();
        String code =requestJson.getString("code");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo==null){
            result.put("result","false");
        }else {
            if(bankInfo.getZhInfoList()==null){
                result.put("result","false");
            }
        }

    }



}
