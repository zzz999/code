package com.htsec.Student.process;

import com.htsec.Student.beans.*;
import com.htsec.Student.init.bean.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zzz on 2017/10/22.
 */
public class FinancialStatementsManager {
    public static FinancialStatements getFinancialStatements(String code, String time) {
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
                subbranchBuildCost = subbranchBuildCost.add(new BigDecimal(orgBuildRule.getZHbuildCost()));
            }
            if (zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime().equals(time)) {
                teamBuildCost = teamBuildCost.add(new BigDecimal(groupBuildRule.getDepositGroupBuildCost()));
            }
            if (zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime().equals(time)) {
                teamBuildCost = teamBuildCost.add(new BigDecimal(groupBuildRule.getLoanGroupBuildCost()));
            }
            if (zhInfo.getGroupInfo().getLoanGroup().getLoanGroupBuildTime() != null) {
                teamRunCost = teamRunCost.add(new BigDecimal(groupBuildRule.getLoanGroupRunCost()));
            }
            if (zhInfo.getGroupInfo().getDepositGroup().getDepositGroupBuiltTime() != null) {
                teamRunCost = teamRunCost.add(new BigDecimal(groupBuildRule.getDepositGroupRunCost()));
            }
        }
        if (bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime().equals(time)) {
            channelBuildCost = channelBuildCost.add(new BigDecimal(marketingBuildRule.getNetBankBuiltCost()));
        }
        if (!bankInfo.getQdInfo().getNetBankInfo().getNetBankBuildTime().equals("0")) {
            channelRunCost = channelRunCost.add(new BigDecimal(marketingBuildRule.getNetBankRunCost()));
        }
        if (bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime().equals(time)) {
            channelBuildCost = channelBuildCost.add(new BigDecimal(marketingBuildRule.getNetBankBuiltCost()));
        }
        if (!bankInfo.getQdInfo().getMobileBankInfo().getMobileBankBuildTime().equals("0")) {
            channelRunCost = channelRunCost.add(new BigDecimal(marketingBuildRule.getNetBankRunCost()));
        }
        marketingCost = marketingCost.add(new BigDecimal(StudentOrderManager.getPersonalDepositOrderMap().get(time).get(code).getADmoney()));
        PersonalLoanOrder personalLoanOrder = StudentOrderManager.getPersonalLoanOrderMap().get(time).get(code);
        marketingCost = marketingCost.add(new BigDecimal(personalLoanOrder.getCarLoanADmoney()));
        marketingCost = marketingCost.add(new BigDecimal(personalLoanOrder.getHouseLoanADmoney()));
        marketingCost = marketingCost.add(new BigDecimal(personalLoanOrder.getOtherLoanADmoney()));
        marketingCost = marketingCost.add(new BigDecimal(StudentOrderManager.getCompanyDepositOrderMap().get(time).get(code).getCompanyDepositADmoney()));
        marketingCost = marketingCost.add(new BigDecimal(StudentOrderManager.getCompanLoanOrderMap().get(time).get(code).getCompanyLongLoanOrderADmoney()));
        marketingCost = marketingCost.add(new BigDecimal(StudentOrderManager.getCompanLoanOrderMap().get(time).get(code).getCompanyShortLoanADmoney()));
        FinancialStatements fs = new FinancialStatements();
        fs.setName(bankInfo.getName());
        fs.setTime(time);
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
        BigDecimal interestIncomeFromConsumerLoans = new BigDecimal(0);
        BigDecimal interestIncomeFromShortCompanyLoans = new BigDecimal(0);
        BigDecimal interestIncomeFromLongCompanyLoans = new BigDecimal(0);
        BigDecimal intermediateBusinessIncome = new BigDecimal(0);
        int curTime = Integer.parseInt(time);
        for (int i = 1; i <= curTime; i++) {
            PersonalLoanOrder forPlOrder = StudentOrderManager.getPersonalLoanOrderMap().get(time).get(code);
            if (curTime <= i + Integer.parseInt(loanRule.getHouseLoanTime()) - 1) {
                interestIncomeFromConsumerLoans = interestIncomeFromConsumerLoans.add(new BigDecimal(bankInfo.getHouseLoanMap().get(time)).multiply(new BigDecimal(forPlOrder.getHouseLoanRate())));
            }
            if (curTime <= i + Integer.parseInt(loanRule.getCarLoanTime()) - 1) {
                interestIncomeFromConsumerLoans = interestIncomeFromConsumerLoans.add(new BigDecimal(bankInfo.getCarLoanMap().get(time)).multiply(new BigDecimal(forPlOrder.getCarLoanRate())));
            }
            if (curTime <= i + Integer.parseInt(loanRule.getOtherLoanTime()) - 1) {
                interestIncomeFromConsumerLoans = interestIncomeFromConsumerLoans.add(new BigDecimal(bankInfo.getOtherLoanMap().get(time)).multiply(new BigDecimal(forPlOrder.getOtherLoanRate())));
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
                    interestIncomeFromShortCompanyLoans = interestIncomeFromShortCompanyLoans.add(new BigDecimal(rate).multiply(new BigDecimal(loanInfo.getLoanMoney())));
                } else {
                    interestIncomeFromLongCompanyLoans = interestIncomeFromLongCompanyLoans.add(new BigDecimal(rate).multiply(new BigDecimal(loanInfo.getLoanMoney())));
                }
            }
            if (curTime == Integer.parseInt(companyLoanTime)) {
                intermediateBusinessIncome = intermediateBusinessIncome.add(new BigDecimal(loanInfo.getLoanMoney()).multiply(new BigDecimal(0.01)));
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
                discountInterestIncome = discountInterestIncome.add(new BigDecimal(txOrderBean.getOrderMoney()).multiply(new BigDecimal(txOrderBean.getRate())));
            }
        }
        ps.setDiscountInterestIncome(discountInterestIncome.toString());
        List<BankLoanForm> interBankBorrowings = bankInfo.getInterBankBorrowingList();
        BigDecimal interestIncomeFromInterbankLending = new BigDecimal(0);
        BigDecimal interbankInterestExpense = new BigDecimal(0);
        for (BankLoanForm blf : interBankBorrowings) {
            if (blf.getBuyCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interestIncomeFromInterbankLending = interestIncomeFromInterbankLending.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
            if (blf.getLoanCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interbankInterestExpense = interbankInterestExpense.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
        }
        ps.setInterestIncomeFromInterbankLending(interestIncomeFromInterbankLending.toString());
        ps.setIntermediateBusinessIncome(intermediateBusinessIncome.toString());

        //国债利息收入
        BigDecimal interestIncomeOfTreasuryBonds = new BigDecimal(0);
        List<BankLoanForm> nationalLoanList = bankInfo.getNationalLoanList();
        for (BankLoanForm blf : nationalLoanList) {
            if (blf.getBuyCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interestIncomeOfTreasuryBonds = interestIncomeOfTreasuryBonds.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
        }
        //手续费
        BigDecimal commissionExpense = new BigDecimal(0);
        //金融债利息收入
        BigDecimal interestIncomeOfFinancialBonds = new BigDecimal(0);
        //金融债利息支出
        BigDecimal interestExpenseOnFinancialBonds = new BigDecimal(0);
        List<BankLoanForm> financialBondsList = bankInfo.getFinancialBondsList();
        for (BankLoanForm blf : financialBondsList) {
            if (blf.getBuyCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interestIncomeOfFinancialBonds = interestIncomeOfFinancialBonds.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
            if (blf.getLoanCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interestExpenseOnFinancialBonds = interestExpenseOnFinancialBonds.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
            if (blf.getLoanCode().equals(code) && blf.getStartTime().equals(time)) {
                commissionExpense = commissionExpense.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(0.001)));
            }
        }
        ps.setInvestIncome(interestIncomeOfTreasuryBonds.add(interestIncomeOfFinancialBonds).subtract(interestExpenseOnFinancialBonds).toString());

        BigDecimal interestExpenseOnPersonalDeposits = new BigDecimal(0);
        BigDecimal interestExpenseOnCompanyDeposits = new BigDecimal(0);
        DepositRule depositRule = StudentInitManager.getDepositRule();
        for (int i = 1; i <= curTime; i++) {
            PersonalDepositOrder pdo = StudentOrderManager.getPersonalDepositOrderMap().get(time).get(code);
            if (curTime <= i + Integer.parseInt(depositRule.getPersionalDepositTime()) - 1) {
                interestExpenseOnPersonalDeposits = interestExpenseOnPersonalDeposits.add(StudentOrderManager.getPersonalDepositMoneyMap().get(time).get(code).multiply(new BigDecimal(pdo.getOrderRate())));
            }
            CompanyDepositOrder cdo = StudentOrderManager.getCompanyDepositOrderMap().get(time).get(code);
            if (curTime <= i + Integer.parseInt(depositRule.getCompanyDepositTime()) - 1) {
                interestExpenseOnCompanyDeposits = interestExpenseOnCompanyDeposits.add(StudentOrderManager.getCompanyDepositMoneyMap().get(time).get(code).multiply(new BigDecimal(cdo.getCompanyDepositRate())));
            }
        }
        ps.setInterestExpenseOnPersonalDeposits(interestExpenseOnPersonalDeposits.toString());
        ps.setInterestExpenseOnCompanyDeposits(interestExpenseOnCompanyDeposits.toString());
        ps.setInterbankInterestExpense(interbankInterestExpense.toString());

        BigDecimal interestExpenseOnReLending = new BigDecimal(0);
        List<BankLoanForm> againLoanList = bankInfo.getAgainLoanList();
        for (BankLoanForm blf : againLoanList) {
            if (blf.getLoanCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interestExpenseOnReLending = interestExpenseOnReLending.add(new BigDecimal(blf.getMoney()).multiply(new BigDecimal(blf.getRate())));
            }
        }
        ps.setInterestExpenseOnReLending(interestExpenseOnReLending.toString());

        ps.setCommissionExpense(commissionExpense.toString());
        BigDecimal comprehensiveExpenditure = new BigDecimal(0);

        ps.setComprehensiveExpenditure(comprehensiveExpenditure
                .add(interestExpenseOnPersonalDeposits)
                .add(interestExpenseOnCompanyDeposits)
                .add(interbankInterestExpense)
                .add(interestExpenseOnReLending)
                .add(commissionExpense).toString()
        );
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
        bs.setCash(bankInfo.getCash());
        BigDecimal personalDeposit = new BigDecimal(0);
        BigDecimal companyDeposit = new BigDecimal(0);
        for (int i = 1; i <= curTime; i++) {
            if (curTime <= i + Integer.parseInt(depositRule.getPersionalDepositTime()) - 1) {
                personalDeposit = personalDeposit.add(StudentOrderManager.getPersonalDepositMoneyMap().get(time).get(code).multiply(
                        new BigDecimal(1).subtract(
                                new BigDecimal(depositRule.getPersionalDepositAnnualReturn().replaceAll("%", ""))
                                        .divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP)
                                        .multiply(new BigDecimal(curTime - i))
                        )
                ));
            }
            if (curTime <= i + Integer.parseInt(depositRule.getCompanyDepositTime()) - 1) {
                companyDeposit = companyDeposit.add(StudentOrderManager.getCompanyDepositMoneyMap().get(time).get(code).multiply(
                        new BigDecimal(1).subtract(
                                new BigDecimal(depositRule.getCompanyDepositAnnualReturn().replaceAll("%", ""))
                                        .divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP)
                                        .multiply(new BigDecimal(curTime - i))
                        )
                ));
            }
        }
        bs.setPersonalDeposit(personalDeposit.toString());
        bs.setCompanyDeposit(companyDeposit.toString());
        BigDecimal againLoanMoney=new BigDecimal(0);
        for (BankLoanForm blf : againLoanList) {
            if (blf.getLoanCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                againLoanMoney = againLoanMoney.add(new BigDecimal(blf.getMoney()));
            }
        }
        //拆入
        BigDecimal interbankBorrowingMoney=new BigDecimal(0);
        //拆出
        BigDecimal interbankLendingMoney=new BigDecimal(0);
        for (BankLoanForm blf : interBankBorrowings) {
            if (blf.getBuyCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interbankLendingMoney = interbankLendingMoney.add(new BigDecimal(blf.getMoney()));
            }
            if (blf.getLoanCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                interbankBorrowingMoney = interbankBorrowingMoney.add(new BigDecimal(blf.getMoney()));
            }
        }
        bs.setInterbankLending(interbankLendingMoney.toString());
        bs.setInterbankBorrowing(interbankBorrowingMoney.toString());
        //投资
        BigDecimal investmentInFinancialBonds=new BigDecimal(0);
        //发行
        BigDecimal financialBondsIssue=new BigDecimal(0);
        for (BankLoanForm blf : financialBondsList) {
            if (blf.getBuyCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                investmentInFinancialBonds = investmentInFinancialBonds.add(new BigDecimal(blf.getMoney()));
            }
            if (blf.getLoanCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                financialBondsIssue = financialBondsIssue.add(new BigDecimal(blf.getMoney()));
            }
        }
        bs.setFinancialBondsIssue(financialBondsIssue.toString());
        bs.setInvestmentInFinancialBonds(investmentInFinancialBonds.toString());
        BigDecimal nationalLoanMoney=new BigDecimal(0);
        for (BankLoanForm blf : nationalLoanList) {
            if (blf.getBuyCode().equals(code) && curTime <= Integer.parseInt(blf.getEndTime())) {
                nationalLoanMoney = nationalLoanMoney.add(new BigDecimal(blf.getMoney()));
            }
        }
        bs.setNationalLoan(nationalLoanMoney.toString());
        BigDecimal carLoanMoney=new BigDecimal(0);
        BigDecimal houseLoanMoney=new BigDecimal(0);
        BigDecimal otherLoanMoney=new BigDecimal(0);
        BigDecimal companyShortLoanMoney=new BigDecimal(0);
        BigDecimal companyLongLoanMoney=new BigDecimal(0);
        for (int i = 1; i <= curTime; i++) {
            if (curTime <= i + Integer.parseInt(loanRule.getHouseLoanTime()) - 1) {
                houseLoanMoney=houseLoanMoney.add(
                        new BigDecimal(bankInfo.getHouseLoanMap().get(time)).multiply(
                            new BigDecimal(1).subtract(
                                new BigDecimal(loanRule.getHouseLoanReturn().replaceAll("%", ""))
                                        .divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP)
                                        .multiply(new BigDecimal(curTime - i))
                                )
                    )
                );
            }
            if (curTime <= i + Integer.parseInt(loanRule.getCarLoanTime()) - 1) {
                carLoanMoney=carLoanMoney.add(
                        new BigDecimal(bankInfo.getCarLoanMap().get(time)).multiply(
                                new BigDecimal(1).subtract(
                                        new BigDecimal(loanRule.getCarLoanReturn().replaceAll("%", ""))
                                                .divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP)
                                                .multiply(new BigDecimal(curTime - i))
                                )
                        )
                );
            }
            if (curTime <= i + Integer.parseInt(loanRule.getOtherLoanTime()) - 1) {
                otherLoanMoney=carLoanMoney.add(
                        new BigDecimal(bankInfo.getOtherLoanMap().get(time)).multiply(
                                new BigDecimal(1).subtract(
                                        new BigDecimal(loanRule.getOtherLoanReturn().replaceAll("%", ""))
                                                .divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP)
                                                .multiply(new BigDecimal(curTime - i))
                                )
                        )
                );
            }
        }
        for(LoanInfo loanInfo:loanInfoList){
            if(loanInfo.getLoanType().equalsIgnoreCase("companyShortOrder")){
                if(new BigDecimal(time).subtract(new BigDecimal(loanInfo.getLoanStartTime())).compareTo(new BigDecimal(loanRule.getCompanyShortLoanTime()))<0){
                    companyShortLoanMoney=companyShortLoanMoney.add(new BigDecimal(loanInfo.getLoanMoney()));
                }

            }
            if(loanInfo.getLoanType().equalsIgnoreCase("companyLongOrder")){
                if(new BigDecimal(time).subtract(new BigDecimal(loanInfo.getLoanStartTime())).compareTo(new BigDecimal(loanRule.getCompanyLongLoanTime()))<0){
                    companyLongLoanMoney=companyLongLoanMoney.add(new BigDecimal(loanInfo.getLoanMoney()));
                }

            }
        }
        bs.setHouseLoan(houseLoanMoney.toString());
        bs.setCarLoan(carLoanMoney.toString());
        bs.setOtherLoan(otherLoanMoney.toString());
        bs.setCompanyLongTermLoan(companyLongLoanMoney.toString());
        bs.setCompanyShortTermLoan(companyShortLoanMoney.toString());
        List<TXOrderBean> txOrderBeanList=bankInfo.getTxOrderBeanList();
        BigDecimal accountReceivableDiscount=new BigDecimal(0);
        for(TXOrderBean txOrderBean:txOrderBeanList){
            if(txOrderBean.getTime().equals(time)){
                accountReceivableDiscount=accountReceivableDiscount.add(new BigDecimal(txOrderBean.getOrderMoney()));
            }
        }
        bs.setAccountReceivableDiscount(accountReceivableDiscount.toString());
        fs.setBalanceSheet(bs);
        return fs;
    }
}
