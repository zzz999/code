package com.htsec.Student.beans;

import com.htsec.Student.init.bean.TXOrderBean;
import com.htsec.Student.process.StudentInitManager;
import com.htsec.Student.process.TeacherInitManager;
import org.jcodings.util.Hash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bernard on 2017/9/26.
 */
public class BankInfo {
    private String name;
    private OrganizationInfo organizationInfo;
    private QDInfo qdInfo;
    private List<DepositInfo> depositInfoList;
    private List<LoanInfo> loanInfoList;
    private HashMap<String,PersonalDepositOrder> personalDepositOrderHashMap;

    private String cash; //现金
    private String freezeCash; //冻结的现金
    private String time; //时间
    private List<FHinfo> zhInfoList; //支行信息
    private HashMap<String,String> companyEvaluateInfo;  //公司评级


    private String affiliatedSchool; //所属学校
    private String ceoNames;
    private String cfoNames;
    private String cloNames;
    private String cmoNames;
    private String cpoNames;

    private String managementState;//用户经营状态 1经营中 2破产

    private List<BankLoanForm> againLoanList;//再贷款
    private List<BankLoanForm> nationalLoanList;//国债
    private List<BankLoanForm> interBankBorrowingList;//同业拆借
    private List<BankLoanForm> financialBondsList;//金融债
    private List<TXOrderBean> txOrderBeanList; //贴现订单
    private HashMap<String,String> newIncreaseDepositMap;//<time,新增存款总额>
    private HashMap<String,String> totalDepositMap;//<time,存款总额>
    private HashMap<String,String> totalLoanMap;//<time,贷款总额>
    private HashMap<String,String> newIncreaseLoanMap;//<time,存款>
    private HashMap<String,String> carLoanMap;//<time,carLoan>
    private HashMap<String,String> otherLoanMap;//<time,otherLoan>
    private HashMap<String,String> houseLoanMap;//<time,houseLoan>


    public BankInfo() {
        this.cash="5000";
        this.companyEvaluateInfo = new HashMap<String,String>();
        this.loanInfoList = new ArrayList<>();
        this.zhInfoList = new ArrayList<>();
        this.zhInfoList.add(new FHinfo());
        this.againLoanList=new ArrayList<>();
        this.nationalLoanList=new ArrayList<>();
        this.interBankBorrowingList=new ArrayList<>();
        this.financialBondsList=new ArrayList<>();
        this.freezeCash="0";
        this.time="1";
        this.managementState="1";
        this.newIncreaseDepositMap = new HashMap<>();
        this.totalDepositMap = new HashMap<>();
        this.qdInfo = new QDInfo();
        this.totalLoanMap = new HashMap<>();
        this.newIncreaseLoanMap = new HashMap<>();
        this.carLoanMap = new HashMap<>();
        this.otherLoanMap = new HashMap<>();
        this.houseLoanMap = new HashMap<>();
        this.personalDepositOrderHashMap=new HashMap<>();
    }

    public HashMap<String, String> getCarLoanMap() {
        return carLoanMap;
    }

    public void setCarLoanMap(HashMap<String, String> carLoanMap) {
        this.carLoanMap = carLoanMap;
    }

    public HashMap<String, String> getOtherLoanMap() {
        return otherLoanMap;
    }

    public void setOtherLoanMap(HashMap<String, String> otherLoanMap) {
        this.otherLoanMap = otherLoanMap;
    }

    public HashMap<String, String> getHouseLoanMap() {
        return houseLoanMap;
    }

