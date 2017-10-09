package com.htsec.Student.init.bean;

public class BaseRateBean {
    private String DepositType;
    private String time;
    private String rate;

    public String getDepositType() {
        return DepositType;
    }

    public void setDepositType(String depositType) {
        DepositType = depositType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
