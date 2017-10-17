package com.htsec.controller;


import com.htsec.Student.beans.StudentOrderSelectBean;
import com.htsec.Student.init.bean.CompanyInfo;
import com.htsec.Student.process.CompanyLoanProcess;
import com.htsec.Student.process.StudentOrderManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.info;

@Controller
public class StudentOrderSelectController {
    private static final Logger logger = Logger.getLogger(StudentOrderSelectController.class);
    @RequestMapping(value = "/queryLongOrderSelectInfo", method = RequestMethod.GET)
    public void queryLongOrderSelectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        JSONObject result = new JSONObject();
        String time= requestJson.getString("time");
        String code=requestJson.getString("code");
        CompanyLoanProcess companyLoanProcess= StudentOrderManager.getCompanyLongLoanProcessHashMap().get(time);
        if(companyLoanProcess ==null){
            result.put("isStart",false);
            response.getWriter().write(result.toString());
            return;
        }
        for(StudentOrderSelectBean bean:companyLoanProcess.getStudentOrderSelectBeanList()){
            if(bean.getCode().equalsIgnoreCase(code)){
                result.put("rate",bean.getRate());
            }
        }
        result.put("isStart",companyLoanProcess.isStart());
        result.put("code",companyLoanProcess.getNowCode());
        result.put("qyLongOrder",companyLoanProcess.getQyLongOrder());
        if(companyLoanProcess.getSelectedOrders().size()==0){
            result.put("selectedOrders",new ArrayList<>());
        }else {
            List<String> selectOrders = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : companyLoanProcess.getSelectedOrders().entrySet()) {
                selectOrders.addAll(entry.getValue());

            }
            result.put("selectedOrders", selectOrders);
        }
        response.getWriter().write(result.toString());
    }

    @RequestMapping(value = "/queryShortOrderSelectInfo", method = RequestMethod.GET)
    public void queryShortOrderSelectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        JSONObject result = new JSONObject();
        String time= requestJson.getString("time");
        String code=requestJson.getString("code");
        CompanyLoanProcess companyLoanProcess= StudentOrderManager.getCompanyShortLoanProcessHashMap().get(time);
        if(companyLoanProcess ==null){
            result.put("isStart",false);
            response.getWriter().write(result.toString());
            return;
        }
        for(StudentOrderSelectBean bean:companyLoanProcess.getStudentOrderSelectBeanList()){
            if(bean.getCode().equalsIgnoreCase(code)){
                result.put("rate",bean.getRate());
            }
        }
        result.put("isStart",companyLoanProcess.isStart());
        result.put("code",companyLoanProcess.getNowCode());
        result.put("qyShortOrder",companyLoanProcess.getQyShortOrder());
        if(companyLoanProcess.getSelectedOrders().size()==0){
            result.put("selectedOrders",new ArrayList<>());
        }else {
            List<String> selectOrders = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : companyLoanProcess.getSelectedOrders().entrySet()) {
                selectOrders.addAll(entry.getValue());

            }
            result.put("selectedOrders", selectOrders);
        }
        response.getWriter().write(result.toString());
    }

    @RequestMapping(value = "/updateLongOrderSelectInfo", method = RequestMethod.GET)
    public void updateLongOrderSelectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        String code=requestJson.getString("code");
        String orderName=requestJson.getString("orderName");
        String time=requestJson.getString("time");
        CompanyLoanProcess process=StudentOrderManager.getCompanyLongLoanProcessHashMap().get(time);
        if(process.getSelectedOrders().get(code)==null){
            process.getSelectedOrders().put(code,new ArrayList<String>());
        }
        process.getSelectedOrders().get(code).add(orderName);
        StudentOrderSelectBean studentOrderSelectBean=process.getStudentOrderSelectBeanList().get(process.getNext());
        studentOrderSelectBean.setTimeRemain(studentOrderSelectBean.getTimeRemain()-1);
        setNextSelectStudent(process);
        JSONObject result = new JSONObject();
        result.put("result","true");
        response.getWriter().write(result.toString());
        return;
    }

    @RequestMapping(value = "/updateShortOrderSelectInfo", method = RequestMethod.GET)
    public void updateShortOrderSelectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        String code=requestJson.getString("code");
        String orderName=requestJson.getString("orderName");
        String time=requestJson.getString("time");
        CompanyLoanProcess process=StudentOrderManager.getCompanyShortLoanProcessHashMap().get(time);
        if(process.getSelectedOrders().get(code)==null){
            process.getSelectedOrders().put(code,new ArrayList<String>());
        }
        process.getSelectedOrders().get(code).add(orderName);
        StudentOrderSelectBean studentOrderSelectBean=process.getStudentOrderSelectBeanList().get(process.getNext());
        studentOrderSelectBean.setTimeRemain(studentOrderSelectBean.getTimeRemain()-1);
        setNextSelectStudent(process);
        JSONObject result = new JSONObject();
        result.put("result","true");
        response.getWriter().write(result.toString());
        return;
    }

    private void setNextSelectStudent(CompanyLoanProcess process){
       int totalPeople= process.getStudentOrderSelectBeanList().size();
       boolean isOver =false;
       for(int i =0;i<totalPeople;i++){
           process.setNext(process.getNext()+1);
           if(process.getNext()<totalPeople){
               StudentOrderSelectBean studentOrderSelectBean= process.getStudentOrderSelectBeanList().get(process.getNext());
               if(studentOrderSelectBean.getTimeRemain()>=1){
                   //studentOrderSelectBean.setTimeRemain(studentOrderSelectBean.getTimeRemain()-1);
                   isOver =true;
                   break;
               }
           }else {
               process.setNext(0);
               StudentOrderSelectBean studentOrderSelectBean= process.getStudentOrderSelectBeanList().get(process.getNext());
               if(studentOrderSelectBean.getTimeRemain()>=1){
                   //studentOrderSelectBean.setTimeRemain(studentOrderSelectBean.getTimeRemain()-1);
                   isOver =true;
                   break;
               }
           }
       }
       if(isOver==false){
           process.setStart(false);
       }

    }


    @RequestMapping(value = "/queryPersonalDepositResult", method = RequestMethod.GET)
    public void queryPersonalDepositResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        String code=requestJson.getString("code");
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getPersonalDepositMoneyMap().get(time)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;

        }
        //StudentOrderManager.getPersonalDepositMoneyMap().get(time).get(code);
        result.put("result","true");
        result.put("money",StudentOrderManager.getPersonalDepositMoneyMap().get(time).get(code).toString());
        //TODO 更新信息
        response.getWriter().write(result.toString());

        return;

    }

    @RequestMapping(value = "/queryCompanyDepositResult", method = RequestMethod.GET)
    public void queryCompanyDepositResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        String code=requestJson.getString("code");
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getCompanyDepositMoneyMap().get(time)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;

        }
        //StudentOrderManager.getPersonalDepositMoneyMap().get(time).get(code);
        result.put("result","true");
        result.put("money",StudentOrderManager.getCompanyDepositMoneyMap().get(time).get(code).toString());
        //TODO 更新信息
        response.getWriter().write(result.toString());

        return;
    }

    @RequestMapping(value = "/queryPersonalLoanResult", method = RequestMethod.GET)
    public void queryPersonalLoanResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        String code=requestJson.getString("code");
        String time=requestJson.getString("time");
        JSONObject result = new JSONObject();
        if(StudentOrderManager.getCarLoanMoneyMap().get(time)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;

        }
        if(StudentOrderManager.getOtherLoanMoneyMap().get(time)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;

        }

        if(StudentOrderManager.getHouseLoanMoneyMap().get(time)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
        //StudentOrderManager.getPersonalDepositMoneyMap().get(time).get(code);
        result.put("result","true");
        result.put("carMoney",StudentOrderManager.getCarLoanMoneyMap().get(time).get(code).toString());
        result.put("otherMoney",StudentOrderManager.getOtherLoanMoneyMap().get(time).get(code).toString());
        result.put("houseMoney",StudentOrderManager.getHouseLoanMoneyMap().get(time).get(code).toString());
        //TODO 更新信息
        response.getWriter().write(result.toString());

        return;

    }

}
