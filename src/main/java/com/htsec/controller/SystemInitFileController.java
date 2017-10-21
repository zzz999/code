package com.htsec.controller;

import com.htsec.Student.init.bean.*;
import com.htsec.Student.process.StudentInitManager;
import com.htsec.Student.process.TeacherInitManager;
import com.htsec.excel.ExcelUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by bernard on 2017/9/25.
 */

@Controller
public class SystemInitFileController {
    private static final Logger logger = Logger.getLogger(SystemInitFileController.class);


    @RequestMapping(value="/initCompanyInfo",method = RequestMethod.POST)
    public void initCompanyInfo(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException{
       ArrayList<String> timeList = ExcelUtil.testGetSheetList(file.getInputStream(),file.getName());
       for(int i =0;i<timeList.size();i++){
           CompanyInfo companyInfo = new CompanyInfo();
           ArrayList<ArrayList<String>> companyInfoList=ExcelUtil.testReadExcel(file.getInputStream(), i,file.getName());
           companyInfo.setZHFYtable(companyInfoList.subList(1,13));
         //  ExcelUtil.printBody(companyInfoList.subList(1,12));
           companyInfo.setLRtable(companyInfoList.subList(15,27));
          companyInfo.setZCFZtable(companyInfoList.subList(29,50));
          TeacherInitManager.getCompanyInfoHashMap().put(timeList.get(i),companyInfo);
          TeacherInitManager.setCompanyNames(companyInfo.getLRtable().get(0).subList(1,(companyInfo.getLRtable().get(0).size())));


           /*ExcelUtil.printBody(companyInfo.getLRtable());
           ExcelUtil.printBody(companyInfo.getZCFZtable());
           ExcelUtil.printBody(companyInfo.getZHFYtable());*/
       }
       JSONObject result = new JSONObject();
       result.put("result","ok");
       response.getWriter().write(result.toString());
        //for(int)
        //ArrayList<ArrayList<String>> loanLostPrepareRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 5,file.getName());//贷款损失准备
    }

    @RequestMapping(value= "/initBaseRate",method= RequestMethod.POST)
    public void init(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException {
        //String path = request.getSession().getServletContext().getRealPath("upload");
        initBaseRate(file);
        initQYLongOrder(file);
        initQYShortOrder(file);
        initTXOrder(file);
        initPersonalDeposit(file);
        initHouseLoan(file);
        initCarLoan(file);
        initOtherLoan(file);
        initContryDep(file);
        initCompanyDeposit(file);
        TeacherInitManager.getContryDeposit();
        TeacherInitManager.getOtherLoan();
        TeacherInitManager.getCarLoan();
        TeacherInitManager.getHouseLoan();
        TeacherInitManager.getTxOrder();
        TeacherInitManager.getPersonalDeposit();
        TeacherInitManager.getQyShortOrder();
        TeacherInitManager.getQyLongOrder();
        TeacherInitManager.getBaseRate();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        result.put("result","ok");
        response.getWriter().write(result.toString());








        /*String result="";
        BufferedReader in=new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            logger.info("readLine:"+line);
            if (line.startsWith("#")){
                continue;
            }else {
                String prop=line.split(";")[0];
                String [] keyAndValue =prop.split("=");
                String key = keyAndValue[0].trim();
                String value =keyAndValue[1].trim();
            }
            result +=line+"\n";
        }
       System.out.println(result);*/
        return;
    }

    @RequestMapping(value="/initRule",method = RequestMethod.POST)
    public void initRule(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException{
        initOrgBuildRule(file);
        initGroupBuildRule(file);
        initDepositRule(file);
        initMarketingBuildRule(file);
        initLoanRule(file);
        initLoanLostPrepareRule(file);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        result.put("result","ok");
        response.getWriter().write(result.toString());

    }


    private void initLoanLostPrepareRule(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> loanLostPrepareRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 5,file.getName());//贷款损失准备
        LoanLostPrepareRule loanLostPrepareRule =new LoanLostPrepareRule();
        loanLostPrepareRule.setTypeA(loanLostPrepareRuleInfo.get(1).get(2).trim());
        loanLostPrepareRule.setTypeB(loanLostPrepareRuleInfo.get(2).get(2).trim());
        loanLostPrepareRule.setTypeC(loanLostPrepareRuleInfo.get(3).get(2).trim());
        StudentInitManager.setLoanLostPrepareRule(loanLostPrepareRule);


    }
    /**
     * 贷款规则
     * @param file
     * @throws IOException
     */
    private void initLoanRule(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> loanRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 4,file.getName());//贷款规则
        LoanRule loanRule =new LoanRule();
        loanRule.setHouseLoanTime(loanRuleInfo.get(1).get(1).trim());
        loanRule.setHouseLoanReturn(loanRuleInfo.get(1).get(2).trim());
        loanRule.setHouseLoanRateLow(loanRuleInfo.get(1).get(3).trim());
        loanRule.setHouseLoanRateHigh(loanRuleInfo.get(1).get(4).trim());

        loanRule.setCarLoanTime(loanRuleInfo.get(2).get(1).trim());
        loanRule.setCarLoanReturn(loanRuleInfo.get(2).get(2).trim());
        loanRule.setCarLoanRateLow(loanRuleInfo.get(2).get(3).trim());
        loanRule.setCarLoanRateHigh(loanRuleInfo.get(2).get(4).trim());

        loanRule.setOtherLoanTime(loanRuleInfo.get(3).get(1).trim());
        loanRule.setOtherLoanReturn(loanRuleInfo.get(3).get(2).trim());
        loanRule.setOtherLoanRateLow(loanRuleInfo.get(3).get(3).trim());
        loanRule.setOtherLoanRateHigh(loanRuleInfo.get(3).get(4).trim());

        loanRule.setCompanyLongLoanTime(loanRuleInfo.get(4).get(1).trim());
        loanRule.setCompanyLongLoanRateLow(loanRuleInfo.get(4).get(3).trim());
        loanRule.setCompanyLongLoanRateHigh(loanRuleInfo.get(4).get(4).trim());

        loanRule.setCompanyShortLoanTime(loanRuleInfo.get(5).get(1).trim());
        loanRule.setCompanyShortLoanRateLow(loanRuleInfo.get(5).get(3).trim());
        loanRule.setCompanyShortLoanRateHigh(loanRuleInfo.get(5).get(4).trim());
        StudentInitManager.setLoanRule(loanRule);

    }


    /**
     * 初始化存款规则
     * @param file
     * @throws IOException
     */
    private void initDepositRule(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> depositeRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 2,file.getName());//存款规则
        DepositRule depositRule=new DepositRule();
        depositRule.setCompanyDepositAnnualReturn(depositeRuleInfo.get(2).get(2).trim());
        depositRule.setCompanyDepositTime(depositeRuleInfo.get(2).get(1).trim());
        depositRule.setCompanyRateLow(depositeRuleInfo.get(2).get(3).trim());
        depositRule.setCompanyRateHigh(depositeRuleInfo.get(2).get(4).trim());
        depositRule.setPersionalDepositAnnualReturn(depositeRuleInfo.get(1).get(2).trim());
        depositRule.setPersionalDepositTime(depositeRuleInfo.get(1).get(1).trim());
        depositRule.setPersionalRateLow(depositeRuleInfo.get(1).get(3).trim());
        depositRule.setPersionalRateHigh(depositeRuleInfo.get(1).get(4).trim());
        StudentInitManager.setDepositRule(depositRule);
    }

/**
 * 初始化渠道建设规则
  */
private void initMarketingBuildRule(MultipartFile file) throws  IOException{
    ArrayList<ArrayList<String>> marketBuildRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 3,file.getName());//渠道建设规则
    MarketingBuildRule marketingBuildRule =new MarketingBuildRule();
    marketingBuildRule.setMobilephoneBankBuiltCost(marketBuildRuleInfo.get(2).get(1).trim());
    marketingBuildRule.setMobilephoneBankRunCost(marketBuildRuleInfo.get(2).get(3).trim());
    marketingBuildRule.setMobilephoneBankTime(marketBuildRuleInfo.get(2).get(2).trim());
    marketingBuildRule.setNetBankBuiltCost(marketBuildRuleInfo.get(1).get(1).trim());
    marketingBuildRule.setNetBankTime(marketBuildRuleInfo.get(1).get(2).trim());
    marketingBuildRule.setNetBankRunCost(marketBuildRuleInfo.get(1).get(3).trim());
    StudentInitManager.setMarketingBuildRule(marketingBuildRule);

}

    /**
     *初始化机构建设规则
     */
    private void initOrgBuildRule(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> orgBuildRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 0,file.getName());//机构建设规则
        OrgBuildRule orgBuildRule = new OrgBuildRule();
        orgBuildRule.setFHbuildCost(orgBuildRuleInfo.get(1).get(1).trim());
        orgBuildRule.setFHcount(orgBuildRuleInfo.get(1).get(2).trim());
        orgBuildRule.setFHRunCost(orgBuildRuleInfo.get(1).get(3).trim());
        orgBuildRule.setZHbuildCost(orgBuildRuleInfo.get(2).get(1).trim());
        orgBuildRule.setZHRunCost(orgBuildRuleInfo.get(2).get(3).trim());
        StudentInitManager.setOrgBuildRule(orgBuildRule);

    }

    /**
     * 初始化团队建设规则
     */
    private void initGroupBuildRule(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> groupBuildRuleInfo=ExcelUtil.testReadExcel(file.getInputStream(), 1,file.getName());//团队建设规则
        GroupBuildRule  groupBuildRule = new GroupBuildRule();
        groupBuildRule.setDepositGroupBuildCost(groupBuildRuleInfo.get(1).get(1).trim());
        groupBuildRule.setDepositGroupIncrease(groupBuildRuleInfo.get(1).get(2).trim());
        groupBuildRule.setDepositGroupRunCost(groupBuildRuleInfo.get(1).get(3).trim());
        groupBuildRule.setLoanGroupBuildCost(groupBuildRuleInfo.get(2).get(1).trim());
        groupBuildRule.setLoanGroupIncrease(groupBuildRuleInfo.get(2).get(2).trim());
        groupBuildRule.setLoanGroupRunCost(groupBuildRuleInfo.get(2).get(3).trim());
        StudentInitManager.setGroupBuildRule(groupBuildRule);
    }

    /**
     * 初始化基础利率
     * @param file
     * @throws IOException
     */
    private void initBaseRate(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> baseRateInfo=ExcelUtil.testReadExcel(file.getInputStream(), 0,file.getName());//基础利率
        int lineNum=0;
        ArrayList<String> timeList =null;
        for(ArrayList<String> line:baseRateInfo){
            lineNum++;
            if(lineNum==1){
                timeList =line;
                continue;
            }
            int col =0;
            String type="";
            for(String cell:line){
                col++;
                if(col==1){
                    type = cell;
                }else {
                    BaseRateBean bean= new BaseRateBean();
                    bean.setRate(cell.trim());
                    bean.setTime(timeList.get(col-1));
                    bean.setDepositType(type);
                    if(TeacherInitManager.getBaseRate()==null){
                        TeacherInitManager.setBaseRate(new BaseRate());
                    }
                    if(TeacherInitManager.getBaseRate().getHashMap().get(bean.getTime())==null){
                        TeacherInitManager.getBaseRate().getHashMap().put(bean.getTime(),new ArrayList<>());
                    }
                    TeacherInitManager.getBaseRate().getHashMap().get(bean.getTime()).add(bean);
                }

            }
        }
    }

    /**
     * 企业长订单
     * @param file
     * @throws IOException
     */
    private void initQYLongOrder(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> longOrderInfo=ExcelUtil.testReadExcel(file.getInputStream(), 1,file.getName());//企业长贷订单
        int lineNum=0;
       // ArrayList<String> timeList =null;
        for(ArrayList<String> line:longOrderInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            QYLongOrderBean qyLongOrderBean = new QYLongOrderBean();
            for(String cell:line){
                if(col ==0){
                    qyLongOrderBean.setNum(cell.trim());
                }
                if(col ==1){
                    qyLongOrderBean.setOrderNum(cell.trim());
                }
                if(col==2){
                    qyLongOrderBean.setTime(cell.trim());
                }
                if(col ==3){
                    qyLongOrderBean.setQYname(cell.trim());
                }
                if(col ==4){
                    qyLongOrderBean.setLoanMoney(cell.trim());
                }
                if(col ==5){
                    qyLongOrderBean.setHighestRate(cell.trim());
                }
                col++;

            }
            if(TeacherInitManager.getQyLongOrder()==null){
                TeacherInitManager.setQyLongOrder(new QYLongOrder());
            }
            TeacherInitManager.getQyLongOrder().getLongOrders().add(qyLongOrderBean);

        }
    }

    /**
     * 企业短订单
     * @param file
     * @throws IOException
     */
    private void initQYShortOrder(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> longOrderInfo=ExcelUtil.testReadExcel(file.getInputStream(), 2,file.getName());//企业长贷订单
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:longOrderInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            QYShortOrderBean qyShortOrderBean = new QYShortOrderBean();
            for(String cell:line){
                if(col ==0){
                    qyShortOrderBean.setNum(cell.trim());
                }
                if(col ==1){
                    qyShortOrderBean.setOrderNum(cell.trim());
                }
                if(col==2){
                    qyShortOrderBean.setTime(cell.trim());
                }
                if(col ==3){
                    qyShortOrderBean.setQYname(cell.trim());
                }
                if(col ==4){
                    qyShortOrderBean.setLoanMoney(cell.trim());
                }
                if(col ==5){
                    qyShortOrderBean.setLoanTime(cell.trim());
                }
                if(col ==6){
                    qyShortOrderBean.setHighestRate(cell.trim());
                }
                col++;

            }
            if(TeacherInitManager.getQyShortOrder()==null){
                TeacherInitManager.setQyShortOrder(new QYShortOrder());
            }
            TeacherInitManager.getQyShortOrder().getShortOrderBeans().add(qyShortOrderBean);

        }
    }


    /**
     * 贴现订单
     * @param file
     * @throws IOException
     */
    private void initTXOrder(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> TXOrderInfo=ExcelUtil.testReadExcel(file.getInputStream(), 3,file.getName());//贴现订单
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:TXOrderInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            TXOrderBean txOrderBean = new TXOrderBean();
            for(String cell:line){
                if(col ==0){
                    txOrderBean.setNum(cell.trim());
                }
                if(col ==1){
                    txOrderBean.setOrderNum(cell.trim());
                }
                if(col==2){
                    txOrderBean.setTime(cell.trim());
                }
                if(col ==3){
                    txOrderBean.setOrderMoney(cell.trim());
                }
                if(col ==4){
                    txOrderBean.setTXtime(cell.trim());
                }
                if(col ==5){
                    txOrderBean.setZQ(cell.trim());
                }
                if(col==6){
                    txOrderBean.setRate(cell.trim());
                }
                if(col==7){
                    txOrderBean.setQYname(cell.trim());
                }
                col++;
            }
            if(TeacherInitManager.getTxOrder()==null){
                TeacherInitManager.setTxOrder(new TXOrder());
            }
            TeacherInitManager.getTxOrder().getTxOrderBeans().add(txOrderBean);

        }
    }


    /**
     * 初始化个人存款
     * @param file
     * @throws IOException
     */
    private void initPersonalDeposit(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> personalDepositInfo=ExcelUtil.testReadExcel(file.getInputStream(), 4,file.getName());//个人存款
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:personalDepositInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            PersonalDepositBean personalDepositBean = new PersonalDepositBean();
            for(String cell:line){
                if(col ==0){
                    personalDepositBean.setTime(cell.trim());
                }
                if(col ==1){
                    personalDepositBean.setSum(cell.trim());
                }
                col++;

            }
            if(TeacherInitManager.getPersonalDeposit()==null){
                TeacherInitManager.setPersonalDeposit(new PersonalDeposit());
            }
            TeacherInitManager.getPersonalDeposit().getPersonalDepositBeanList().add(personalDepositBean);

        }
    }

    /**
     * 初始化公司存款
     * @param file
     * @throws IOException
     */
    private void initCompanyDeposit(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> companyDepositInfo=ExcelUtil.testReadExcel(file.getInputStream(), 9,file.getName());//个人存款
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:companyDepositInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            CompanyDepositBean companyDepositBean = new CompanyDepositBean();
            for(String cell:line){
                if(col ==0){
                    companyDepositBean.setTime(cell.trim());
                }
                if(col ==1){
                    companyDepositBean.setSum(cell.trim());
                }
                col++;

            }
            if(TeacherInitManager.getCompanyDeposit()==null){
                TeacherInitManager.setCompanyDeposit(new CompanyDeposit());
            }
            TeacherInitManager.getCompanyDeposit().getCompanyDepositBeanList().add(companyDepositBean);

        }
    }

    /**
     *
     * 住房贷款初始化
     * @param file
     * @throws IOException
     */

    private void initHouseLoan(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> hoseLoanInfo=ExcelUtil.testReadExcel(file.getInputStream(), 5,file.getName());//住房贷款
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:hoseLoanInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            HouseLoanBean houseLoanBean = new HouseLoanBean();
            for(String cell:line){
                if(col ==0){
                    houseLoanBean.setTime(cell.trim());
                }
                if(col ==1){
                    houseLoanBean.setSum(cell.trim());
                }
                col++;
            }
            if(TeacherInitManager.getHouseLoan()==null){
                TeacherInitManager.setHouseLoan(new HouseLoan());
            }
            TeacherInitManager.getHouseLoan().getHouseLoanBeanList().add(houseLoanBean);

        }
    }


    /**
     *
     * 车贷款初始化
     * @param file
     * @throws IOException
     */

    private void initCarLoan(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> carLoanInfo=ExcelUtil.testReadExcel(file.getInputStream(), 6,file.getName());//车贷款
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:carLoanInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            CarLoanBean carLoanBean = new CarLoanBean();
            for(String cell:line){
                if(col ==0){
                    carLoanBean.setTime(cell.trim());
                }
                if(col ==1){
                    carLoanBean.setSum(cell.trim());
                }
                col++;
            }
            if(TeacherInitManager.getCarLoan()==null){
                TeacherInitManager.setCarLoan(new CarLoan());
            }
            TeacherInitManager.getCarLoan().getCarLoanBeanList().add(carLoanBean);

        }
    }

    /**
     * 综合贷款
     * @param file
     * @throws IOException
     */
    private void initOtherLoan(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> otherLoanInfo=ExcelUtil.testReadExcel(file.getInputStream(), 7,file.getName());//综合贷款
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:otherLoanInfo){
            lineNum++;
            if(lineNum==1){
                //timeList =line;
                continue;
            }
            int col =0;
            OtherLoanBean otherLoanBean = new OtherLoanBean();
            for(String cell:line){
                if(col ==0){
                    otherLoanBean.setTime(cell.trim());
                }
                if(col ==1){
                    otherLoanBean.setSum(cell.trim());
                }
                col++;
            }
            if(TeacherInitManager.getOtherLoan()==null){
                TeacherInitManager.setOtherLoan(new OtherLoan());
            }
            TeacherInitManager.getOtherLoan().getOtherLoanBeanList().add(otherLoanBean);

        }
    }


    /**
     * 初始化国债
     * @param file
     * @throws IOException
     */
    private void initContryDep(MultipartFile file) throws IOException{
        ArrayList<ArrayList<String>> otherLoanInfo=ExcelUtil.testReadExcel(file.getInputStream(), 8,file.getName());//国债
        int lineNum=0;
        // ArrayList<String> timeList =null;
        for(ArrayList<String> line:otherLoanInfo){
            lineNum++;
            if(lineNum==1){
                continue;
            }
            int col =0;
            CountryDepositBean countryDepositBean = new CountryDepositBean();
            for(String cell:line){
                if(col ==0){
                    countryDepositBean.setTime(cell.trim());
                }
                if(col ==1){
                    countryDepositBean.setSum(cell.trim());
                }
                if(col==2){
                    countryDepositBean.setRate(cell.trim());
                }
                col++;
            }
            if(TeacherInitManager.getContryDeposit()==null){
                TeacherInitManager.setContryDeposit(new ContryDeposit());
            }
            TeacherInitManager.getContryDeposit().getCountryDepositBeanList().add(countryDepositBean);

        }
    }




}
