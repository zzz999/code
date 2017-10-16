package com.htsec.Student.beans;

import org.jcodings.util.Hash;

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

    private String cash; //现金
    private String freezeCash; //冻结的现金
    private String time; //时间
    private List<FHinfo> zhInfoList; //支行信息
    private HashMap<String,String> companyEvaluateInfo;  //公司评级

    private List<BankLoanForm> againLoanList;//再贷款
    private List<BankLoanForm> nationalLoanList;//国债
    private List<BankLoanForm> interBankBorrowingList;//同业拆借
    private List<BankLoanForm> financialBondsList;//金融债
    private HashMap<String,String> newIncreaseDepositMap;//<time,新增存款总额>
    private HashMap<String,String> totalDepositMap;//<time,贷款总额>



    public BankInfo() {
        this.companyEvaluateInfo = new HashMap<String,String>();
        this.zhInfoList = new ArrayList<>();
        this.zhInfoList.add(new FHinfo());
        this.againLoanList=new ArrayList<>();
        this.nationalLoanList=new ArrayList<>();
        this.interBankBorrowingList=new ArrayList<>();
        this.financialBondsList=new ArrayList<>();
        this.freezeCash="0";
        this.newIncreaseDepositMap = new HashMap<>();
        this.totalDepositMap = new HashMap<>();
        this.qdInfo = new QDInfo();
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
}
