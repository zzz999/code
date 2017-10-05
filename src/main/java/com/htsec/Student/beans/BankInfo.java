package com.htsec.Student.beans;

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
}
