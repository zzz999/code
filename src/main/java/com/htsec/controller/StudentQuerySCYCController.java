package com.htsec.controller;

import com.htsec.Student.beans.OrderPerdictInfo;
import com.htsec.Student.init.bean.*;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bernard on 2017/9/25.
 */
@Controller
public class StudentQuerySCYCController {
    private static final Logger logger = Logger.getLogger(StudentQuerySCYCController.class);
    @RequestMapping(value = "/querySCYC", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
       // String time =queryObj.getString("time");
        BaseRate baseRate =TeacherInitManager.getBaseRate();
        result.put("baseRate",baseRate);
        List<QYLongOrderBean> qyLongOrderBeans = TeacherInitManager.getQyLongOrder().getLongOrders();
        HashMap<String,List<QYLongOrderBean>> yearMap =new HashMap<>();
        for(QYLongOrderBean qyLongOrder:qyLongOrderBeans){
            String time = qyLongOrder.getTime();
            if(yearMap.get(time)==null){
                yearMap.put(time,new ArrayList<>());
            }
            yearMap.get(time).add(qyLongOrder);
        }
        HashMap<String,OrderPerdictInfo> orderPerdictInfoHashMap = new HashMap<>();
        for(Map.Entry<String,List<QYLongOrderBean>> entry: yearMap.entrySet()){
            orderPerdictInfoHashMap.put(entry.getKey(),calcAvg(entry.getValue()));
        }
        result.put("companyLongOrder",orderPerdictInfoHashMap);


        List<QYShortOrderBean> qyShortOrderBeanList = TeacherInitManager.getQyShortOrder().getShortOrderBeans();
        HashMap<String,List<QYShortOrderBean>> shortOrderYearMap =new HashMap<>();
        for(QYShortOrderBean shortOrderBean:qyShortOrderBeanList){
            String time = shortOrderBean.getTime();
            if(shortOrderYearMap.get(time)==null){
                shortOrderYearMap.put(time,new ArrayList<>());
            }
            shortOrderYearMap.get(time).add(shortOrderBean);
        }
        HashMap<String,OrderPerdictInfo> shortorderPerdictInfoHashMap = new HashMap<>();
        for(Map.Entry<String,List<QYShortOrderBean>> entry: shortOrderYearMap.entrySet()){
            shortorderPerdictInfoHashMap.put(entry.getKey(),shortcalcAvg(entry.getValue()));
        }

        result.put("companyShortOrder",orderPerdictInfoHashMap);


        List<TXOrderBean> txOrderBeans= TeacherInitManager.getTxOrder().getTxOrderBeans();

        HashMap<String,List<TXOrderBean>> txOrderMap =new HashMap<>();
        for(TXOrderBean txOrderBean:txOrderBeans){
            String time = txOrderBean.getTime();
            if(txOrderMap.get(time)==null){
                txOrderMap.put(time,new ArrayList<>());
            }
            txOrderMap.get(time).add(txOrderBean);
        }
        HashMap<String,OrderPerdictInfo> txorderPredictInfo = new HashMap<>();
        for(Map.Entry<String,List<TXOrderBean>> entry: txOrderMap.entrySet()){
            txorderPredictInfo.put(entry.getKey(),calTXavg(entry.getValue()));
        }

        result.put("txOrder",txorderPredictInfo);
        result.put("houseLoan",TeacherInitManager.getHouseLoan().getHouseLoanBeanList());
        result.put("carLoan",TeacherInitManager.getCarLoan().getCarLoanBeanList());
        result.put("otherLoan",TeacherInitManager.getOtherLoan().getOtherLoanBeanList());
        result.put("contryDeposit",TeacherInitManager.getContryDeposit().getCountryDepositBeanList());

        response.getWriter().write(result.toString());




    }

    private OrderPerdictInfo calTXavg(List<TXOrderBean> txOrderBeans){
        BigDecimal money = new BigDecimal(0);
        BigDecimal rate = new BigDecimal(0);

        for(TXOrderBean txOrderBean: txOrderBeans){
            money =money.add(new BigDecimal(txOrderBean.getOrderMoney()));
            rate =rate.add(new BigDecimal(txOrderBean.getRate()));
        }
        OrderPerdictInfo orderPerdictInfo = new OrderPerdictInfo();
        orderPerdictInfo.setAvgMoney(money.toString());
        orderPerdictInfo.setAvgRate(rate.divide(new BigDecimal(txOrderBeans.size()),4).toString());
        return orderPerdictInfo;
    }

    private OrderPerdictInfo calcAvg(List<QYLongOrderBean> qyLongOrderList){
        BigDecimal money = new BigDecimal(0);
        BigDecimal rate = new BigDecimal(0);

        for(QYLongOrderBean qyLongOrder: qyLongOrderList){
            money =money.add(new BigDecimal(qyLongOrder.getLoanMoney()));
            rate =rate.add(new BigDecimal(qyLongOrder.getHighestRate()));
        }
        OrderPerdictInfo orderPerdictInfo = new OrderPerdictInfo();
        orderPerdictInfo.setAvgMoney(money.toString());
        orderPerdictInfo.setAvgRate(rate.divide(new BigDecimal(qyLongOrderList.size()),4).toString());
        return orderPerdictInfo;
    }

    private OrderPerdictInfo shortcalcAvg(List<QYShortOrderBean> qyLongOrderList){
        BigDecimal money = new BigDecimal(0);
        BigDecimal rate = new BigDecimal(0);

        for(QYShortOrderBean qyLongOrder: qyLongOrderList){
            money =money.add(new BigDecimal(qyLongOrder.getLoanMoney()));
            rate =rate.add(new BigDecimal(qyLongOrder.getHighestRate()));
        }
        OrderPerdictInfo orderPerdictInfo = new OrderPerdictInfo();
        orderPerdictInfo.setAvgMoney(money.toString());
        orderPerdictInfo.setAvgRate(rate.divide(new BigDecimal(qyLongOrderList.size()),4).toString());
        return orderPerdictInfo;
    }

}
