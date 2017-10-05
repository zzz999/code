package com.htsec.Student.init.bean;

public class QYLongOrderBean {
    private String num;
    private String orderNum;
    private String time;
    private String QYname;
    private String loanMoney;
    private String highestRate;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQYname() {
        return QYname;
    }

    public void setQYname(String QYname) {
        this.QYname = QYname;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getHighestRate() {
        return highestRate;
    }

    public void setHighestRate(String highestRate) {
        this.highestRate = highestRate;
    }

    @Override
    public String toString() {
        return "QYLongOrderBean{" +
                "num='" + num + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", time='" + time + '\'' +
                ", QYname='" + QYname + '\'' +
                ", loanMoney='" + loanMoney + '\'' +
                ", highestRate='" + highestRate + '\'' +
                '}';
    }
}
