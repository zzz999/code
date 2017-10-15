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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class UpdateFinancialBondsController {
    private static final Logger logger = Logger.getLogger(UpdateFinancialBondsController.class);

    @RequestMapping(value = "/updateFinancialBonds", method = RequestMethod.GET)
    public void updateFinancialBonds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code = requestJson.getString("code");
        String scale = requestJson.getString("scale");
        String unit_price = requestJson.getString("unit_price");
        String rate = requestJson.getString("rate");
        BankLoanForm blf = new BankLoanForm();
        blf.setLoanCode(code);
        blf.setScale(scale);
        blf.setUnitPrice(unit_price);
        blf.setMoney(new BigDecimal(unit_price).multiply(new BigDecimal(scale)).toString());
        blf.setRate(rate);
        blf.setType("3");
        JSONObject result = new JSONObject();
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if (bankInfo == null) {
            result.put("result", "false");
            response.getWriter().write(result.toString());
            return;
        }
        if (new BigDecimal(blf.getMoney()).compareTo(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(bankInfo.getFreezeCash())).divide(new BigDecimal(2))) == 1) {
            result.put("result", "false");
            result.put("info", "现金不足！");
            response.getWriter().write(result.toString());
            return;
        }
        bankInfo.setFreezeCash(new BigDecimal(bankInfo.getFreezeCash()).add(new BigDecimal(blf.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        bankInfo.getFinancialBondsList().add(blf);
        StudentMessage sm = new StudentMessage(code, "", "3", bankInfo.getName() + "：发行金融债规模为" + scale + "股，每股单价为" + unit_price + "，利率为" + rate + "%", blf.getId() + '@' + code);
        MessageManager.getList().add(sm);
        result.put("result", "true");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/buyFinancialBonds", method = RequestMethod.GET)
    public void buyFinancialBonds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String buyCode = requestJson.getString("buyCode");
        String loanCode = requestJson.getString("loanCode");
        String unit_price = requestJson.getString("unit_price");
        String scale = requestJson.getString("scale");
        String id = requestJson.getString("id");

        JSONObject result = new JSONObject();
        BankInfo buyBankInfo = StudentProcessManager.getBankInfoHashMap().get(buyCode);
        BankInfo loanBankInfo = StudentProcessManager.getBankInfoHashMap().get(loanCode);
        BankLoanForm loanBlf = BankLoanManager.findById(loanBankInfo.getFinancialBondsList(), id);
        if (loanBankInfo == null || buyBankInfo == null||loanBlf==null) {
            result.put("result", "false");
            response.getWriter().write(result.toString());
            return;
        }
        if(loanBlf.getAudit()){
            result.put("result", "false");
            result.put("info", "已经结束招标，不可购买！");
            response.getWriter().write(result.toString());
            return;
        }
        BankLoanForm buyBlf = new BankLoanForm();
        buyBlf.setLoanCode(loanCode);
        buyBlf.setBuyCode(buyCode);
        buyBlf.setScale(scale);
        buyBlf.setUnitPrice(unit_price);
        buyBlf.setMoney(new BigDecimal(buyBlf.getUnitPrice()).multiply(new BigDecimal(scale)).toString());
        buyBlf.setRate(loanBlf.getRate());
        buyBlf.setType("3_1");
        buyBlf.setRef(id);

        if (new BigDecimal(buyBlf.getMoney()).compareTo(new BigDecimal(buyBankInfo.getCash()).subtract(new BigDecimal(buyBankInfo.getFreezeCash()))) == 1) {
            result.put("result", "false");
            result.put("info", "现金不足！");
            response.getWriter().write(result.toString());
            return;
        }
        buyBankInfo.setFreezeCash(new BigDecimal(buyBankInfo.getFreezeCash()).add(new BigDecimal(buyBlf.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        BankLoanManager.getFinancialBondsList().add(buyBlf);
        StudentMessage sm = new StudentMessage(buyBlf.getLoanCode(), buyBlf.getBuyCode(), "1", buyBankInfo.getName() + "：已申请" + loanBankInfo.getName() + "的金融债订单，总价为" + buyBlf.getMoney() + "，利率为" + buyBlf.getRate() + "%", null);
        MessageManager.getList().add(sm);
        result.put("result", "true");
        response.getWriter().write(result.toString());

    }

    @RequestMapping(value = "/endFinancialBonds", method = RequestMethod.GET)
    public void endFinancialBonds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject requestJson = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        String code = requestJson.getString("code");
        String id = requestJson.getString("id");
        String time = requestJson.getString("time");
        JSONObject result = new JSONObject();
        BankInfo loanBankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        BankLoanForm loanBlf = BankLoanManager.findById(loanBankInfo.getFinancialBondsList(), id);
        if(loanBlf.getAudit()){
            result.put("result", "false");
            result.put("info", "不能重复结束招标！");
            response.getWriter().write(result.toString());
            return;
        }
        if (loanBankInfo == null) {
            result.put("result", "false");
            response.getWriter().write(result.toString());
            return;
        }
        List<BankLoanForm> list = BankLoanManager.findByRef(BankLoanManager.getFinancialBondsList(), id);
        Collections.sort(list,new Comparator<BankLoanForm>(){
            public int compare(BankLoanForm arg0, BankLoanForm arg1) {
                int result=new BigDecimal(arg1.getMoney()).compareTo(new BigDecimal(arg0.getMoney()));
                if(result==0){
                    return new BigDecimal(arg1.getUnitPrice()).compareTo(new BigDecimal(arg0.getUnitPrice()));
                }
                return result;
            }
        });
        BigDecimal sum=new BigDecimal(0),count=new BigDecimal(loanBlf.getScale()),sumScale=new BigDecimal(0),sumMoney=new BigDecimal(0);
        List<BankLoanForm> resultList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            BankLoanForm blf=list.get(i);
            BigDecimal sumTemp=sum.add(new BigDecimal(blf.getScale()));
            BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(blf.getBuyCode());
            bankInfo.setFreezeCash(new BigDecimal(bankInfo.getFreezeCash()).subtract(new BigDecimal(blf.getMoney())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            BankLoanManager.getFinancialBondsList().remove(blf);
            blf.setAudit(true);
            blf.setStartTime(time);
            blf.setEndTime((Integer.parseInt(time)+3)+"");
            resultList.add(blf);
            StudentMessage sm;
            if(sumTemp.compareTo(count)==1){
                blf.setScale(count.subtract(sum).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                blf.setMoney(new BigDecimal(blf.getUnitPrice()).multiply(new BigDecimal(blf.getScale())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                sumScale=sumScale.add(count.subtract(sum));
                sumMoney=sumMoney.add(new BigDecimal(blf.getMoney()));
                bankInfo.setCash(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(blf.getMoney())).toString());
                bankInfo.getFinancialBondsList().add(blf);
                sm= new StudentMessage("-2", blf.getBuyCode(), "1", bankInfo.getName() + "：已完成金融债订单金额为："+blf.getMoney(), null);
                MessageManager.getList().add(sm);
                break;
            }else{
                bankInfo.setCash(new BigDecimal(bankInfo.getCash()).subtract(new BigDecimal(blf.getMoney())).toString());
                sumScale=sumScale.add(new BigDecimal(blf.getScale()));
                sumMoney=sumMoney.add(new BigDecimal(blf.getMoney()));
                bankInfo.getFinancialBondsList().add(blf);
                sm = new StudentMessage("-2", blf.getBuyCode(), "1", bankInfo.getName() + "：已完成金融债订单金额为："+blf.getMoney(), null);
                MessageManager.getList().add(sm);
                if(sumTemp.compareTo(count)==0)break;
                sum=sumTemp;
            }
        }
        loanBlf.setAudit(true);
        loanBlf.setStartTime(time);
        loanBlf.setEndTime((Integer.parseInt(time)+3)+"");
        loanBlf.setScale(sumScale.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        loanBlf.setMoney(sumMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        loanBankInfo.setCash(new BigDecimal(loanBankInfo.getCash()).add(new BigDecimal(loanBlf.getMoney()).multiply(new BigDecimal(0.9999))).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        StudentMessage sm = new StudentMessage(code, code, "1", loanBankInfo.getName() + "：结束金融债订单，订单金额为："+loanBlf.getMoney(), null);
        MessageManager.getList().add(sm);
        result.put("result", "true");
        result.put("list", resultList);
        response.getWriter().write(result.toString());
    }
}
