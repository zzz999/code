package com.htsec.mysql.dto;

import java.math.BigDecimal;

public class swcompositescore {
    private int bizDt;
    private int funaccount;
    private BigDecimal assetllocation;
    private BigDecimal operate;
    private BigDecimal stockSelection;
    private BigDecimal stability;
    private BigDecimal marketTiming;
    private BigDecimal riskControl;

    public int getBizDt() {
        return bizDt;
    }

    public void setBizDt(int bizDt) {
        this.bizDt = bizDt;
    }

    public int getFunaccount() {
        return funaccount;
    }

    public void setFunaccount(int funaccount) {
        this.funaccount = funaccount;
    }

    public BigDecimal getAssetllocation() {
        return assetllocation;
    }

    public void setAssetllocation(BigDecimal assetllocation) {
        this.assetllocation = assetllocation;
    }

    public BigDecimal getOperate() {
        return operate;
    }

    public void setOperate(BigDecimal operate) {
        this.operate = operate;
    }

    public BigDecimal getStockSelection() {
        return stockSelection;
    }

    public void setStockSelection(BigDecimal stockSelection) {
        this.stockSelection = stockSelection;
    }

    public BigDecimal getStability() {
        return stability;
    }

    public void setStability(BigDecimal stability) {
        this.stability = stability;
    }

    public BigDecimal getMarketTiming() {
        return marketTiming;
    }

    public void setMarketTiming(BigDecimal marketTiming) {
        this.marketTiming = marketTiming;
    }

    public BigDecimal getRiskControl() {
        return riskControl;
    }

    public void setRiskControl(BigDecimal riskControl) {
        this.riskControl = riskControl;
    }

    @Override
    public String toString() {
        return "swcompositescore{" +
                "bizDt=" + bizDt +
                ", funaccount=" + funaccount +
                ", assetllocation=" + assetllocation +
                ", operate=" + operate +
                ", stockSelection=" + stockSelection +
                ", stability=" + stability +
                ", marketTiming=" + marketTiming +
                ", riskControl=" + riskControl +
                '}';
    }
}
