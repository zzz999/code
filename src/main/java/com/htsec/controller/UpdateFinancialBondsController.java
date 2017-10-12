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

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class UpdateFinancialBondsController {
    private static final Logger logger = Logger.getLogger(UpdateFinancialBondsController.class);

    @RequestMapping(value = "/updateFinancialBonds", method = RequestMethod.GET)
    public void updateFinancialBonds(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
       // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String money =requestJson.getString("money");
        String rate =requestJson.getString("rate");
        String time =requestJson.getString("time");
        BankLoanForm blf=new BankLoanForm();
        blf.setLoanCode(code);
        blf.setStartTime(time);
        blf.setMoney(money);
        blf.setRate(rate);
        blf.setType("3");
        BankLoanManager.getFinancialBondsList().add(blf);
        BankInfo bankInfo= StudentProcessManager.getBankInfoHashMap().get(code);
        JSONObject result = new JSONObject();
        if(bankInfo==null){
            result.put("result","false");
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        StudentMessage sm=new StudentMessage(code,"","3",bankInfo.getName()+"：申请金融债额度为"+money+"，利率为"+rate+"%",blf.getId());
        MessageManager.getList().add(sm);
        result.put("result","true");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/buyFinancialBonds", method = RequestMethod.GET)
    public void buyFinancialBonds(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String id =requestJson.getString("id");
        BankLoanForm blf=BankLoanManager.findByIdAndRemove(BankLoanManager.getFinancialBondsList(),id);
        JSONObject result = new JSONObject();
        if(blf==null){
            result.put("result","false");
        }else{
            BankInfo bankInfo= StudentProcessManager.getBankInfoHashMap().get(code);
            BankInfo loanBankInfo= StudentProcessManager.getBankInfoHashMap().get(blf.getLoanCode());
            if(bankInfo==null){
                result.put("result","false");
                try {
                    response.getWriter().write(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            blf.setBuyCode(code);
            blf.setAudit(true);
            bankInfo.getFinancialBondsList().add(blf);
            loanBankInfo.getFinancialBondsList().add(blf);
            StudentMessage sm=new StudentMessage(blf.getLoanCode(),blf.getBuyCode(),"1",bankInfo.getName()+"：买入"+loanBankInfo.getName()+"申请的金融债，额度为"+blf.getMoney()+"，利率为"+blf.getRate()+"%",blf.getId());
            MessageManager.getList().add(sm);
            result.put("result","true");
        }
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
