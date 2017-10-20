package com.htsec.controller;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.LoanInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bernard on 2017/9/25.
 */

@Controller
public class StudentOrderManagementController {
    private static final Logger logger = Logger.getLogger(StudentQueryJYGLController.class);

    @RequestMapping(value = "/queryStudentOrder", method = RequestMethod.GET)
    public void queryStudentOrderManagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject( CodeHelper.decode(requestQueryString));
        String code = requestJson.getString("code");
        JSONObject result = new JSONObject();
        BankInfo bankInfo =StudentProcessManager.getBankInfoHashMap().get(code);
        List<LoanInfo> loanInfoList= bankInfo.getLoanInfoList();
        HashMap<String,List<LoanInfo>> LoanInfoMap = new HashMap<>();
        for(LoanInfo loanInfo:loanInfoList){
            if(LoanInfoMap.get(loanInfo.getLoanStartTime()) ==null){
                LoanInfoMap.put(loanInfo.getLoanStartTime(),new ArrayList<>());
            }
            LoanInfoMap.get(loanInfo.getLoanStartTime()).add(loanInfo);
        }

        result.put("nationalLoanList",bankInfo.getNationalLoanList());
        result.put("againLoanList",bankInfo.getAgainLoanList());
        result.put("interBankBorrowingList",bankInfo.getInterBankBorrowingList());
        result.put("financialBondsList",bankInfo.getFinancialBondsList());

        result.put("loanInfoMap",LoanInfoMap);
        response.getWriter().write(result.toString());
    }
}
