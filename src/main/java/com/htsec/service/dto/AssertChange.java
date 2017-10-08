package com.htsec.service.dto;

public class AssertChange {
    private String beginAssert;
    private String endAssert;
    private String assertIncome;
    private String assertOutcome;

    public String getBeginAssert() {
        return beginAssert;
    }

    public void setBeginAssert(String beginAssert) {
        this.beginAssert = beginAssert;
    }

    public String getEndAssert() {
        return endAssert;
    }

    public void setEndAssert(String endAssert) {
        this.endAssert = endAssert;
    }

    public String getAssertIncome() {
        return assertIncome;
    }

    public void setAssertIncome(String assertIncome) {
        this.assertIncome = assertIncome;
    }

    public String getAssertOutcome() {
        return assertOutcome;
    }

    public void setAssertOutcome(String assertOutcome) {
        this.assertOutcome = assertOutcome;
    }
}
