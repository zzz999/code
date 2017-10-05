package com.htsec.Student.init.bean;

public class CountryDepositBean {

    private String time;
    private String sum;
    private String rate;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CountryDepositBean{" +
                "time='" + time + '\'' +
                ", sum='" + sum + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
