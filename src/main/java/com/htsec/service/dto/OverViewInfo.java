package com.htsec.service.dto;

public class OverViewInfo implements Comparable<OverViewInfo> {
    private String todayRate;
    private String todayHold;
    private String todayProfit;
    private String time;
    private String totalRate;
    private String totalProfit;

    public String getTodayRate() {
        return todayRate;
    }

    public void setTodayRate(String todayRate) {
        this.todayRate = todayRate;
    }

    public String getTodayHold() {
        return todayHold;
    }

    public void setTodayHold(String todayHold) {
        this.todayHold = todayHold;
    }

    public String getTodayProfit() {
        return todayProfit;
    }

    public void setTodayProfit(String todayProfit) {
        this.todayProfit = todayProfit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public int compareTo(OverViewInfo o) {
        int i = Integer.parseInt(this.time) - Integer.parseInt(o.getTime());//先按照年龄排序  
        return i;
    }
}
