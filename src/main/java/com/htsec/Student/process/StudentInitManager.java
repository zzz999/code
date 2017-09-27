package com.htsec.Student.process;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.JGInfo;
import com.htsec.Student.beans.StudentMessage;
import com.htsec.Student.beans.ZhInfo;
import com.htsec.Student.init.bean.*;

/**
 * Created by bernard on 2017/9/27.
 */
public class StudentInitManager {
    private static DepositRule depositRule;
    private static GroupBuildRule groupBuildRule;
    private static LoanLostPrepareRule loanLostPrepareRule;
    private static LoanRule loanRule;
    private static MarketingBuildRule marketingBuildRule;
    private static OrgBuildRule orgBuildRule;

    public static DepositRule getDepositRule() {
        return depositRule;
    }

    public static void setDepositRule(DepositRule depositRule) {
        StudentInitManager.depositRule = depositRule;
    }

    public static GroupBuildRule getGroupBuildRule() {
        return groupBuildRule;
    }

    public static void setGroupBuildRule(GroupBuildRule groupBuildRule) {
        StudentInitManager.groupBuildRule = groupBuildRule;
    }

    public static LoanLostPrepareRule getLoanLostPrepareRule() {
        return loanLostPrepareRule;
    }

    public static void setLoanLostPrepareRule(LoanLostPrepareRule loanLostPrepareRule) {
        StudentInitManager.loanLostPrepareRule = loanLostPrepareRule;
    }

    public static LoanRule getLoanRule() {
        return loanRule;
    }

    public static void setLoanRule(LoanRule loanRule) {
        StudentInitManager.loanRule = loanRule;
    }

    public static MarketingBuildRule getMarketingBuildRule() {
        return marketingBuildRule;
    }

    public static void setMarketingBuildRule(MarketingBuildRule marketingBuildRule) {
        StudentInitManager.marketingBuildRule = marketingBuildRule;
    }

    public static OrgBuildRule getOrgBuildRule() {
        return orgBuildRule;
    }

    public static void setOrgBuildRule(OrgBuildRule orgBuildRule) {
        StudentInitManager.orgBuildRule = orgBuildRule;
    }

}
