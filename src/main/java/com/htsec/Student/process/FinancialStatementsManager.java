package com.htsec.Student.process;

import com.htsec.Student.beans.*;
import com.htsec.Student.init.bean.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zzz on 2017/10/22.
 */
public class FinancialStatementsManager {
    public FinancialStatements getFinancialStatements(String code, String time) {
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        OrgBuildRule orgBuildRule = StudentInitManager.getOrgBuildRule();
        GroupBuildRule groupBuildRule = StudentInitManager.getGroupBuildRule();
        MarketingBuildRule marketingBuildRule = StudentInitManager.getMarketingBuildRule();
        BigDecimal subbranchBuildCost = new BigDecimal(0);
        BigDecimal teamBuildCost = new BigDecimal(0);
        BigDecimal teamRunCost = new BigDecimal(0);
        BigDecimal channelBuildCost = new BigDecimal(0);
        BigDecimal channelRunCost = new BigDecimal(0);
        BigDecimal marketingCost = new BigDecimal(0);
        List<ZHInfo> zhInfos = bankInfo.getZhInfoList().get(0).getZhInfoList();
        for (ZHInfo zhInfo : zhInfos) {
            if (zhInfo.getZHbuildTime().equals(time)) {
                subbranchBuildCost.add(new BigDecimal(orgBuildRule.getZHbuildCost()));
            }
            if (zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime().equals(time)) {
                teamBuildCost.add(new BigDecimal(groupBuildRule.getDepositGroupBuildCost()));
            }
            if (zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime().equals(time)) {
                teamBuildCost.add(new BigDecimal(groupBuildRule.getLoanGroupBuildCost()));
            }
            if (zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime() != null) {
                teamRunCost.add(new BigDecimal(groupBuildRule.getLoanGroupRunCost()));
            }
            if (zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime() != null) {
                teamRunCost.add(new BigDecimal(groupBuildRule.getDepositGroupRunCost()));
            }
        }
        if (bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime().equals(time)) {
            channelBuildCost.add(new BigDecimal(marketingBuildRule.getNetBankBuiltCost()));
        }
        if (!bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime().equals("0")) {
            channelRunCost.add(new BigDecimal(marketingBuildRule.getNetBankRunCost()));
        }
        if (bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime().equals(time)) {
            channelBuildCost.add(new BigDecimal(marketingBuildRule.getNetBankBuiltCost()));
        }
        if (!bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime().equals("0")) {
            channelRunCost.add(new BigDecimal(marketingBuildRule.getNetBankRunCost()));
        }
        marketingCost.add(new BigDecimal(StudentOrderManager.getPersonalDepositOrderMap().get(time).get(code).getADmoney()));
        PersonalLoanOrder personalLoanOrder = StudentOrderManager.getPersonalLoanOrderMap().get(time).get(code);
        marketingCost.add(new BigDecimal(personalLoanOrder.getCarLoanADmoney()));
        marketingCost.add(new BigDecimal(personalLoanOrder.getHouseLoanADmoney()));
        marketingCost.add(new BigDecimal(personalLoanOrder.getOtherLoanADmoney()));
        marketingCost.add(new BigDecimal(StudentOrderManager.getCompanyDepositOrderMap().get(time).get(code).getCompanyDepositADmoney()));
        marketingCost.add(new BigDecimal(StudentOrderManager.getCompanLoanOrderMap().get(time).get(code).getCompanyLongLoanOrderADmoney()));
        marketingCost.add(new BigDecimal(StudentOrderManager.getCompanLoanOrderMap().get(time).get(code).getCompanyShortLoanADmoney()));
        FinancialStatements fs = new FinancialStatements();
        ConsolidatedExpenseStatement ces = new ConsolidatedExpenseStatement();
        ces.setBranchBankRunCost(StudentInitManager.getOrgBuildRule().getFHRunCost());
        ces.setSubbranchBuildCost(subbranchBuildCost.toString());
        ces.setSubbranchRunCost(new BigDecimal(bankInfo.getZhInfoList().size()).multiply(new BigDecimal(orgBuildRule.getFHRunCost())).toString());
        ces.setChannelBuildCost(channelBuildCost.toString());
        ces.setChannelRunCost(channelRunCost.toString());
        ces.setTeamBuildCost(teamBuildCost.toString());
        ces.setTeamWages(teamRunCost.toString());
        ces.setMarketingCost(marketingCost.toString());
        fs.setConsolidatedExpenseStatement(ces);
        LoanRule loanRule = StudentInitManager.getLoanRule();
        loanRule.getHouseLoanTime();
        BigDecimal interestIncomeFromConsumerLoans = new BigDecimal(0);
        BigDecimal interestIncomeFromShortCompanyLoans = new BigDecimal(0);
        BigDecimal interestIncomeFromLongCompanyLoans = new BigDecimal(0);
        BigDecimal intermediateBusinessIncome = new BigDecimal(0);
        int curTime = Integer.parseInt(time);
        for (int i = 1; i <= curTime; i++) {
            PersonalLoanOrder forPlOrder = StudentOrderManager.getPersonalLoanOrderMap().get(time).get(code);
            if (curTime <= i + Integer.parseInt(loanRule.getHouseLoanTime()) - 1) {
                interestIncomeFromConsumerLoans.add(new BigDecimal(bankInfo.getHouseLoanMap().get(time)).multiply(new BigDecimal(forPlOrder.getHouseLoanRate())));
            }
            if (curTime <= i + Integer.parseInt(loanRule.getCarLoanTime()) - 1) {
                interestIncomeFromConsumerLoans.add(new BigDecimal(bankInfo.getCarLoanMap().get(time)).multiply(new BigDecimal(forPlOrder.getCarLoanRate())));
            }
            if (curTime <= i + Integer.parseInt(loanRule.getOtherLoanTime()) - 1) {
                interestIncomeFromConsumerLoans.add(new BigDecimal(bankInfo.getOtherLoanMap().get(time)).multiply(new BigDecimal(forPlOrder.getOtherLoanRate())));
            }
        }
        List<LoanInfo> loanInfoList = bankInfo.getLoanInfoList();
        for (LoanInfo loanInfo : loanInfoList) {
            String rate, companyLoanTime, companyLoanRuleTime;
            if (loanInfo.getQyShortOrderBean() != null) {
                rate = loanInfo.getQyShortOrderBean().getHighestRate();
                companyLoanTime = loanInfo.getQyShortOrderBean().getTime();
                companyLoanRuleTime = loanRule.getCompanyShortLoanTime();
            } else {
                rate = loanInfo.getQyLongOrderBean().getHighestRate();
                companyLoanTime = loanInfo.getQyLongOrderBean().getTime();
                companyLoanRuleTime = loanRule.getCompanyLongLoanTime();
            }
            if (curTime <= Integer.parseInt(companyLoanTime) + Integer.parseInt(companyLoanRuleTime) - 1) {
                if (loanInfo.getQyShortOrderBean() != null) {
                    interestIncomeFromShortCompanyLoans.add(new BigDecimal(rate).multiply(new BigDecimal(loanInfo.getLoanMoney())));
                } else {
                    interestIncomeFromLongCompanyLoans.add(new BigDecimal(rate).multiply(new BigDecimal(loanInfo.getLoanMoney())));
                }
            }
            if(curTime==Integer.parseInt(companyLoanTime)){
                intermediateBusinessIncome.add(new BigDecimal(loanInfo.getLoanMoney()).multiply(new BigDecimal(0.01)));
            }
        }

        ProfitStatement ps = new ProfitStatement();

        ps.setInterestIncomeFromConsumerLoans(interestIncomeFromConsumerLoans.toString());
        ps.setInterestIncomeFromShortCompanyLoans(interestIncomeFromShortCompanyLoans.toString());
        ps.setInterestIncomeFromLongCompanyLoans(interestIncomeFromLongCompanyLoans.toString());

        BigDecimal discountInterestIncome = new BigDecimal(0);
        List<TXOrderBean> txOrderBeans = bankInfo.getTxOrderBeanList();
        for (TXOrderBean txOrderBean : txOrderBeans) {
            if (curTime <= Integer.parseInt(txOrderBean.getTime()) + 1 - 1) {
                discountInterestIncome.add(new BigDecimal(txOrderBean.getOrderMoney()).multiply(new BigDecimal(txOrderBean.getRate())));
            }
        }
        ps.setDiscountInterestIncome(discountInterestIncome.toString());
        List<BankLoanForm> interBankBorrowings=bankInfo.getInterBankBorrowingList();
        BigDecimal interestIncomeFromInterbankLending = new BigDecimal(0);
        for(BankLoanForm blf:interBankBorrowings){
            if(blf.getBuyCode().equals(code)&&curTime<=Integer.parseInt(blf.getEndTime())){
                interestIncomeFromInterbankLending.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
        }
        ps.setInterestIncomeFromInterbankLending(interestIncomeFromInterbankLending.toString());
        ps.setIntermediateBusinessIncome(intermediateBusinessIncome.toString());

        //国债利息收入
        BigDecimal interestIncomeOfTreasuryBonds=new BigDecimal(0);
        List<BankLoanForm> nationalLoanList=bankInfo.getNationalLoanList();
        for(BankLoanForm blf:nationalLoanList){
            if(blf.getBuyCode().equals(code)&&curTime<=Integer.parseInt(blf.getEndTime())){
                interestIncomeOfTreasuryBonds.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
        }
        //金融债利息收入
        BigDecimal interestIncomeOfFinancialBonds=new BigDecimal(0);
        //金融债利息支出
        BigDecimal interestExpenseOnFinancialBonds=new BigDecimal(0);
        List<BankLoanForm> financialBondsList=bankInfo.getFinancialBondsList();
        for(BankLoanForm blf:financialBondsList){
            if(blf.getBuyCode().equals(code)&&curTime<=Integer.parseInt(blf.getEndTime())){
                interestIncomeOfFinancialBonds.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
            if(blf.getLoanCode().equals(code)&&curTime<=Integer.parseInt(blf.getEndTime())){
                interestExpenseOnFinancialBonds.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
        }
        ps.setInvestIncome(interestIncomeOfTreasuryBonds.add(interestIncomeOfFinancialBonds).subtract(interestExpenseOnFinancialBonds).toString());
        BigDecimal interestExpenseOnPersonalDeposits = new BigDecimal(0);
        ps.setInterestExpenseOnPersonalDeposits(interestExpenseOnPersonalDeposits.toString());
        BigDecimal interestExpenseOnCompanyDeposits = new BigDecimal(0);
        ps.setInterestExpenseOnCompanyDeposits(interestExpenseOnCompanyDeposits.toString());
        BigDecimal interbankInterestExpense = new BigDecimal(0);
        ps.setInterbankInterestExpense(interbankInterestExpense.toString());
        BigDecimal interestExpenseOnReLending = new BigDecimal(0);
        ps.setInterestExpenseOnReLending(interestExpenseOnReLending.toString());
        BigDecimal commissionExpense = new BigDecimal(0);
        ps.setCommissionExpense(commissionExpense.toString());
        BigDecimal comprehensiveExpenditure = new BigDecimal(0);
        ps.setComprehensiveExpenditure(comprehensiveExpenditure.toString());
        BigDecimal nonOperatingIncome = new BigDecimal(0);
        ps.setNonOperatingIncome(nonOperatingIncome.toString());
        BigDecimal nonOperatingExpenses = new BigDecimal(0);
        ps.setNonOperatingExpenses(nonOperatingExpenses.toString());
        BigDecimal specialRiskPreparation = new BigDecimal(0);
        ps.setSpecialRiskPreparation(specialRiskPreparation.toString());
        BigDecimal incomeTax = new BigDecimal(0);
        ps.setIncomeTax(incomeTax.toString());
        fs.setProfitStatement(ps);

        BalanceSheet bs = new BalanceSheet();

        fs.setBalanceSheet(bs);
        return fs;
    }
}
