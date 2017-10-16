package com.htsec.controller;

import com.htsec.Student.process.StudentInitManager;
import com.htsec.Student.process.StudentOrderManager;
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
public class TeacherOrderProcessController {
    private static final Logger logger = Logger.getLogger(TeacherOrderProcessController.class);
    @RequestMapping(value = "/startPersonalLoanProcess", method = RequestMethod.GET)
    public void startPersonalLoanProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getPersonalLoanOrderMap().get(time).size()!= StudentProcessManager.getBankInfoHashMap().size()){
            result.put("result","false");
            result.put("info","存在未提交学生");
            response.getWriter().write(result.toString());
            return;
        }
        if(StudentOrderManager.dispatchHouseLoanOrder(time)){
            result.put("result","true");
            return;
        }else {
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
    }

    @RequestMapping(value = "/startPersonalDepositProcess", method = RequestMethod.GET)
    public void startPersonalDepositProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getPersonalDepositOrderMap().get(time).size()!= StudentProcessManager.getBankInfoHashMap().size()){
            result.put("result","false");
            result.put("info","存在未提交学生");
            response.getWriter().write(result.toString());
            return;
        }
        if(StudentOrderManager.dispatchPersonalDepositOrder(time)){
            result.put("result","true");
            response.getWriter().write(result.toString());
            return;
        }else {
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
    }

    @RequestMapping(value = "/startCompanyDepositProcess", method = RequestMethod.GET)
    public void startCompanyDepositProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getCompanyDepositOrderMap().get(time).size()!= StudentProcessManager.getBankInfoHashMap().size()){
            result.put("result","false");
            result.put("info","存在未提交学生");
            response.getWriter().write(result.toString());
            return;
        }
        if(StudentOrderManager.dispathCompanyDepositOrder(time)){
            result.put("result","true");
            return;
        }else {
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
    }

    @RequestMapping(value = "/startCompanyLongLoanProcess", method = RequestMethod.GET)
    public void startCompanyLongLoanProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getCompanLoanOrderMap().get(time).size()!= StudentProcessManager.getBankInfoHashMap().size()){
            result.put("result","false");
            result.put("info","存在未提交学生");
            response.getWriter().write(result.toString());
            return;
        }
        if(StudentOrderManager.startCompanyLongLoanProcess(time)){
            result.put("result","true");
            return;
        }else {
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
    }


    @RequestMapping(value = "/startCompanyShortLoanProcess", method = RequestMethod.GET)
    public void startCompanyShortLoanProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getCompanLoanOrderMap().get(time).size()!= StudentProcessManager.getBankInfoHashMap().size()){
            result.put("result","false");
            result.put("info","存在未提交学生");
            response.getWriter().write(result.toString());
            return;
        }
        if(StudentOrderManager.startCompanyShortLoanProcess(time)){
            result.put("result","true");
            return;
        }else {
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
    }
}
