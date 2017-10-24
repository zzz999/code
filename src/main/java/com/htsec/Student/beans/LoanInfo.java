package com.htsec.Student.beans;

import com.htsec.Student.init.bean.QYLongOrderBean;
import com.htsec.Student.init.bean.QYShortOrderBean;

public class LoanInfo {
    private String loanType;
    private String loanStartTime;
    private String loanMoney;
    private QYLongOrderBean qyLongOrderBean;
    private QYShortOrderBean qyShortOrderBean;

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanStartTime() {
        return loanStartTime;
    }

    public void setLoanStartTime(String loanStartTime) {
        this.loanStartTime = loanStartTime;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public QYLongOrderBean getQyLongOrderBean() {
        return qyLongOrderBean;
    }

    public void setQyLongOrderBean(QYLongOrderBean qyLongOrderBean) {
        this.qyLongOrderBean = qyLongOrderBean;
    }

    public QYShortOrderBean getQyShortOrderBean() {
        return qyShortOrderBean;
    }

    public void setQyShortOrderBean(QYShortOrderBean qyShortOrderBean) {
        this.qyShortOrderBean = qyShortOrderBean;
    }
}
