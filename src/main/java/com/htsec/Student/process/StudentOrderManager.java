package com.htsec.Student.process;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.htsec.Student.beans.*;
import com.htsec.Student.init.bean.*;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.jcodings.util.Hash;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentOrderManager {
    private static HashMap<String,HashMap<String,CompanyLoanOrder>> companLoanOrderMap = new HashMap<>();
    private static HashMap<String,HashMap<String,CompanyDepositOrder>> companyDepositOrderMap =new HashMap<>();
    private static HashMap<String,HashMap<String,PersonalDepositOrder>> personalDepositOrderMap = new HashMap<>();
    private static HashMap<String,HashMap<String,PersonalLoanOrder>> personalLoanOrderMap = new HashMap<>();
    private static HashMap<String,HashMap<String,BigDecimal>> companyDepositMoneyMap = new HashMap<>();
    private static HashMap<String,HashMap<String,BigDecimal>> personalDepositMoneyMap = new HashMap<>();
    private static HashMap<String,HashMap<String,BigDecimal>> houseLoanMoneyMap = new HashMap<>();
    private static HashMap<String,HashMap<String,BigDecimal>> carLoanMoneyMap = new HashMap<>();
    private static HashMap<String,HashMap<String,BigDecimal>> otherLoanMoneyMap= new HashMap<>();
    private static HashMap<String,CompanyLoanProcess> companyLongLoanProcessHashMap = new HashMap<>();
    private static HashMap<String,CompanyLoanProcess> companyShortLoanProcessHashMap = new HashMap<>();


    public static HashMap<String, HashMap<String, CompanyLoanOrder>> getCompanLoanOrderMap() {
        return companLoanOrderMap;
    }

    public static void setCompanLoanOrderMap(HashMap<String, HashMap<String, CompanyLoanOrder>> companLoanOrderMap) {
        StudentOrderManager.companLoanOrderMap = companLoanOrderMap;
    }

    public static HashMap<String, HashMap<String, CompanyDepositOrder>> getCompanyDepositOrderMap() {
        return companyDepositOrderMap;
    }

    public static void setCompanyDepositOrderMap(HashMap<String, HashMap<String, CompanyDepositOrder>> companyDepositOrderMap) {
        StudentOrderManager.companyDepositOrderMap = companyDepositOrderMap;
    }

    public static HashMap<String, HashMap<String, PersonalDepositOrder>> getPersonalDepositOrderMap() {
        return personalDepositOrderMap;
    }

    public static void setPersonalDepositOrderMap(HashMap<String, HashMap<String, PersonalDepositOrder>> personalDepositOrderMap) {
        StudentOrderManager.personalDepositOrderMap = personalDepositOrderMap;
    }

    public static HashMap<String, HashMap<String, PersonalLoanOrder>> getPersonalLoanOrderMap() {
        return personalLoanOrderMap;
    }

    public static void setPersonalLoanOrderMap(HashMap<String, HashMap<String, PersonalLoanOrder>> personalLoanOrderMap) {
        StudentOrderManager.personalLoanOrderMap = personalLoanOrderMap;
    }

    public static HashMap<String, HashMap<String, BigDecimal>> getCompanyDepositMoneyMap() {
        return companyDepositMoneyMap;
    }

    public static void setCompanyDepositMoneyMap(HashMap<String, HashMap<String, BigDecimal>> companyDepositMoneyMap) {
        StudentOrderManager.companyDepositMoneyMap = companyDepositMoneyMap;
    }

    public static HashMap<String, HashMap<String, BigDecimal>> getPersonalDepositMoneyMap() {
        return personalDepositMoneyMap;
    }

    public static void setPersonalDepositMoneyMap(HashMap<String, HashMap<String, BigDecimal>> personalDepositMoneyMap) {
        StudentOrderManager.personalDepositMoneyMap = personalDepositMoneyMap;
    }

    /**
     * 分配企业存款
     * @param year
     */
    public static boolean dispathCompanyDepositOrder(String year){
        HashMap<String,CompanyDepositOrder> companyDepositOrderHashMap = companyDepositOrderMap.get(year);
        String bigRate="0";
        String bigAdmoney="0";
        String bigLastyearNewIncreaseDeposit="0";
        String bigLastyearTotalDeposit="0";
        for(Map.Entry<String,CompanyDepositOrder> entry:companyDepositOrderHashMap.entrySet()){
            String code =entry.getKey();
            CompanyDepositOrder companyDepositOrder =entry.getValue();
            String rate =companyDepositOrder.getCompanyDepositRate();
            String ADmoney =companyDepositOrder.getCompanyDepositADmoney();
            BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
            if(bankInfo==null){
                return false;
            }
            String lasterYearNewIncreaseDeposit=bankInfo.getNewIncreaseDepositMap().get((Integer.parseInt(year)-1)+"");
            String lastYearTotalDepost  =bankInfo.getTotalDepositMap().get((Integer.parseInt(year)-1)+"");
            if(lasterYearNewIncreaseDeposit ==null){
                lasterYearNewIncreaseDeposit ="0";
            }
            if(lastYearTotalDepost ==null){
                lastYearTotalDepost="0";
            }
            if(new BigDecimal(bigRate).compareTo(new BigDecimal(rate))<0){
                bigRate = rate;
            }
            if(new BigDecimal(bigAdmoney).compareTo(new BigDecimal(ADmoney))<0){
                bigAdmoney = ADmoney;
            }
            if(new BigDecimal(bigLastyearNewIncreaseDeposit).compareTo(new BigDecimal(lasterYearNewIncreaseDeposit))<0){
                bigLastyearNewIncreaseDeposit =lasterYearNewIncreaseDeposit;
            }
            if(new BigDecimal(bigLastyearTotalDeposit).compareTo(new BigDecimal(lastYearTotalDepost))<0){
                bigLastyearTotalDeposit =lastYearTotalDepost;
            }
        }

        //计算分数
        BigDecimal totalSocre =new BigDecimal("0");
        HashMap<String ,BigDecimal> scoreHashMap =new HashMap<>();
        for(Map.Entry<String,CompanyDepositOrder> entry:companyDepositOrderHashMap.entrySet()){
            String code =entry.getKey();
            CompanyDepositOrder companyDepositOrder =entry.getValue();
            String rate =companyDepositOrder.getCompanyDepositADmoney();
            String ADmoney =companyDepositOrder.getCompanyDepositADmoney();
            BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
            if(bankInfo==null){
                return false;
            }
            String lasterYearNewIncreaseDeposit=bankInfo.getNewIncreaseDepositMap().get((Integer.parseInt(year)-1)+"");
            String lastYearTotalDepost  =bankInfo.getTotalDepositMap().get((Integer.parseInt(year)-1)+"");
            BigDecimal rateScore=calcScore(rate,bigRate).multiply(new BigDecimal(50));
            BigDecimal ADmoneyScore =calcScore(ADmoney,bigAdmoney).multiply(new BigDecimal(20));
            BigDecimal lasterYearNewIncreaseDepositSocre=calcScore(lasterYearNewIncreaseDeposit,bigLastyearNewIncreaseDeposit).multiply(new BigDecimal("15"));
            BigDecimal lastYearTotalDepostScore = calcScore(lastYearTotalDepost,bigLastyearTotalDeposit).multiply(new BigDecimal("15"));
            int mobileBank=Integer.parseInt(bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime());
            int mobileBankConstant =Integer.parseInt(StudentInitManager.getMarketingBuildRule().getMobilephoneBankTime());
            int netBank=Integer.parseInt(bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime());
            int netBankConstant =Integer.parseInt(StudentInitManager.getMarketingBuildRule().getNetBankTime());
            BigDecimal mobileBankScore = new BigDecimal("0");
            if(mobileBank!=0&&(Integer.parseInt(year)-mobileBank)<=mobileBankConstant){
                mobileBankScore = new BigDecimal("0.1");
            }
            BigDecimal netBankScore = new BigDecimal("0");
            if(netBank!=0&&(Integer.parseInt(year)-netBank)<=netBankConstant){
                netBankScore = new BigDecimal("0.2");
            }
            BigDecimal bankMutil= (new BigDecimal("1")).add(mobileBankScore).add(netBankScore);
            BigDecimal bankTotalScore=(rateScore.add(ADmoneyScore).add(lasterYearNewIncreaseDepositSocre).add(lastYearTotalDepostScore)).multiply(bankMutil);
            totalSocre = totalSocre.add(bankTotalScore);
            scoreHashMap.put(code,bankTotalScore);
        }
        //分配资金
        BigDecimal MoneyToDeposit = new BigDecimal("0");

        for(CompanyDepositBean companyDepositBean:TeacherInitManager.getCompanyDeposit().getCompanyDepositBeanList()){
            if(companyDepositBean.getTime().trim().equalsIgnoreCase(year.trim())){
                MoneyToDeposit = new BigDecimal(companyDepositBean.getSum());
            }
        }
        for(Map.Entry<String,BigDecimal> entry: scoreHashMap.entrySet()){
            if(companyDepositMoneyMap.get(year)==null){
                companyDepositMoneyMap.put(year,new HashMap<>());
            }
            companyDepositMoneyMap.get(year).put(entry.getKey(),entry.getValue().divide(totalSocre,8,BigDecimal.ROUND_HALF_UP).multiply(MoneyToDeposit));
        }
        return true;

    }

    public static BigDecimal calcScore(String now ,String big){
        if(now==null||big==null){
            return new BigDecimal("1");
        }

        BigDecimal nowDeci = new BigDecimal(now);
        BigDecimal bigDeci = new BigDecimal(big);
        if(big ==""||big=="0"){
            return new BigDecimal("1");
        }else {
            return nowDeci.divide(bigDeci,8,BigDecimal.ROUND_HALF_UP);
        }
    }



    /**
     * 分配个人存款
     * @param year
     */
    public static boolean dispatchPersonalDepositOrder(String year){
        //personalDepositOrderMap.get(year);
        HashMap<String,PersonalDepositOrder> personalDepositOrderHashMap = personalDepositOrderMap.get(year);
        String bigRate="0";
        String bigAdmoney="0";
        String bigLastyearNewIncreaseDeposit="0";
        String bigLastyearTotalDeposit="0";
        for(Map.Entry<String,PersonalDepositOrder> entry:personalDepositOrderHashMap.entrySet()){
            String code =entry.getKey();
            PersonalDepositOrder personalDepositOrder =entry.getValue();
            String rate =personalDepositOrder.getOrderRate();
            String ADmoney =personalDepositOrder.getADmoney();
            BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
            if(bankInfo==null){
                return false;
            }
            String lasterYearNewIncreaseDeposit=bankInfo.getNewIncreaseDepositMap().get((Integer.parseInt(year)-1)+"");
            String lastYearTotalDepost  =bankInfo.getTotalDepositMap().get((Integer.parseInt(year)-1)+"");
            if(lasterYearNewIncreaseDeposit ==null){
                lasterYearNewIncreaseDeposit ="0";
            }
            if(lastYearTotalDepost ==null){
                lastYearTotalDepost="0";
            }
            if(new BigDecimal(bigRate).compareTo(new BigDecimal(rate))<0){
                bigRate = rate;
            }
            if(new BigDecimal(bigAdmoney).compareTo(new BigDecimal(ADmoney))<0){
                bigAdmoney = ADmoney;
            }
            if(new BigDecimal(bigLastyearNewIncreaseDeposit).compareTo(new BigDecimal(lasterYearNewIncreaseDeposit))<0){
                bigLastyearNewIncreaseDeposit =lasterYearNewIncreaseDeposit;
            }
            if(new BigDecimal(bigLastyearTotalDeposit).compareTo(new BigDecimal(lastYearTotalDepost))<0){
                bigLastyearTotalDeposit =lastYearTotalDepost;
            }
        }

        //计算分数
        BigDecimal totalSocre =new BigDecimal("0");
        HashMap<String ,BigDecimal> scoreHashMap =new HashMap<>();
        for(Map.Entry<String,PersonalDepositOrder> entry:personalDepositOrderHashMap.entrySet()){
            String code =entry.getKey();
            PersonalDepositOrder personalDepositOrder =entry.getValue();
            String rate =personalDepositOrder.getOrderRate();
            String ADmoney =personalDepositOrder.getADmoney();
            BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
            if(bankInfo==null){
                return false;
            }
            String lasterYearNewIncreaseDeposit=bankInfo.getNewIncreaseDepositMap().get((Integer.parseInt(year)-1)+"");
            String lastYearTotalDepost  =bankInfo.getTotalDepositMap().get((Integer.parseInt(year)-1)+"");
            BigDecimal rateScore=calcScore(rate,bigRate).multiply(new BigDecimal(50));
            BigDecimal ADmoneyScore =calcScore(ADmoney,bigAdmoney).multiply(new BigDecimal(20));
            BigDecimal lasterYearNewIncreaseDepositSocre=calcScore(lasterYearNewIncreaseDeposit,bigLastyearNewIncreaseDeposit).multiply(new BigDecimal("15"));
            BigDecimal lastYearTotalDepostScore = calcScore(lastYearTotalDepost,bigLastyearTotalDeposit).multiply(new BigDecimal("15"));
            int mobileBank=Integer.parseInt(bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime());
            int mobileBankConstant =Integer.parseInt(StudentInitManager.getMarketingBuildRule().getMobilephoneBankTime());
            int netBank=Integer.parseInt(bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime());
            int netBankConstant =Integer.parseInt(StudentInitManager.getMarketingBuildRule().getNetBankTime());
            BigDecimal mobileBankScore = new BigDecimal("0");
            if(mobileBank!=0&&(Integer.parseInt(year)-mobileBank)<=mobileBankConstant){
                mobileBankScore = new BigDecimal("0.1");
            }
            BigDecimal netBankScore = new BigDecimal("0");
            if(netBank!=0&&(Integer.parseInt(year)-netBank)<=netBankConstant){
                netBankScore = new BigDecimal("0.2");
            }
            BigDecimal bankMutil= (new BigDecimal("1")).add(mobileBankScore).add(netBankScore);
            BigDecimal bankTotalScore=(rateScore.add(ADmoneyScore).add(lasterYearNewIncreaseDepositSocre).add(lastYearTotalDepostScore)).multiply(bankMutil);
            totalSocre = totalSocre.add(bankTotalScore);
            scoreHashMap.put(code,bankTotalScore);
        }
        //分配资金
        BigDecimal MoneyToDeposit = new BigDecimal("0");

        for(PersonalDepositBean personalDepositBean:TeacherInitManager.getPersonalDeposit().getPersonalDepositBeanList()){
            if(personalDepositBean.getTime().trim().equalsIgnoreCase(year.trim())){
                MoneyToDeposit = new BigDecimal(personalDepositBean.getSum());
            }
        }
        for(Map.Entry<String,BigDecimal> entry: scoreHashMap.entrySet()){
            if(personalDepositMoneyMap.get(year)==null){
                personalDepositMoneyMap.put(year,new HashMap<>());
            }
            personalDepositMoneyMap.get(year).put(entry.getKey(),entry.getValue().divide(totalSocre,6,BigDecimal.ROUND_HALF_UP).multiply(MoneyToDeposit));
        }
        return true;
    }

    //分配住房消费贷款
    /**
     * 分配住房消费贷款
     * @param year
     */
    public static boolean dispatchHouseLoanOrder(String year){

        HashMap<String,PersonalLoanOrder> personalLoanOrderHashMap = personalLoanOrderMap.get(year);
        String houseLoanbigRate="0";
        String houseLoanbigAdmoney="0";
        String carLoanbigRate="0";
        String carLoanbigAdmoney="0";
        String otherLoanbigRate="0";
        String otherLoanbigAdmoney="0";

        String bigLastyearNewIncreaseDeposit="0";
        String bigLastyearTotalDeposit="0";
        for(Map.Entry<String,PersonalLoanOrder> entry:personalLoanOrderHashMap.entrySet()){
            String code =entry.getKey();
            PersonalLoanOrder personalLoanOrder =entry.getValue();
            String houseLoanRate =personalLoanOrder.getHouseLoanRate();
            String houseADmoney =personalLoanOrder.getHouseLoanADmoney();
            String carLoanRate =personalLoanOrder.getCarLoanRate();
            String carLoanADmoney=personalLoanOrder.getCarLoanADmoney();
            String otherLoanADmoney=personalLoanOrder.getOtherLoanADmoney();
            String otherLoanRate =personalLoanOrder.getOtherLoanRate();
            BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
            if(bankInfo==null){
                return false;
            }
            String lasterYearNewIncreaseDeposit=bankInfo.getNewIncreaseDepositMap().get((Integer.parseInt(year)-1)+"");
            String lastYearTotalDepost  =bankInfo.getTotalDepositMap().get((Integer.parseInt(year)-1)+"");
            if(lasterYearNewIncreaseDeposit ==null){
                lasterYearNewIncreaseDeposit ="0";
            }
            if(lastYearTotalDepost ==null){
                lastYearTotalDepost="0";
            }
            if(new BigDecimal(houseLoanbigRate).compareTo(new BigDecimal(houseLoanRate))<0){
                houseLoanbigRate = houseLoanRate;
            }
            if(new BigDecimal(houseLoanbigAdmoney).compareTo(new BigDecimal(houseADmoney))<0){
                houseLoanbigAdmoney = houseADmoney;
            }

            if(new BigDecimal(carLoanbigRate).compareTo(new BigDecimal(carLoanRate))<0){
                carLoanbigRate=carLoanRate;
            }
            if(new BigDecimal(carLoanbigAdmoney).compareTo(new BigDecimal(carLoanADmoney))<0){
                carLoanbigAdmoney =carLoanADmoney;
            }

            if(new BigDecimal(otherLoanbigRate).compareTo(new BigDecimal(otherLoanRate))<0){
                otherLoanbigRate=otherLoanRate;
            }

            if(new BigDecimal(otherLoanbigAdmoney).compareTo(new BigDecimal(otherLoanADmoney))<0){
                otherLoanbigAdmoney = otherLoanADmoney;
            }


            if(new BigDecimal(bigLastyearNewIncreaseDeposit).compareTo(new BigDecimal(lasterYearNewIncreaseDeposit))<0){
                bigLastyearNewIncreaseDeposit =lasterYearNewIncreaseDeposit;
            }
            if(new BigDecimal(bigLastyearTotalDeposit).compareTo(new BigDecimal(lastYearTotalDepost))<0){
                bigLastyearTotalDeposit =lastYearTotalDepost;
            }
        }

        //计算分数
        BigDecimal houseLoantotalSocre =new BigDecimal("0");
        BigDecimal carLoantotalScore = new BigDecimal("0");
        BigDecimal otherLoantatalScore = new BigDecimal("0");
        HashMap<String ,BigDecimal> housescoreHashMap =new HashMap<>();
        HashMap<String ,BigDecimal> carscoreHashMap =new HashMap<>();
        HashMap<String ,BigDecimal> ohterscoreHashMap =new HashMap<>();
        for(Map.Entry<String,PersonalLoanOrder> entry:personalLoanOrderHashMap.entrySet()){
            String code =entry.getKey();
            PersonalLoanOrder personalLoanOrder =entry.getValue();
            String houserate =personalLoanOrder.getHouseLoanRate();
            String houseADmoney =personalLoanOrder.getHouseLoanADmoney();
            String carrate= personalLoanOrder.getCarLoanRate();
            String carAdmoney =personalLoanOrder.getCarLoanADmoney();
            String otherRate =personalLoanOrder.getOtherLoanRate();
            String otherLoanADmoney =personalLoanOrder.getOtherLoanADmoney();

            BankInfo bankInfo=StudentProcessManager.getBankInfoHashMap().get(code);
            if(bankInfo==null){
                return false;
            }
            String lasterYearNewIncreaseDeposit=bankInfo.getNewIncreaseDepositMap().get((Integer.parseInt(year)-1)+"");
            String lastYearTotalDepost  =bankInfo.getTotalDepositMap().get((Integer.parseInt(year)-1)+"");
            BigDecimal houserateScore=calcScore(houserate,houseLoanbigRate).multiply(new BigDecimal(50));
            BigDecimal carLoanRateScore =calcScore(carrate,carLoanbigRate).multiply(new BigDecimal(50));
            BigDecimal otherLoanRateScore=calcScore(otherRate,otherLoanbigRate).multiply(new BigDecimal(50));
            BigDecimal houseADmoneyScore =calcScore(houseADmoney,houseLoanbigAdmoney).multiply(new BigDecimal(20));
            BigDecimal carLoanADmoneyScore = calcScore(carAdmoney,carLoanbigAdmoney).multiply(new BigDecimal(20));
            BigDecimal otherLoanADmoneyScore= calcScore(otherLoanADmoney,otherLoanbigAdmoney).multiply(new BigDecimal(20));
            BigDecimal lasterYearNewIncreaseDepositSocre=calcScore(lasterYearNewIncreaseDeposit,bigLastyearNewIncreaseDeposit).multiply(new BigDecimal("15"));
            BigDecimal lastYearTotalDepostScore = calcScore(lastYearTotalDepost,bigLastyearTotalDeposit).multiply(new BigDecimal("15"));
            int mobileBank=Integer.parseInt(bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime());
            int mobileBankConstant =Integer.parseInt(StudentInitManager.getMarketingBuildRule().getMobilephoneBankTime());
            int netBank=Integer.parseInt(bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime());
            int netBankConstant =Integer.parseInt(StudentInitManager.getMarketingBuildRule().getNetBankTime());
            BigDecimal mobileBankScore = new BigDecimal("0");
            if(mobileBank!=0&&(Integer.parseInt(year)-mobileBank)<=mobileBankConstant){
                mobileBankScore = new BigDecimal("0.05");
            }
            BigDecimal netBankScore = new BigDecimal("0");
            if(netBank!=0&&(Integer.parseInt(year)-netBank)<=netBankConstant){
                netBankScore = new BigDecimal("0.1");
            }
            BigDecimal bankMutil= (new BigDecimal("1")).add(mobileBankScore).add(netBankScore);
            BigDecimal bankhouseLoanTotalScore=(houserateScore.add(houseADmoneyScore).add(lasterYearNewIncreaseDepositSocre).add(lastYearTotalDepostScore)).multiply(bankMutil);
            BigDecimal bankcarLoanTotalScore=(carLoanRateScore.add(carLoanADmoneyScore).add(lasterYearNewIncreaseDepositSocre).add(lastYearTotalDepostScore)).multiply(bankMutil);
            BigDecimal bankotherLoanTotalScore=(otherLoanRateScore.add(otherLoanADmoneyScore).add(lasterYearNewIncreaseDepositSocre).add(lastYearTotalDepostScore)).multiply(bankMutil);
            houseLoantotalSocre = houseLoantotalSocre.add(bankhouseLoanTotalScore);
            carLoantotalScore=carLoantotalScore.add(bankcarLoanTotalScore);
            otherLoantatalScore=otherLoantatalScore.add(bankotherLoanTotalScore);
            housescoreHashMap.put(code,bankhouseLoanTotalScore);
            carscoreHashMap.put(code,bankcarLoanTotalScore);
            ohterscoreHashMap.put(code,bankotherLoanTotalScore);
        }
        //分配资金
        BigDecimal houseMoneyToDeposit = new BigDecimal("0");
        BigDecimal carMoneyToDeposit = new BigDecimal("0");
        BigDecimal otherMoneyToDeposit = new BigDecimal("0");

        for(HouseLoanBean houseLoanBean:TeacherInitManager.getHouseLoan().getHouseLoanBeanList()){
            if(houseLoanBean.getTime().trim().equalsIgnoreCase(year.trim())){
                houseMoneyToDeposit = new BigDecimal(houseLoanBean.getSum());
            }
        }
        for(CarLoanBean carLoanBean:TeacherInitManager.getCarLoan().getCarLoanBeanList()){
            if(carLoanBean.getTime().trim().equalsIgnoreCase(year.trim())){
                carMoneyToDeposit = new BigDecimal(carLoanBean.getSum());
            }
        }
        for(OtherLoanBean otherLoanBean:TeacherInitManager.getOtherLoan().getOtherLoanBeanList()){
            if(otherLoanBean.getTime().trim().equalsIgnoreCase(year.trim())){
                otherMoneyToDeposit = new BigDecimal(otherLoanBean.getSum());
            }
        }

        for(Map.Entry<String,BigDecimal> entry: housescoreHashMap.entrySet()){
            if(houseLoanMoneyMap.get(year)==null){
                houseLoanMoneyMap.put(year,new HashMap<>());
            }
            houseLoanMoneyMap.get(year).put(entry.getKey(),entry.getValue().divide(houseLoantotalSocre,8,BigDecimal.ROUND_HALF_UP).multiply(houseMoneyToDeposit));
        }

        for(Map.Entry<String,BigDecimal> entry: carscoreHashMap.entrySet()){
            if(carLoanMoneyMap.get(year)==null){
                carLoanMoneyMap.put(year,new HashMap<>());
            }
            carLoanMoneyMap.get(year).put(entry.getKey(),entry.getValue().divide(carLoantotalScore,8,BigDecimal.ROUND_HALF_UP).multiply(carMoneyToDeposit));
        }

        for(Map.Entry<String,BigDecimal> entry: ohterscoreHashMap.entrySet()){
            if(otherLoanMoneyMap.get(year)==null){
                otherLoanMoneyMap.put(year,new HashMap<>());
            }
            otherLoanMoneyMap.get(year).put(entry.getKey(),entry.getValue().divide(otherLoantatalScore,8,BigDecimal.ROUND_HALF_UP).multiply(otherMoneyToDeposit));
        }


        return true;
    }



    public static boolean startCompanyLongLoanProcess(String year){
        HashMap<String,CompanyLoanOrder> companyLoanOrderHashMap= companLoanOrderMap.get(year);
        if(companyLoanOrderHashMap.size()<StudentProcessManager.getBankInfoHashMap().size()){
            return false;
        }
        if(companyLongLoanProcessHashMap.get(year)!=null){
            return false;
        }
        companyLongLoanProcessHashMap.put(year,new CompanyLoanProcess());
        CompanyLoanProcess process= companyLongLoanProcessHashMap.get(year);

        QYLongOrder qyLongOrderByYear = new QYLongOrder();
        for(QYLongOrderBean qyLongOrderBean:TeacherInitManager.getQyLongOrder().getLongOrders()){
            if(qyLongOrderBean.getTime().trim().equalsIgnoreCase(year)){
                qyLongOrderByYear.getLongOrders().add(qyLongOrderBean);
            }
        }

        for(Map.Entry<String,CompanyLoanOrder> entry:companyLoanOrderHashMap.entrySet()){
            StudentOrderSelectBean studentOrderSelectBean = new StudentOrderSelectBean();
            studentOrderSelectBean.setCode(entry.getKey());
            studentOrderSelectBean.setType("0");
            BigDecimal round= new BigDecimal(entry.getValue().getCompanyLongLoanOrderADmoney()).subtract(new BigDecimal("10")).divide(new BigDecimal("50"),8,BigDecimal.ROUND_HALF_UP);
            studentOrderSelectBean.setTimeRemain(round.intValue());
            studentOrderSelectBean.setRate(entry.getValue().getCompanyLongLoanOrderRate());
            process.getStudentOrderSelectBeanList().add(studentOrderSelectBean);
            process.setQyLongOrder(qyLongOrderByYear);
        }

        Collections.sort(process.getStudentOrderSelectBeanList());
        process.setNowCode(process.getStudentOrderSelectBeanList().get(0).getCode());
        if(process.getStudentOrderSelectBeanList().size()<=1){
            process.setNext(0);
        }else {
            process.setNext(1);
        }
        process.setStart(true);
        return true;
    }


    public static boolean startCompanyShortLoanProcess(String year){
        HashMap<String,CompanyLoanOrder> companyLoanOrderHashMap= companLoanOrderMap.get(year);
        if(companyLoanOrderHashMap.size()<StudentProcessManager.getBankInfoHashMap().size()){
            return false;
        }

        if(companyShortLoanProcessHashMap.get(year)!=null){
            return false;
        }
        companyShortLoanProcessHashMap.put(year,new CompanyLoanProcess());
        CompanyLoanProcess process= companyShortLoanProcessHashMap.get(year);
        QYShortOrder qyShortOrderByYear = new QYShortOrder();

        for(QYShortOrderBean qyShortOrderBean:TeacherInitManager.getQyShortOrder().getShortOrderBeans()){
            if(qyShortOrderBean.getTime().trim().equalsIgnoreCase(year)){
                qyShortOrderByYear.getShortOrderBeans().add(qyShortOrderBean);
            }
        }

        for(Map.Entry<String,CompanyLoanOrder> entry:companyLoanOrderHashMap.entrySet()){
            StudentOrderSelectBean studentOrderSelectBean = new StudentOrderSelectBean();
            studentOrderSelectBean.setCode(entry.getKey());
            studentOrderSelectBean.setType("1");
            BigDecimal round= (new BigDecimal(entry.getValue().getCompanyShortLoanADmoney()).subtract(new BigDecimal("10"))).divide(new BigDecimal("50"),8,BigDecimal.ROUND_HALF_UP);
            //增加短贷营销费
            studentOrderSelectBean.setAdmoney(entry.getValue().getCompanyShortLoanADmoney());
            studentOrderSelectBean.setTimeRemain(round.intValue());
            studentOrderSelectBean.setRate(entry.getValue().getCompanyShortLoanRate());
            process.getStudentOrderSelectBeanList().add(studentOrderSelectBean);
            process.setQyShortOrder(qyShortOrderByYear);
        }

        Collections.sort(process.getStudentOrderSelectBeanList());
        process.setNowCode(process.getStudentOrderSelectBeanList().get(0).getCode());
        if(process.getStudentOrderSelectBeanList().size()<=1){
            process.setNext(0);
        }else {
            process.setNext(1);
        }
        process.setStart(true);
        return true;
    }


    public static HashMap<String, HashMap<String, BigDecimal>> getHouseLoanMoneyMap() {
        return houseLoanMoneyMap;
    }

    public static void setHouseLoanMoneyMap(HashMap<String, HashMap<String, BigDecimal>> houseLoanMoneyMap) {
        StudentOrderManager.houseLoanMoneyMap = houseLoanMoneyMap;
    }

    public static HashMap<String, HashMap<String, BigDecimal>> getCarLoanMoneyMap() {
        return carLoanMoneyMap;
    }

    public static void setCarLoanMoneyMap(HashMap<String, HashMap<String, BigDecimal>> carLoanMoneyMap) {
        StudentOrderManager.carLoanMoneyMap = carLoanMoneyMap;
    }

    public static HashMap<String, HashMap<String, BigDecimal>> getOtherLoanMoneyMap() {
        return otherLoanMoneyMap;
    }

    public static void setOtherLoanMoneyMap(HashMap<String, HashMap<String, BigDecimal>> otherLoanMoneyMap) {
        StudentOrderManager.otherLoanMoneyMap = otherLoanMoneyMap;
    }

    public static HashMap<String, CompanyLoanProcess> getCompanyLongLoanProcessHashMap() {
        return companyLongLoanProcessHashMap;
    }

    public static void setCompanyLongLoanProcessHashMap(HashMap<String, CompanyLoanProcess> companyLongLoanProcessHashMap) {
        StudentOrderManager.companyLongLoanProcessHashMap = companyLongLoanProcessHashMap;
    }

    public static HashMap<String, CompanyLoanProcess> getCompanyShortLoanProcessHashMap() {
        return companyShortLoanProcessHashMap;
    }

    public static void setCompanyShortLoanProcessHashMap(HashMap<String, CompanyLoanProcess> companyShortLoanProcessHashMap) {
        StudentOrderManager.companyShortLoanProcessHashMap = companyShortLoanProcessHashMap;
    }
}
