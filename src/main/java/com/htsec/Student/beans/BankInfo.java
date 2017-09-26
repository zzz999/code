package com.htsec.Student.beans;

import java.util.List;

/**
 * Created by bernard on 2017/9/26.
 */
public class BankInfo {
    private String cash;
    private String time;
    private String totalLoan;
    private String totalDeposit;
    private List<ZhInfo> zhInfoList;

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

    public String getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(String totalLoan) {
        this.totalLoan = totalLoan;
    }

    public String getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(String totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public List<ZhInfo> getZhInfoList() {
        return zhInfoList;
    }

    public void setZhInfoList(List<ZhInfo> zhInfoList) {
        this.zhInfoList = zhInfoList;
    }
}
