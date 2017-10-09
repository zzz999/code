package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.QDInfo;
import com.htsec.Student.process.StudentProcessManager;
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
public class StudentQDJSController {
    private static final Logger logger = Logger.getLogger(StudentQDJSController.class);
    @RequestMapping(value = "/updateQDJS", method = RequestMethod.GET)
    public void updateQDJS(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code = requestJson.getString("code");
        JSONObject result = new JSONObject();
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo ==null){
            result.put("result","false");
        }else {
            QDInfo qdInfo=bankInfo.getQdInfo();
            if(qdInfo ==null){
                bankInfo.setQdInfo(new QDInfo());
            }
            if(requestJson.getString("netBank").equalsIgnoreCase("true")){
                bankInfo.getQdInfo().getNetBankInfo().setNetBankBuildTime(bankInfo.getTime());
            }
            if(requestJson.getString("mobileBank").equalsIgnoreCase("true")){
                bankInfo.getQdInfo().getMobileBankInfo().setMobileBankBuildTime(bankInfo.getTime());
            }
            result.put("result","true");

        }
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/queryQDJS", method = RequestMethod.GET)
    public void queryQDJS(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        JSONObject result = new JSONObject();
        String code = requestJson.getString("code");
        BankInfo bankInfo =StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo ==null){
            result.put("result","false");
        }else {
            QDInfo qdInfo=bankInfo.getQdInfo();
            if(qdInfo ==null){
                bankInfo.setQdInfo(new QDInfo());
            }
            result.put("qdInfo",bankInfo.getQdInfo());
        }

    }
}
