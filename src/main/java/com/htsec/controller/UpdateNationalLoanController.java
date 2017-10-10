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

/**
 * 国债
 * Created by bernard on 2017/9/26.
 */
@Controller
public class UpdateNationalLoanController {
    private static final Logger logger = Logger.getLogger(UpdateNationalLoanController.class);

    @RequestMapping(value = "/updateNationalLoan", method = RequestMethod.GET)
    public void updateNationalLoan(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
       // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String money =requestJson.getString("money");
        BankLoanForm blf=new BankLoanForm();
        blf.setLoanCode(code);
        blf.setMoney(money);
        blf.setType("2");
        BankLoanManager.getNationalLoanList().add(blf);
        JSONObject result = new JSONObject();
        result.put("result","ok");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(value = "/auditNationalLoan", method = RequestMethod.GET)
    public void auditNationalLoan(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String id =requestJson.getString("id");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        JSONObject result = new JSONObject();
        BankLoanForm blf=BankLoanManager.findByIdAndRemove(BankLoanManager.getNationalLoanList(),id);
        blf.setAudit(true);
        result.put("result","ok");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
