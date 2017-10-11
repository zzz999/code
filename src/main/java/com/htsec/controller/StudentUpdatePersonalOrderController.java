package com.htsec.controller;

import com.htsec.Student.beans.PersonalDepositOrder;
import com.htsec.Student.beans.PersonalLoanOrder;
import com.htsec.Student.init.bean.PersonalDeposit;
import com.htsec.Student.process.StudentInitManager;
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
public class StudentUpdatePersonalOrderController {

    @RequestMapping(value = "/updatePersonalOrder", method = RequestMethod.GET)
    public void updatePersonalOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        String code=queryObj.getString("code");
        String time=queryObj.getString("time");
        JSONObject personalDepositOrder = queryObj.getJSONObject("personalDepositOrder");
        JSONObject personalLoanOrder = queryObj.getJSONObject("personalLoanOrder");
        if(StudentProcessManager.getBankInfoHashMap().get(code)==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
        if (StudentOrderManager.getPersonalDepositOrderMap().get(time)==null){
            PersonalDepositOrder order = new PersonalDepositOrder();
            order.setADmoney(personalDepositOrder.getString("ADmoney"));
            order.setOrderRate(personalDepositOrder.getString("orderRate"));
            order.setCode(code);
            order.setName(StudentProcessManager.getBankInfoHashMap().get(code).getName());
            HashMap<String,PersonalDepositOrder> personalDepositOrderHashMap = new HashMap<>();
            personalDepositOrderHashMap.put(code,order);
            StudentOrderManager.getPersonalDepositOrderMap().put(time,personalDepositOrderHashMap);
        }else {
            PersonalDepositOrder order = new PersonalDepositOrder();
            order.setADmoney(personalDepositOrder.getString("ADmoney"));
            order.setOrderRate(personalDepositOrder.getString("orderRate"));
            order.setCode(code);
            order.setName(StudentProcessManager.getBankInfoHashMap().get(code).getName());
            StudentOrderManager.getPersonalDepositOrderMap().get(time).put(code,order);
        }
        if(StudentOrderManager.getPersonalLoanOrderMap().get(time)==null){
            PersonalLoanOrder order = new PersonalLoanOrder();
            order.setCarLoanADmoney(personalLoanOrder.getString("carLoanADmoney"));
            order.setCarLoanMoney(personalLoanOrder.getString("carLoanMoney"));
            order.setCarLoanRate(personalLoanOrder.getString("carLoanRate"));

            order.setHouseLoanADmoney(personalLoanOrder.getString("houseLoanADmoney"));
            order.setHouseLoanMoney(personalLoanOrder.getString("houseLoanMoney"));
            order.setHouseLoanRate(personalLoanOrder.getString("houseLoanRate"));

            order.setOtherLoanADmoney(personalLoanOrder.getString("otherLoanADmoney"));
            order.setOtherLoanMoney(personalLoanOrder.getString("otherLoanMoney"));
            order.setOtherLoanRate(personalLoanOrder.getString("otherLoanRate"));

            order.setCode(code);
            order.setName(StudentProcessManager.getBankInfoHashMap().get(code).getName());

            HashMap<String,PersonalLoanOrder> map = new HashMap<>();
            map.put(code,order);
            StudentOrderManager.getPersonalLoanOrderMap().put(time,map);
        }else{
            PersonalLoanOrder order = new PersonalLoanOrder();
            order.setCarLoanADmoney(personalLoanOrder.getString("carLoanADmoney"));
            order.setCarLoanMoney(personalLoanOrder.getString("carLoanMoney"));
            order.setCarLoanRate(personalLoanOrder.getString("carLoanRate"));

            order.setHouseLoanADmoney(personalLoanOrder.getString("houseLoanADmoney"));
            order.setHouseLoanMoney(personalLoanOrder.getString("houseLoanMoney"));
            order.setHouseLoanRate(personalLoanOrder.getString("houseLoanRate"));

            order.setOtherLoanADmoney(personalLoanOrder.getString("otherLoanADmoney"));
            order.setOtherLoanMoney(personalLoanOrder.getString("otherLoanMoney"));
            order.setOtherLoanRate(personalLoanOrder.getString("otherLoanRate"));

            order.setCode(code);
            order.setName(StudentProcessManager.getBankInfoHashMap().get(code).getName());
            StudentOrderManager.getPersonalLoanOrderMap().get(time).put(code,order);
        }
        result.put("result","true");
        response.getWriter().write(result.toString());
        return;


    }
}
