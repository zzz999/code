package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.BankLoanForm;
import com.htsec.Student.beans.StudentMessage;
import com.htsec.Student.process.BankLoanManager;
import com.htsec.Student.process.MessageManager;
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
import java.math.BigDecimal;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class UpdateInterBankBorrowingController {
    private static final Logger logger = Logger.getLogger(UpdateInterBankBorrowingController.class);

    @RequestMapping(value = "/updateInterBankBorrowing", method = RequestMethod.GET)
    public void updateInterBankBorrowing(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
       // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String buyCode =requestJson.getString("buyCode");
        String money =requestJson.getString("money");
        String time =requestJson.getString("time");
        String rate =requestJson.getString("rate");
        BankInfo bankInfo= StudentProcessManager.getBankInfoHashMap().get(code);
        BankInfo buyBankInfo= StudentProcessManager.getBankInfoHashMap().get(buyCode);
        JSONObject result = new JSONObject();
        if(bankInfo==null||buyBankInfo==null){
            result.put("result","false");
            response.getWriter().write(result.toString());
            return;
        }
        BankLoanForm blf=new BankLoanForm();
        blf.setLoanCode(code);
        blf.setBuyCode(buyCode);
        blf.setMoney(money);
        blf.setRate(rate);
        blf.setType("4");
        blf.setStartTime(time);
        blf.setEndTime(time);
        BankLoanManager.getInterBankBorrowingList().add(blf);
        StudentMessage sm=new StudentMessage(code,buyCode,"4",bankInfo.getName()+"：请求"+buyBankInfo.getName()+"同业拆借额度为"+money+"，利率为"+rate+"%，时间为1年",blf.getId());
        MessageManager.getList().add(sm);

        result.put("result","true");
        response.getWriter().write(result.toString());
    }
    @RequestMapping(value = "/buyInterBankBorrowing", method = RequestMethod.GET)
    public void buyInterBankBorrowing(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
            BankInfo bankInfo= StudentProcessManager.getBankInfoHashMap().get(code);
            if (new BigDecimal(blf.getMoney()).compareTo(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(bankInfo.getFreezeCash()))) == 1) {
                result.put("result", "false");
                result.put("info", "现金不足！");
                response.getWriter().write(result.toString());
                return;
            }
            BankInfo loanBankInfo= StudentProcessManager.getBankInfoHashMap().get(blf.getLoanCode());
            if(bankInfo==null){
                result.put("result","false");
                response.getWriter().write(result.toString());
                return;
            }
            blf.setBuyCode(code);
            blf.setAudit(true);

            bankInfo.setCash(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(blf.getMoney())).toString());
            loanBankInfo.setCash(new BigDecimal(loanBankInfo.getCash()).add(new BigDecimal(blf.getMoney())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());

            bankInfo.getInterBankBorrowingList().add(blf);
            loanBankInfo.getInterBankBorrowingList().add(blf);
            StudentMessage sm=new StudentMessage(blf.getLoanCode(),blf.getBuyCode(),"1",bankInfo.getName()+"：买入"+loanBankInfo.getName()+"出售的同业拆借，额度为"+blf.getMoney()+"，利率为"+blf.getRate()+"%，时间为"+(Integer.parseInt(blf.getEndTime())-Integer.parseInt(blf.getStartTime()))+"年",blf.getId());
            MessageManager.getList().add(sm);
            result.put("result","true");
        }
        response.getWriter().write(result.toString());
    }
}
