package com.htsec.Student.beans;

public class GroupInfo {
    private DepositGroup depositGroup;
    private LoanGroup loanGroup;

    public GroupInfo(){
        this.depositGroup = new DepositGroup();
        this.loanGroup =new LoanGroup();
    }

    public DepositGroup getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(DepositGroup depositGroup) {
        this.depositGroup = depositGroup;
    }

    public LoanGroup getLoanGroup() {
        return loanGroup;
    }

    public void setLoanGroup(LoanGroup loanGroup) {
        this.loanGroup = loanGroup;
    }
}
