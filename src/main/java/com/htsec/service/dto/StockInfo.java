package com.htsec.service.dto;

import java.math.BigDecimal;

/**
 * Created by bernard on 2017/9/22.
 */
public class StockInfo {
    private String stockName;
    private BigDecimal profitRate;
    private BigDecimal profitMoney;
    private BigDecimal holdDays;
    private BigDecimal currentState;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(BigDecimal profitMoney) {
        this.profitMoney = profitMoney;
    }

    public BigDecimal getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(BigDecimal holdDays) {
        this.holdDays = holdDays;
    }

    public BigDecimal getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BigDecimal currentState) {
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "stockName='" + stockName + '\'' +
                ", profitRate=" + profitRate +
                ", profitMoney=" + profitMoney +
                ", holdDays=" + holdDays +
                ", currentState=" + currentState +
                '}';
    }
}
