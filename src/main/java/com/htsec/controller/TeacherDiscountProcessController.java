package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.LoanInfo;
import com.htsec.Student.beans.PersonalLoanOrder;
import com.htsec.Student.beans.StudentMessage;
import com.htsec.Student.init.bean.TXOrderBean;
import com.htsec.Student.process.MessageManager;
import com.htsec.Student.process.StudentOrderManager;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.Student.process.TeacherInitManager;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzz on 2017/10/21.
 */
@Controller
public class TeacherDiscountProcessController {
    private static final Logger logger = Logger.getLogger(TeacherOrderProcessController.class);

    @RequestMapping(value = "/startDiscountProcess", method = RequestMethod.GET)
    public void startDiscountProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        JSONArray index = requestJson.getJSONArray("index");
        List<TXOrderBean> list = TeacherInitManager.getTxOrder().getTxOrderBeans();
        int sendIndex = 0;
        for (Object i : index) {
            TXOrderBean tx = list.get((int) i);

            for (Map.Entry<String, BankInfo> entry : StudentProcessManager.getBankInfoHashMap().entrySet()) {
                BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(entry.getKey());
                for (LoanInfo loanInfo : bankInfo.getLoanInfoList()) {
                    if (
                            loanInfo.getQyLongOrderBean() != null && loanInfo.getQyLongOrderBean().getQYname().equals(tx.getQYname()) ||
                                    loanInfo.getQyShortOrderBean() != null && loanInfo.getQyShortOrderBean().getQYname().equals(tx.getQYname())
                            ) {
                        StudentMessage sm = new StudentMessage("-2", entry.getKey(), "5", "系统消息：可以购买" + tx.getQYname() + "总额为" + tx.getOrderMoney() + "的贴现订单", tx.getOrderNum());
                        MessageManager.getList().add(sm);
                        sendIndex++;
                    }
                }
            }
        }

        JSONObject result = new JSONObject();
        if (sendIndex == 0) {
            result.put("result", "false");
            result.put("info", "所有学生均无此企业的贷款订单");
            return;
        }

//        StudentMessage sm=new StudentMessage(code,buyCode,"4",bankInfo.getName()+"：请求"+buyBankInfo.getName()+"同业拆借额度为"+money+"，利率为"+rate+"%，时间为1年",blf.getId());
        result.put("result", "true");
        response.getWriter().write(result.toString());
        return;
    }

    @RequestMapping(value = "/queryDiscount", method = RequestMethod.GET)
    public void queryDiscount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String time = requestJson.getString("time");
        JSONObject result = new JSONObject();
        List<TXOrderBean> list = TeacherInitManager.getTxOrder().getTxOrderBeans();
        List<TXOrderBean> resultList = new ArrayList<>();
        for (TXOrderBean tx : list) {
            if (tx.getTime().equals(time)) {
                resultList.add(tx);
            }
        }
        result.put("result", "true");
        result.put("list", resultList);
        response.getWriter().write(result.toString());
        return;
    }

    @RequestMapping(value = "/updateDiscount", method = RequestMethod.GET)
    public void updateDiscount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(requestQueryString));
        String code = requestJson.getString("code");
        String orderNum = requestJson.getString("orderNum");
        JSONObject result = new JSONObject();
        List<TXOrderBean> list = TeacherInitManager.getTxOrder().getTxOrderBeans();
        for (TXOrderBean tx : list) {
            if (tx.getOrderNum().equals(orderNum)) {
                if (tx.getAudit()) {
                    result.put("result", "false");
                    result.put("info", "订单已完成，不能重复提交");
                    response.getWriter().write(result.toString());
                    return;
                }
                BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
                if (bankInfo.getTxOrderBeanList() == null) bankInfo.setTxOrderBeanList(new ArrayList<>());
                if (new BigDecimal(tx.getOrderMoney()).compareTo(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(bankInfo.getFreezeCash()))) == 1) {
                    result.put("result", "false");
                    result.put("info", "现金不足！");
                    response.getWriter().write(result.toString());
                    return;
                }
                tx.setAudit(true);
                bankInfo.setCash(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(tx.getOrderMoney())).toString());
                bankInfo.getTxOrderBeanList().add(tx);
                StudentMessage sm = new StudentMessage("-2", code, "1", "系统消息：已买入" + tx.getQYname() + "总额为" + tx.getOrderMoney() + "的贴现订单", tx.getOrderNum());
                MessageManager.getList().add(sm);
                break;
            }
        }

//        StudentMessage sm=new StudentMessage(code,buyCode,"4",bankInfo.getName()+"：请求"+buyBankInfo.getName()+"同业拆借额度为"+money+"，利率为"+rate+"%，时间为1年",blf.getId());
        result.put("result", "true");
        response.getWriter().write(result.toString());
        return;
    }
}
