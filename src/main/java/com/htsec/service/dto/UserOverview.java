package com.htsec.service.dto;

import com.amazonaws.services.dynamodbv2.xspec.B;

import java.math.BigDecimal;

/**
 * Created by bernard on 2017/9/19.
 */
public class UserOverview {
    private BigDecimal porfitToday;
    private String day;
    private BigDecimal totalProfit;
    private BigDecimal profitMoney;
    private BigDecimal totalProfitMoney;

    public BigDecimal getPorfitToday() {
        return porfitToday;
    }

    public void setPorfitToday(BigDecimal porfitToday) {
        this.porfitToday = porfitToday;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(BigDecimal profitMoney) {
        this.profitMoney = profitMoney;
    }

    public BigDecimal getTotalProfitMoney() {
        return totalProfitMoney;
    }

    public void setTotalProfitMoney(BigDecimal totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }




}
