package com.htsec.controller;

import com.htsec.Student.beans.*;
import com.htsec.Student.init.bean.DepositRule;
import com.htsec.Student.init.bean.LoanRule;
import com.htsec.Student.process.MessageManager;
import com.htsec.Student.process.StudentInitManager;
import com.htsec.Student.process.StudentOrderManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentLoginController {
    private static final Logger logger = Logger.getLogger(StudentQueryCompanyInfoController.class);


    @RequestMapping(value = "/studentLogin", method = RequestMethod.GET)
    public void studentLogin(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        if (StudentProcessManager.getBankInfoHashMap().containsKey(queryObj.getString("code")) == false) {
            result.put("result", "false");
        } else {
            BankInfo info = StudentProcessManager.getBankInfoHashMap().get(queryObj.getString("code"));
            info.setName(queryObj.getString("name"));
            result.put("result", "true");
            result.put("code", queryObj.getString("code"));
        }

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;


    }

    @RequestMapping(value = "/teacherInitStudent", method = RequestMethod.GET)
    public void teacherInitStudent(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        int studentNum = Integer.parseInt(queryObj.getString("studentNum"));
        if (StudentProcessManager.getBankInfoHashMap().size() >= studentNum) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (String key : StudentProcessManager.getBankInfoHashMap().keySet()) {
                arrayList.add(key);
            }
            result.put("codes", arrayList);
        } else {
            int oldSize = studentNum - StudentProcessManager.getBankInfoHashMap().size();
            for (int i = 0; i < oldSize; i++) {
                BankInfo bankInfo = new BankInfo();
                String code = Math.abs((System.currentTimeMillis() + "bank" + i).hashCode()) + "";
                bankInfo.setName("银行" + (i + 1));
                bankInfo.setCash("50000000.00");
                StudentProcessManager.getBankInfoHashMap().put(code, bankInfo);
            }
            ArrayList<String> arrayList = new ArrayList<>();
            for (String key : StudentProcessManager.getBankInfoHashMap().keySet()) {
                arrayList.add(key);
            }
            result.put("codes", arrayList);
        }

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/queryStudents", method = RequestMethod.GET)
    public void queryStudents(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        List<Map> arrayList = new ArrayList<>();
        HashMap<String, BankInfo> bankInfoHashMap = StudentProcessManager.getBankInfoHashMap();
        for (String key : bankInfoHashMap.keySet()) {
            if (bankInfoHashMap.get(key).getName() == null) continue;
            Map<String, String> fields = new HashMap<>();
            fields.put("code", key);
            fields.put("name", bankInfoHashMap.get(key).getName());
            arrayList.add(fields);
        }
        result.put("list", arrayList);

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @RequestMapping(value = "/updateStudents", method = RequestMethod.GET)
    public void updateStudents(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code = requestJson.getString("code");
        JSONObject result = new JSONObject();
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if (requestJson.getString("name") != null) bankInfo.setName(requestJson.getString("name"));
        if (requestJson.getString("affiliatedSchool") != null)
            bankInfo.setAffiliatedSchool(requestJson.getString("affiliatedSchool"));
        if (requestJson.getString("ceoNames") != null) bankInfo.setCeoNames(requestJson.getString("ceoNames"));
        if (requestJson.getString("cfoNames") != null) bankInfo.setCfoNames(requestJson.getString("cfoNames"));
        if (requestJson.getString("cloNames") != null) bankInfo.setCloNames(requestJson.getString("cloNames"));
        if (requestJson.getString("cmoNames") != null) bankInfo.setCmoNames(requestJson.getString("cmoNames"));
        if (requestJson.getString("cpoNames") != null) bankInfo.setCpoNames(requestJson.getString("cpoNames"));
        if (requestJson.getString("managementState") != null)
            bankInfo.setManagementState(requestJson.getString("managementState"));
        result.put("result", "true");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/queryStudentsInfo", method = RequestMethod.GET)
    public void queryStudentsInfo(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code = requestJson.getString("code");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        JSONObject result = new JSONObject();
        result.put("info", StudentProcessManager.getBankInfoHashMap().get(code));
        result.put("txOrderBeanList",bankInfo.getTxOrderBeanList());
        int curTime=Integer.parseInt(bankInfo.getTime());
        LoanRule loanRule = StudentInitManager.getLoanRule();
        DepositRule depositRule = StudentInitManager.getDepositRule();
        List<JSONObject> loanList=new ArrayList<>();
        for(int i=1;i<=curTime;i++){
            PersonalLoanOrder forPlOrder = StudentOrderManager.getPersonalLoanOrderMap().get(bankInfo.getTime()).get(code);
            if (curTime <= i + Integer.parseInt(loanRule.getHouseLoanTime()) - 1) {
                JSONObject house=new JSONObject();
                house.put("money",bankInfo.getHouseLoanMap().get(i));
                house.put("startTime",i);
                house.put("type","个人房贷");
                house.put("endTime",i+Integer.parseInt(loanRule.getHouseLoanTime())-1);
                loanList.add(house);
            }
            if (curTime <= i + Integer.parseInt(loanRule.getCarLoanTime()) - 1) {
                JSONObject car=new JSONObject();
                car.put("money",bankInfo.getCarLoanMap().get(i));
                car.put("startTime",i);
                car.put("type","个人车贷");
                car.put("endTime",i+Integer.parseInt(loanRule.getHouseLoanTime())-1);
                loanList.add(car);
            }
            if (curTime <= i + Integer.parseInt(loanRule.getOtherLoanTime()) - 1) {
                JSONObject other=new JSONObject();
                other.put("money",bankInfo.getOtherLoanMap().get(i));
                other.put("startTime",i);
                other.put("type","个人其他贷款");
                other.put("endTime",i+Integer.parseInt(loanRule.getHouseLoanTime())-1);
                loanList.add(other);
            }
        }
        List<LoanInfo> loanInfoList = bankInfo.getLoanInfoList();
        for (LoanInfo loanInfo : loanInfoList) {
            String companyLoanRuleTime,type;
            if (loanInfo.getQyShortOrderBean() != null) {
                companyLoanRuleTime = loanRule.getCompanyShortLoanTime();
                type="企业短贷";
            } else {
                companyLoanRuleTime = loanRule.getCompanyLongLoanTime();
                type="企业长贷";
            }
            if (curTime <= Integer.parseInt(loanInfo.getLoanStartTime()) + Integer.parseInt(companyLoanRuleTime) - 1) {
                JSONObject companyLoan=new JSONObject();
                companyLoan.put("money",loanInfo.getLoanMoney());
                companyLoan.put("startTime",loanInfo.getLoanStartTime());
                companyLoan.put("type",type);
                companyLoan.put("endTime",Integer.parseInt(loanInfo.getLoanStartTime())+Integer.parseInt(companyLoanRuleTime)-1);
                loanList.add(companyLoan);
            }
        }
        result.put("loanList",loanList);
        List<JSONObject> depositList=new ArrayList<>();
        for (int i = 1; i <= curTime; i++) {
            if (curTime <= i + Integer.parseInt(depositRule.getPersionalDepositTime()) - 1) {
                JSONObject personalDeposit=new JSONObject();
                personalDeposit.put("money",StudentOrderManager.getPersonalDepositMoneyMap().get(i).get(code));
                personalDeposit.put("startTime",i);
                personalDeposit.put("type","个人存款");
                personalDeposit.put("endTime",i+Integer.parseInt(depositRule.getPersionalDepositTime())-1);
                depositList.add(personalDeposit);
            }
            if (curTime <= i + Integer.parseInt(depositRule.getCompanyDepositTime()) - 1) {
                JSONObject companyDeposit=new JSONObject();
                companyDeposit.put("money",StudentOrderManager.getCompanyDepositMoneyMap().get(i).get(code));
                companyDeposit.put("startTime",i);
                companyDeposit.put("type","企业存款");
                companyDeposit.put("endTime",i+Integer.parseInt(depositRule.getCompanyDepositTime())-1);
                depositList.add(companyDeposit);
            }
        }
        result.put("depositList",depositList);
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/addCash", method = RequestMethod.GET)
    public void addCash(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        String code = requestJson.getString("code");
        JSONObject result = new JSONObject();
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        bankInfo.setCash(new BigDecimal(bankInfo.getCash()).add(new BigDecimal(requestJson.getString("money"))).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        result.put("result", "true");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public void sendMessage(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code = requestJson.getString("code");
        String sendCode = requestJson.getString("sendCode");
        String text = requestJson.getString("text");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if (bankInfo == null) {
            result.put("result", "false");
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        StudentMessage sm = new StudentMessage(code, sendCode, "1", bankInfo.getName() + "：" + text, null);
        MessageManager.getList().add(sm);
        result.put("result", "true");

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
