package com.htsec.service.dto;

import java.math.BigDecimal;

/**
 * Created by bernard on 2017/9/20.
 */
public class MasterOverview {
    private BigDecimal porfitToday;
    private String day;
    private BigDecimal totalProfit;

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

    @Override
    public String toString() {
        return "MasterOverview{" +
                "porfitToday=" + porfitToday +
                ", day='" + day + '\'' +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