    public void setHouseLoanMap(HashMap<String, String> houseLoanMap) {
        this.houseLoanMap = houseLoanMap;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public List<FHinfo> getZhInfoList() {
        return zhInfoList;
    }

    public void setZhInfoList(List<FHinfo> zhInfoList) {
        this.zhInfoList = zhInfoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganizationInfo getOrganizationInfo() {
        return organizationInfo;
    }

    public void setOrganizationInfo(OrganizationInfo organizationInfo) {
        this.organizationInfo = organizationInfo;
    }

    public QDInfo getQdInfo() {
        return qdInfo;
    }

    public void setQdInfo(QDInfo qdInfo) {
        this.qdInfo = qdInfo;
    }

    public List<DepositInfo> getDepositInfoList() {
        return depositInfoList;
    }

    public void setDepositInfoList(List<DepositInfo> depositInfoList) {
        this.depositInfoList = depositInfoList;
    }

    public List<LoanInfo> getLoanInfoList() {
        return loanInfoList;
    }

    public void setLoanInfoList(List<LoanInfo> loanInfoList) {
        this.loanInfoList = loanInfoList;
    }

    public HashMap<String, String> getCompanyEvaluateInfo() {
        return companyEvaluateInfo;
    }

    public void setCompanyEvaluateInfo(HashMap<String, String> companyEvaluateInfo) {
        this.companyEvaluateInfo = companyEvaluateInfo;
    }

    public List<BankLoanForm> getAgainLoanList() {
        return againLoanList;
    }

    public void setAgainLoanList(List<BankLoanForm> againLoanList) {
        this.againLoanList = againLoanList;
    }

    public List<BankLoanForm> getNationalLoanList() {
        return nationalLoanList;
    }

    public void setNationalLoanList(List<BankLoanForm> nationalLoanList) {
        this.nationalLoanList = nationalLoanList;
    }

    public List<BankLoanForm> getInterBankBorrowingList() {
        return interBankBorrowingList;
    }

    public void setInterBankBorrowingList(List<BankLoanForm> interBankBorrowingList) {
        this.interBankBorrowingList = interBankBorrowingList;
    }

    public List<BankLoanForm> getFinancialBondsList() {
        return financialBondsList;
    }

    public void setFinancialBondsList(List<BankLoanForm> financialBondsList) {
        this.financialBondsList = financialBondsList;
    }

    public String getFreezeCash() {
        return freezeCash;
    }

    public void setFreezeCash(String freezeCash) {
        this.freezeCash = freezeCash;
    }

    public HashMap<String, String> getNewIncreaseDepositMap() {
        return newIncreaseDepositMap;
    }

    public void setNewIncreaseDepositMap(HashMap<String, String> newIncreaseDepositMap) {
        this.newIncreaseDepositMap = newIncreaseDepositMap;
    }

    public HashMap<String, String> getTotalDepositMap() {
        return totalDepositMap;
    }

    public void setTotalDepositMap(HashMap<String, String> totalDepositMap) {
        this.totalDepositMap = totalDepositMap;
    }

    public String getAffiliatedSchool() {
        return affiliatedSchool;
    }

    public void setAffiliatedSchool(String affiliatedSchool) {
        this.affiliatedSchool = affiliatedSchool;
    }

    public String getCeoNames() {
        return ceoNames;
    }

    public void setCeoNames(String ceoNames) {
        this.ceoNames = ceoNames;
    }

    public String getCfoNames() {
        return cfoNames;
    }

    public void setCfoNames(String cfoNames) {
        this.cfoNames = cfoNames;
    }

    public String getCloNames() {
        return cloNames;
    }

    public void setCloNames(String cloNames) {
        this.cloNames = cloNames;
    }

    public String getCmoNames() {
        return cmoNames;
    }

    public void setCmoNames(String cmoNames) {
        this.cmoNames = cmoNames;
    }

    public String getCpoNames() {
        return cpoNames;
    }

    public void setCpoNames(String cpoNames) {
        this.cpoNames = cpoNames;
    }

    public String getManagementState() {
        return managementState;
    }

    public void setManagementState(String managementState) {
        this.managementState = managementState;
    }

    public HashMap<String, String> getTotalLoanMap() {
        return totalLoanMap;
    }

    public void setTotalLoanMap(HashMap<String, String> totalLoanMap) {
        this.totalLoanMap = totalLoanMap;
    }

    public HashMap<String, String> getNewIncreaseLoanMap() {
        return newIncreaseLoanMap;
    }

    public void setNewIncreaseLoanMap(HashMap<String, String> newIncreaseLoanMap) {
        this.newIncreaseLoanMap = newIncreaseLoanMap;
    }

    public List<TXOrderBean> getTxOrderBeanList() {
        return txOrderBeanList;
    }

    public void setTxOrderBeanList(List<TXOrderBean> txOrderBeanList) {
        this.txOrderBeanList = txOrderBeanList;
    }

    public void addDepositCash(BigDecimal addmoney, String year){
        BigDecimal maxDepositMoney = new BigDecimal("0");
        List<ZHInfo> listZH = this.zhInfoList.get(0).getZhInfoList();
        if(listZH ==null){
            return;
        }
        for(ZHInfo zhInfo:listZH){            if(zhInfo.getGroupInfo()!=null&&zhInfo.getGroupInfo().getDepositGroup()!=null
                    &&zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime()!=null
                    &&zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime()!=""
                    ){
                int groupBuiltTime=Integer.parseInt(zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime());
                if(groupBuiltTime!=0){
                    maxDepositMoney=maxDepositMoney.add(new BigDecimal(StudentInitManager.getGroupBuildRule().getDepositGroupIncrease()));
                }
            }

        }
        if(this.totalDepositMap.get(time)==null){
            this.totalDepositMap.put(time,"0");
        }
        String depositbyyear=this.totalDepositMap.get(time);
        if(new BigDecimal(depositbyyear).add(addmoney).compareTo(maxDepositMoney)<0){
            //更新当年存款总数
            this.totalDepositMap.put(time,new BigDecimal(depositbyyear).add(addmoney).toString());
            //更新当年新增存款总数
            if(this.newIncreaseDepositMap.get(time)==null){
                this.newIncreaseDepositMap.put(time,"0");
            }
            this.newIncreaseDepositMap.put(time,new BigDecimal(this.newIncreaseDepositMap.get(time)).add(addmoney).toString());
            //更新现金
            this.setCash(new BigDecimal(this.getCash()).add(addmoney).toString());
        }else {
            //更新当年存款总数
            this.totalDepositMap.put(time,maxDepositMoney.toString());
            //更新当年新增贷款
            if(this.newIncreaseDepositMap.get(time)==null){
                this.newIncreaseDepositMap.put(time,"0");
            }
            this.newIncreaseDepositMap.put(time,maxDepositMoney.subtract(new BigDecimal(depositbyyear)).toString());
            //更新现金
            this.setCash(new BigDecimal(this.getCash()).add(maxDepositMoney.subtract(new BigDecimal(depositbyyear))).toString());

        }


    }

    public void removeLoanCash(BigDecimal loanMoney){
        //TODO 校验当前现金
        //TODO 校验贷款金额限制条件
        BigDecimal maxLoanMoney = new BigDecimal("0");
        List<ZHInfo> listZH = this.zhInfoList.get(0).getZhInfoList();
        if(listZH ==null){
            return;
        }
        for(ZHInfo zhInfo:listZH){
            if(zhInfo.getGroupInfo()!=null&&zhInfo.getGroupInfo().getLoanGroup()!=null
                    &&zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime()!=null
                    &&zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime()!=""
                    ){
                int groupBuiltTime=Integer.parseInt(zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime());
                if(groupBuiltTime!=0){
                    maxLoanMoney.add(new BigDecimal(StudentInitManager.getGroupBuildRule().getLoanGroupIncrease()));
                }
            }
        }
        //判断是否超过最大贷款限额
        if(this.totalLoanMap.get(time)==null){
            this.totalLoanMap.put(time,"0");
        }
        String loanByyear = totalLoanMap.get(time);
        if(new BigDecimal(loanByyear).add(loanMoney).compareTo(maxLoanMoney)<0){
            //没有超过最大贷款数
            if(new BigDecimal(this.getCash()).compareTo(loanMoney)<0){
                //现金不足
                //更新新增贷款
                if(this.newIncreaseLoanMap.get(time)!=null){
                    this.newIncreaseLoanMap.put(time,"0");
                }
                this.newIncreaseLoanMap.put(time,new BigDecimal(this.newIncreaseLoanMap.get(time)).add(new BigDecimal(this.getCash())).toString());
                //更新总贷款
                this.totalLoanMap.put(time,new BigDecimal(this.totalLoanMap.get(time)).add(new BigDecimal(this.getCash())).toString());

                //更新现金
                this.setCash("0");

            }else {
                //现金足够
                if(this.newIncreaseLoanMap.get(time)==null){
                    this.newIncreaseLoanMap.put(time,"0");
                }
                //更新新增贷款
                this.newIncreaseLoanMap.put(time,new BigDecimal(this.newIncreaseLoanMap.get(time)).add(loanMoney).toString());
                //更新总贷款
                this.totalLoanMap.put(time,new BigDecimal(this.totalLoanMap.get(time)).add(loanMoney).toString());
                //更新现金
                this.setCash(new BigDecimal(this.getCash()).subtract(loanMoney).toString());

            }
        }else {
            //超过最大贷款数
            BigDecimal loanCan = maxLoanMoney.subtract(new BigDecimal(loanByyear));
            if(loanCan.compareTo(new BigDecimal(this.cash))<0){
                //有钱
                //更新新增贷款
                if(this.newIncreaseLoanMap.get(time)==null){
                    this.newIncreaseLoanMap.put(time,"0");
                }
                this.newIncreaseLoanMap.put(time,new BigDecimal(this.newIncreaseLoanMap.get(time)).add(loanCan).toString());
                //更新贷款总额
                this.totalLoanMap.put(time,new BigDecimal(this.totalLoanMap.get(time)).add(loanCan).toString());
                //更新现金
                this.setCash(new BigDecimal(this.getCash()).subtract(loanCan).toString());

            }else {
                //没钱
                if(this.newIncreaseLoanMap.get(time)!=null){
                    this.newIncreaseLoanMap.put(time,"0");
                }
                this.newIncreaseLoanMap.put(time,new BigDecimal(this.newIncreaseLoanMap.get(time)).add(new BigDecimal(this.getCash())).toString());
                //更新总贷款
                this.totalLoanMap.put(time,new BigDecimal(this.totalLoanMap.get(time)).add(new BigDecimal(this.getCash())).toString());

                //更新现金
                this.setCash("0");
            }

        }
        this.setCash(new BigDecimal(this.getCash()).subtract(loanMoney).toString());
    }

}
