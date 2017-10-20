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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 国债
 * Created by bernard on 2017/9/26.
 */
@Controller
public class UpdateNationalLoanController {
    private static final Logger logger = Logger.getLogger(UpdateNationalLoanController.class);

    @RequestMapping(value = "/updateNationalLoan", method = RequestMethod.GET)
    public void updateNationalLoan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code = requestJson.getString("code");
        String money = requestJson.getString("money");
        String time = requestJson.getString("time");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        BankLoanForm blf = new BankLoanForm();
        blf.setLoanCode(code);
        blf.setMoney(money);
        blf.setType("2");
        blf.setRate("4");
        blf.setStartTime(time);
        blf.setEndTime((Integer.parseInt(time) + 4) + "");
        JSONObject result = new JSONObject();

        if (new BigDecimal(blf.getMoney()).compareTo(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(bankInfo.getFreezeCash()))) == 1) {
            result.put("result", "false");
            result.put("info", "现金不足！");
            response.getWriter().write(result.toString());
            return;
        }
        bankInfo.setFreezeCash(new BigDecimal(bankInfo.getFreezeCash()).add(new BigDecimal(blf.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        BankLoanManager.getNationalLoanList().add(blf);
        result.put("result", "true");
        response.getWriter().write(result.toString());

    }

    @RequestMapping(value = "/auditNationalLoan", method = RequestMethod.GET)
    public void auditNationalLoan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        BigDecimal count = new BigDecimal(requestJson.getString("money"));
        List<BankLoanForm> list = BankLoanManager.getNationalLoanList();
        Collections.sort(list, new Comparator<BankLoanForm>() {
            public int compare(BankLoanForm arg0, BankLoanForm arg1) {
                return new BigDecimal(arg1.getMoney()).compareTo(new BigDecimal(arg0.getMoney()));
            }
        });
        JSONObject result = new JSONObject();
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            BankLoanForm blf = list.get(i);
            BigDecimal sumTemp = sum.add(new BigDecimal(blf.getMoney()));
            BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(blf.getLoanCode());
            blf.setAudit(true);
            bankInfo.setFreezeCash(new BigDecimal(bankInfo.getFreezeCash()).subtract(new BigDecimal(blf.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            if (sumTemp.compareTo(count) == 1) {
                blf.setMoney(count.subtract(sum).toString());
                bankInfo.setCash(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(blf.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                bankInfo.getNationalLoanList().add(blf);
                break;
            } else {
                bankInfo.getNationalLoanList().add(blf);
                bankInfo.setCash(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(blf.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                if (sumTemp.compareTo(count) == 0) break;
                sum = sumTemp;
            }
        }
        BankLoanManager.setNationalLoanList(new ArrayList<>());
        result.put("result", "true");
        response.getWriter().write(result.toString());
    }
}
