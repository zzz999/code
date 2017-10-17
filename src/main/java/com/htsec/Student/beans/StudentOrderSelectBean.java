package com.htsec.Student.beans;

import java.math.BigDecimal;

public class StudentOrderSelectBean implements Comparable<StudentOrderSelectBean> {
    private String code;
    private int timeRemain;
    private String rate;
    private String type;
    private String lastYearNewDeposit;
    private String Admoney;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(int timeRemain) {
        this.timeRemain = timeRemain;
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

    public String getLastYearNewDeposit() {
        return lastYearNewDeposit;
    }

    public void setLastYearNewDeposit(String lastYearNewDeposit) {
        this.lastYearNewDeposit = lastYearNewDeposit;
    }

    public String getAdmoney() {
        return Admoney;
    }

    public void setAdmoney(String admoney) {
        Admoney = admoney;
    }

    @Override
    public int compareTo(StudentOrderSelectBean o) {
        int a = new BigDecimal(this.rate).compareTo(new BigDecimal(o.rate));
        if (a != 0 || this.Admoney == null && this.lastYearNewDeposit == null) {
            return a;
        } else if (this.Admoney == null && this.lastYearNewDeposit != null) {
            int c = new BigDecimal(this.lastYearNewDeposit).compareTo(new BigDecimal(o.lastYearNewDeposit));
            return c;
        } else {
            int b = new BigDecimal(this.Admoney).compareTo(new BigDecimal(o.Admoney));
            if (b != 0 || this.lastYearNewDeposit == null) {
                return b;
            } else {
                int c = new BigDecimal(this.lastYearNewDeposit).compareTo(new BigDecimal(o.lastYearNewDeposit));
                return c;
            }
        }
    }
}
