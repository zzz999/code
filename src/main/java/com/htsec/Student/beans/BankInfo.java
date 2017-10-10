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
    private String time; //时间
    private List<FHinfo> zhInfoList; //支行信息
    private HashMap<String,String> companyEvaluateInfo;  //公司评级



    public BankInfo() {
        this.companyEvaluateInfo = new HashMap<String,String>();
        this.zhInfoList = new ArrayList<>();
        this.zhInfoList.add(new FHinfo());
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
}
