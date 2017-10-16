package com.htsec.Student.init.bean;

public class CompanyDepositBean {
    private String time;
    private String sum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public CompanyDepositBean(){
        this.time ="0";
        this.sum="0";
    }
}
