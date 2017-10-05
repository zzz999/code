package com.htsec.Student.init.bean;

public class OtherLoanBean {
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

    @Override
    public String toString() {
        return "OtherLoanBean{" +
                "time='" + time + '\'' +
                ", sum='" + sum + '\'' +
                '}';
    }
}
