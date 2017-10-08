package com.htsec.service.dto;

import java.math.BigDecimal;

public class SwfundAssetmonth {
    private int yearMth;
    private String profitRateByday;
    private String holderRateByday;
    private String profitByday;
    private BigDecimal periodProfit;
    private BigDecimal beginTotalAsset;
    private BigDecimal endTotalAsset;
    private BigDecimal periodInBalance;
    private BigDecimal periodOutBalance;
    //归因分析
    private String factorProfit;
    private String riskExp;
    //操作分析
    private BigDecimal alpha;
    private BigDecimal stockSelection;
    private BigDecimal beta1;
    private BigDecimal beta2;
    private BigDecimal marketTiming;
    private BigDecimal tradeSuccessRate;
    private BigDecimal tradingTimes;
    private int profitSecCount;
    private String industryProfit;
    private String investStyle;

    //风险控制
    private BigDecimal calmarRatio;
    private BigDecimal sortinoRatio;
    private BigDecimal sharpeRatio;

    //综合表现
    private BigDecimal assetAllocation;
    private BigDecimal operate;
    private BigDecimal stability;
    private BigDecimal riskControl;

    public int getYearMth() {
        return yearMth;
    }

    public void setYearMth(int yearMth) {
        this.yearMth = yearMth;
    }

    public String getProfitRateByday() {
        return profitRateByday;
    }

    public void setProfitRateByday(String profitRateByday) {
        this.profitRateByday = profitRateByday;
    }

    public String getHolderRateByday() {
        return holderRateByday;
    }

    public void setHolderRateByday(String holderRateByday) {
        this.holderRateByday = holderRateByday;
    }

    public String getProfitByday() {
        return profitByday;
    }

    public void setProfitByday(String profitByday) {
        this.profitByday = profitByday;
    }

    public BigDecimal getPeriodProfit() {
        return periodProfit;
    }

    public void setPeriodProfit(BigDecimal periodProfit) {
        this.periodProfit = periodProfit;
    }

    public BigDecimal getBeginTotalAsset() {
        return beginTotalAsset;
    }

    public void setBeginTotalAsset(BigDecimal beginTotalAsset) {
        this.beginTotalAsset = beginTotalAsset;
    }

    public BigDecimal getEndTotalAsset() {
        return endTotalAsset;
    }

    public void setEndTotalAsset(BigDecimal endTotalAsset) {
        this.endTotalAsset = endTotalAsset;
    }

    public BigDecimal getPeriodInBalance() {
        return periodInBalance;
    }

    public void setPeriodInBalance(BigDecimal periodInBalance) {
        this.periodInBalance = periodInBalance;
    }

    public BigDecimal getPeriodOutBalance() {
        return periodOutBalance;
    }

    public void setPeriodOutBalance(BigDecimal periodOutBalance) {
        this.periodOutBalance = periodOutBalance;
    }

    public String getFactorProfit() {
        return factorProfit;
    }

    public void setFactorProfit(String factorProfit) {
        this.factorProfit = factorProfit;
    }

    public String getRiskExp() {
        return riskExp;
    }

    public void setRiskExp(String riskExp) {
        this.riskExp = riskExp;
    }

    public BigDecimal getAlpha() {
        return alpha;
    }

    public void setAlpha(BigDecimal alpha) {
        this.alpha = alpha;
    }

    public BigDecimal getStockSelection() {
        return stockSelection;
    }

    public void setStockSelection(BigDecimal stockSelection) {
        this.stockSelection = stockSelection;
    }

    public BigDecimal getBeta1() {
        return beta1;
    }

    public void setBeta1(BigDecimal beta1) {
        this.beta1 = beta1;
    }

    public BigDecimal getBeta2() {
        return beta2;
    }

    public void setBeta2(BigDecimal beta2) {
        this.beta2 = beta2;
    }

    public BigDecimal getMarketTiming() {
        return marketTiming;
    }

    public void setMarketTiming(BigDecimal marketTiming) {
        this.marketTiming = marketTiming;
    }

    public BigDecimal getTradeSuccessRate() {
        return tradeSuccessRate;
    }

    public void setTradeSuccessRate(BigDecimal tradeSuccessRate) {
        this.tradeSuccessRate = tradeSuccessRate;
    }

    public BigDecimal getTradingTimes() {
        return tradingTimes;
    }

    public void setTradingTimes(BigDecimal tradingTimes) {
        this.tradingTimes = tradingTimes;
    }

    public int getProfitSecCount() {
        return profitSecCount;
    }

    public void setProfitSecCount(int profitSecCount) {
        this.profitSecCount = profitSecCount;
    }

    public String getIndustryProfit() {
        return industryProfit;
    }

    public void setIndustryProfit(String industryProfit) {
        this.industryProfit = industryProfit;
    }

    public String getInvestStyle() {
        return investStyle;
    }

    public void setInvestStyle(String investStyle) {
        this.investStyle = investStyle;
    }

    public BigDecimal getCalmarRatio() {
        return calmarRatio;
    }

    public void setCalmarRatio(BigDecimal calmarRatio) {
        this.calmarRatio = calmarRatio;
    }

    public BigDecimal getSortinoRatio() {
        return sortinoRatio;
    }

    public void setSortinoRatio(BigDecimal sortinoRatio) {
        this.sortinoRatio = sortinoRatio;
    }

    public BigDecimal getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(BigDecimal sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public BigDecimal getAssetAllocation() {
        return assetAllocation;
    }

    public void setAssetAllocation(BigDecimal assetAllocation) {
        this.assetAllocation = assetAllocation;
    }

    public BigDecimal getOperate() {
        return operate;
    }

    public void setOperate(BigDecimal operate) {
        this.operate = operate;
    }

    public BigDecimal getStability() {
        return stability;
    }

    public void setStability(BigDecimal stability) {
        this.stability = stability;
    }

    public BigDecimal getRiskControl() {
        return riskControl;
    }

    public void setRiskControl(BigDecimal riskControl) {
        this.riskControl = riskControl;
    }
}
