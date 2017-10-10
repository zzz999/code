package com.htsec.controller;

import com.htsec.Student.beans.BankLoanForm;
import com.htsec.Student.process.BankLoanManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class UpdateInterBankBorrowingController {
    private static final Logger logger = Logger.getLogger(UpdateInterBankBorrowingController.class);

    @RequestMapping(value = "/updateInterBankBorrowing", method = RequestMethod.GET)
    public void updateInterBankBorrowing(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
       // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String buyCode =requestJson.getString("buyCode");
        String money =requestJson.getString("money");
        String time =requestJson.getString("time");
        String year =requestJson.getString("year");
        String rate =requestJson.getString("rate");

        BankLoanForm blf=new BankLoanForm();
        blf.setLoanCode(code);
        blf.setBuyCode(buyCode);
        blf.setMoney(money);
        blf.setRate(rate);
        blf.setType("4");
        blf.setStartTime(time);
        blf.setEndTime(Integer.parseInt(year)+Integer.parseInt(time)+"");
        BankLoanManager.getInterBankBorrowingList().add(blf);
        JSONObject result = new JSONObject();
        result.put("result","ok");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/buyInterBankBorrowing", method = RequestMethod.GET)
    public void buyInterBankBorrowing(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String id =requestJson.getString("id");
        BankLoanForm blf=BankLoanManager.findByIdAndRemove(BankLoanManager.getInterBankBorrowingList(),id);
        JSONObject result = new JSONObject();
        if(blf==null){
            result.put("result","false");
        }else{
            blf.setBuyCode(code);
            blf.setAudit(true);
            result.put("result","ok");
        }
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/queryBankList", method = RequestMethod.GET)
    public void queryBankList(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        //贷款银行的code
        String loanCode =requestJson.getString("loanCode");
        String money =requestJson.getString("money");
        String year =requestJson.getString("year");
        String rate =requestJson.getString("rate");
        JSONObject result = new JSONObject();
        result.put("result","ok");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
