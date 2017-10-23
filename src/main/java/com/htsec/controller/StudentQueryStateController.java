package com.htsec.controller;

import com.htsec.Student.beans.*;
import com.htsec.Student.process.MessageManager;
import com.htsec.Student.process.StudentInitManager;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class StudentQueryStateController {
    private static final Logger logger = Logger.getLogger(StudentQuerySCYCController.class);
    @RequestMapping(value = "/queryState", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        String code =queryObj.getString("code");
        String time =queryObj.getString("time");
        JSONObject result = new JSONObject();
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        ///
       /* FHinfo FHinfo = new FHinfo();
        FHinfo.setDepositGroupNum("1");
        FHinfo.setLoanGroupNum("2");
        FHinfo zhInfo1 = new FHinfo();
        zhInfo1.setDepositGroupNum("1");
        zhInfo1.setLoanGroupNum("2");
        ArrayList<FHinfo> zhInfos = new ArrayList<>();
        zhInfos.add(FHinfo);
        zhInfos.add(zhInfo1);



        BankInfo kk=new BankInfo();
        kk.setCash("10000");
        kk.setTime("第一年");
        kk.setTotalDeposit("10000");
        kk.setTotalLoan("dfdsfsdfsd");
        kk.setZhInfoList(zhInfos);

        JGInfo jgInfo =new JGInfo();
        jgInfo.setBbfgl("1");
        jgInfo.setBldkv("22");
        jgInfo.setCdb("22");
        jgInfo.setDkjzd("33");
        jgInfo.setHxzbczl("222");
        jgInfo.setZbczl("ddd");

        StudentMessage studentMessage = new StudentMessage();
        studentMessage.setType("1");
        studentMessage.setMessage("hello world ,你好 太阳");
        ArrayList<StudentMessage> messages = new ArrayList<>();
        messages.add(studentMessage);
        StudentMessage studentMessage1 = new StudentMessage();
        studentMessage1.setType("2");
        studentMessage.setFileName("1.xlsx");




        result.put("bankInfo",kk);
        result.put("jgInfo",jgInfo);
        result.put("message",messages);*/
       String totalDeposit=bankInfo.getTotalDepositMap().get(time);
       String totalLoan=bankInfo.getTotalLoanMap().get(time);
       String DepositLoanRate ="0";
       if(totalDeposit==null||totalLoan==null||totalLoan=="0"){

       }else {
           DepositLoanRate= new BigDecimal(totalLoan).divide(new BigDecimal(totalDeposit),8,BigDecimal.ROUND_HALF_UP).toString();
       }
        result.put("bankInfo",bankInfo);
       result.put("depositLoanRate",DepositLoanRate);


        result.put("message", MessageManager.findByCode(code));
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/**
 * 计算风险加权资产
 */
    private BigDecimal calcFXJQ(BankInfo bankInfo,String time){
        BigDecimal result =new BigDecimal("0");

        //TODO 存放在央行的款项
        //TODO 当前同行拆借*0.2
        //TODO 当前金融债总额*1
        //当前企业贷款*1
       // result=result.add(bankInfo.getTotalLoanMap())
        return result;
    }

    /**
     * 计算
     * 企业贷款	100%
     住房按揭贷款	50%
     其他消费贷款	100%
     的风险加权资产

     */

    private BigDecimal calcLoan(BankInfo bankInfo,String time){
        List<LoanInfo> loanInfoList=bankInfo.getLoanInfoList();
        if(loanInfoList ==null||loanInfoList.size()==0){
            return new BigDecimal("0");
        }
        String carLoanTime=StudentInitManager.getLoanRule().getCarLoanTime();
        String companyShortLoanTime=StudentInitManager.getLoanRule().getCompanyShortLoanTime();
        String otherLoanTime= StudentInitManager.getLoanRule().getOtherLoanTime();
        String companyLongLoanTime = StudentInitManager.getLoanRule().getCompanyLongLoanTime();
        String houseLoanTime=StudentInitManager.getLoanRule().getHouseLoanTime();
        BigDecimal companyShortLoanMoney = new BigDecimal("0");
        BigDecimal companyLongLoanMoney = new BigDecimal("0");
        BigDecimal carLoanMoney =new BigDecimal("0");
        BigDecimal houseLoanMoney =new BigDecimal("0");
        BigDecimal otherLoanMoney = new BigDecimal("0");
        for(LoanInfo loanInfo:loanInfoList){
            if(loanInfo.getLoanType().equalsIgnoreCase("companyShortOrder")){
                if(new BigDecimal(time).subtract(new BigDecimal(loanInfo.getLoanStartTime())).compareTo(new BigDecimal(companyShortLoanTime))<0){
                    companyShortLoanMoney=companyShortLoanMoney.add(new BigDecimal(loanInfo.getLoanMoney()));
                }

            }
            if(loanInfo.getLoanType().equalsIgnoreCase("companyLongOrder")){
                if(new BigDecimal(time).subtract(new BigDecimal(loanInfo.getLoanStartTime())).compareTo(new BigDecimal(companyLongLoanTime))<0){
                    companyLongLoanMoney=companyLongLoanMoney.add(new BigDecimal(loanInfo.getLoanMoney()));
                }

            }
        }

        for(Map.Entry<String,String>entry:bankInfo.getHouseLoanMap().entrySet()){
            if(new BigDecimal(time).subtract(new BigDecimal(entry.getKey())).compareTo(new BigDecimal(houseLoanTime))<0){
                BigDecimal loanDuringTime= new BigDecimal(time).subtract( new BigDecimal(entry.getKey()));
                BigDecimal loanRemainRate = new BigDecimal("1").subtract(loanDuringTime.multiply(new BigDecimal(StudentInitManager.getLoanRule().getHouseLoanReturn())));
                houseLoanMoney=houseLoanMoney.add(new BigDecimal(entry.getValue()).multiply(loanRemainRate)).multiply(new BigDecimal("0.5"));
            }
        }
        for(Map.Entry<String,String>entry:bankInfo.getOtherLoanMap().entrySet()){
            if(new BigDecimal(time).subtract(new BigDecimal(entry.getKey())).compareTo(new BigDecimal(otherLoanTime))<0){
                BigDecimal loanDuringTime= new BigDecimal(time).subtract( new BigDecimal(entry.getKey()));
                BigDecimal loanRemainRate = new BigDecimal("1").subtract(loanDuringTime.multiply(new BigDecimal(StudentInitManager.getLoanRule().getOtherLoanReturn())));
                otherLoanMoney=otherLoanMoney.add(new BigDecimal(entry.getValue()).multiply(loanRemainRate));
            }
        }

        for(Map.Entry<String,String>entry:bankInfo.getCarLoanMap().entrySet()){
            if(new BigDecimal(time).subtract(new BigDecimal(entry.getKey())).compareTo(new BigDecimal(carLoanTime))<0){
                BigDecimal loanDuringTime= new BigDecimal(time).subtract( new BigDecimal(entry.getKey()));
                BigDecimal loanRemainRate = new BigDecimal("1").subtract(loanDuringTime.multiply(new BigDecimal(StudentInitManager.getLoanRule().getOtherLoanReturn())));
                carLoanMoney=carLoanMoney.add(new BigDecimal(entry.getValue()).multiply(loanRemainRate));
            }
        }
        return companyShortLoanMoney.add(companyLongLoanMoney).add(carLoanMoney).add(houseLoanMoney).add(otherLoanMoney);

    }


    /**
     * 计算操作风险资本（个人存款、消费贷款）
     */




}
