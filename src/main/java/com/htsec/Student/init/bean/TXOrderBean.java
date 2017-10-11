package com.htsec.Student.init.bean;

public class TXOrderBean {
    private String num;
    private String orderNum;
    private String time;
    private String orderMoney;
    private String TXtime;
    private String ZQ;
    private String rate;

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

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getTXtime() {
        return TXtime;
    }

    public void setTXtime(String TXtime) {
        this.TXtime = TXtime;
    }

    public String getZQ() {
        return ZQ;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setZQ(String ZQ) {
        this.ZQ = ZQ;
    }

    @Override
    public String toString() {
        return "TXOrderBean{" +
                "num='" + num + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", time='" + time + '\'' +
                ", orderMoney='" + orderMoney + '\'' +
                ", TXtime='" + TXtime + '\'' +
                ", ZQ='" + ZQ + '\'' +
                '}';
    }
}
