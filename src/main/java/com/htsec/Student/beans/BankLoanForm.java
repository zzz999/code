package com.htsec.Student.beans;

import java.util.UUID;

/**
 * 银行贷款表单
 * Created by zzz on 2017/10/10.
 */
public class BankLoanForm {
    private String id;
    public BankLoanForm(){
        this.id=UUID.randomUUID().toString().replaceAll("-", "");
    }
    //贷款人的code
    private String loanCode;
    //借款人的code
    private String buyCode;
    //1 申请再贷款、2 国债管理、3 金融债管理、4 同业拆借
    private String type;
    //审核状态
    private Boolean audit=false;
    private String money;
    private String rate;
    private String startTime;
    private String endTime;

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getBuyCode() {
        return buyCode;
    }

    public void setBuyCode(String buyCode) {
        this.buyCode = buyCode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
