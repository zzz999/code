package com.htsec.Student.beans;

public class LoanGroup {
    private String loanGroupBuildTime;

    public String getLoanGroupBuildTime() {
        return loanGroupBuildTime;
    }

    public void setLoanGroupBuildTime(String loanGroupBuildTime) {
        this.loanGroupBuildTime = loanGroupBuildTime;
    }

    public LoanGroup(){
        this.loanGroupBuildTime ="";
    }
}
