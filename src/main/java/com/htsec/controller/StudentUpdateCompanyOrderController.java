package com.htsec.controller;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.htsec.Student.beans.CompanyDepositOrder;
import com.htsec.Student.beans.CompanyLoanOrder;
import com.htsec.Student.beans.PersonalDepositOrder;
import com.htsec.Student.beans.PersonalLoanOrder;
import com.htsec.Student.process.StudentOrderManager;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
@Controller
public class StudentUpdateCompanyOrderController {


    @RequestMapping(value = "/updateCompanyOrder", method = RequestMethod.GET)
    public void updateCompanyOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        String code=queryObj.getString("code");
        String time=queryObj.getString("time");
        JSONObject companyDepositOrderInfo = queryObj.getJSONObject("companyDepositOrder");
        JSONObject companyLoanOrderInfo = queryObj.getJSONObject("companyLoanOrder");
        if(StudentProcessManager.getBankInfoHashMap().get(code)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
        CompanyDepositOrder companyDepositOrder = new CompanyDepositOrder();
        CompanyLoanOrder companyLoanOrder = new CompanyLoanOrder();

        companyDepositOrder.setCode(code);
        companyDepositOrder.setCompanyDepositADmoney(companyDepositOrderInfo.getString("companyDepositADmoney"));
        companyDepositOrder.setCompanyDepositRate(companyDepositOrderInfo.getString("companyDepositRate"));
        companyDepositOrder.setName(StudentProcessManager.getBankInfoHashMap().get(code).getName());

        companyLoanOrder.setCode(code);
        companyLoanOrder.setName(StudentProcessManager.getBankInfoHashMap().get(code).getName());
        companyLoanOrder.setCompanyLongLoanOrderADmoney(companyLoanOrderInfo.getString("companyLongLoanADmoney"));
        companyLoanOrder.setCompanyLongLoanOrderRate(companyLoanOrderInfo.getString("companyLongLoanRate"));
        companyLoanOrder.setCompanyShortLoanADmoney(companyLoanOrderInfo.getString("companyShortLoanADmoney"));
        companyLoanOrder.setCompanyShortLoanRate(companyLoanOrderInfo.getString("companyShortLoanRate"));

        if(StudentOrderManager.getCompanyDepositOrderMap().get(time)==null){
            StudentOrderManager.getCompanyDepositOrderMap().put(time,new HashMap<>());
        }
        if(StudentOrderManager.getCompanLoanOrderMap().get(time)==null){
            StudentOrderManager.getCompanLoanOrderMap().put(time,new HashMap<>());
        }
        StudentOrderManager.getCompanyDepositOrderMap().get(time).put(code,companyDepositOrder);
        StudentOrderManager.getCompanLoanOrderMap().get(time).put(code,companyLoanOrder);

        //StudentOrderManager.getCompanLoanOrderMap().get(time)
        result.put("result","true");
        response.getWriter().write(result.toString());
        return;


    }
}
