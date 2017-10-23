package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.BankLoanForm;
import com.htsec.Student.process.BankLoanManager;
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
public class UpdateAgainLoanController {
    private static final Logger logger = Logger.getLogger(UpdateAgainLoanController.class);

    @RequestMapping(value = "/updateAgainLoan", method = RequestMethod.GET)
    public void updateAgainLoan(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
       // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String money =requestJson.getString("money");
        String time =requestJson.getString("time");
        JSONObject result = new JSONObject();
        BankLoanForm blf=new BankLoanForm();
        blf.setLoanCode(code);
        blf.setMoney(money);
        blf.setType("1");
        blf.setStartTime(time);
        blf.setEndTime(time);
        blf.setRate("3");
        BankLoanManager.getAgainLoanList().add(blf);
        result.put("result","true");
        response.getWriter().write(result.toString());
    }
    @RequestMapping(value = "/auditAgainLoan", method = RequestMethod.GET)
    public void auditAgainLoan(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String id =requestJson.getString("id");
        String audit =requestJson.getString("audit");

        JSONObject result = new JSONObject();
        BankLoanForm blf=BankLoanManager.findByIdAndRemove(BankLoanManager.getAgainLoanList(),id);
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(blf.getLoanCode());
        //通过
        if(audit.equals("0")){
            blf.setAudit(true);
            bankInfo.setCash(new BigDecimal(bankInfo.getCash()).add(new BigDecimal(blf.getMoney())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            bankInfo.getAgainLoanList().add(blf);
        }

        //JSONObject result = new JSONObject();
        result.put("result","true");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/queryAgainLoan", method = RequestMethod.GET)
    public void queryAgainLoan(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        result.put("list",BankLoanManager.getAgainLoanList());
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
