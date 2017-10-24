package com.htsec.controller;

import com.htsec.Student.beans.FinancialStatements;
import com.htsec.Student.process.FinancialStatementsManager;
import com.htsec.Student.process.StudentOrderManager;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzz on 2017/10/23.
 */
@Controller
public class FinancialStatementsController {
    @RequestMapping(value = "/queryFinancialStatements", method = RequestMethod.GET)
    public void queryFinancialStatements(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time=requestJson.getString("time");
        String code=requestJson.getString("code");
        FinancialStatements fs=FinancialStatementsManager.getFinancialStatements(code,time);
        JSONObject result = new JSONObject();
        result.put("info",fs);
        response.getWriter().write(result.toString());
    }
}
