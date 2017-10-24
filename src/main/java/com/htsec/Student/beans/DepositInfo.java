package com.htsec.Student.beans;

public class DepositInfo {
    private String depositStartTime;
    private String depositMoney;
    private String depositType;

    public String getDepositStartTime() {
        return depositStartTime;
    }

    public void setDepositStartTime(String depositStartTime) {
        this.depositStartTime = depositStartTime;
    }

    public String getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(String depositMoney) {
        this.depositMoney = depositMoney;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }
}
