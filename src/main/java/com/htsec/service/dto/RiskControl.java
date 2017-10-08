package com.htsec.service.dto;

public class RiskControl {
    private String calmarRatio;
    private String sortinoRatio;
    private String sharpeRatio;

    public String getCalmarRatio() {
        return calmarRatio;
    }

    public void setCalmarRatio(String calmarRatio) {
        this.calmarRatio = calmarRatio;
    }

    public String getSortinoRatio() {
        return sortinoRatio;
    }

    public void setSortinoRatio(String sortinoRatio) {
        this.sortinoRatio = sortinoRatio;
    }

    public String getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(String sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }
}
