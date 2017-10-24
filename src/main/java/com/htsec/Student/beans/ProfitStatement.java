package com.htsec.Student.beans;

import java.math.BigDecimal;

/**
 * Created by zzz on 2017/10/22.
 * 利润表
 */
public class ProfitStatement {
    //消费贷款利息收入
    private String interestIncomeFromConsumerLoans;
    //企业短期贷款利息收入
    private String interestIncomeFromShortCompanyLoans;
    //企业长期贷款利息收入
    private String interestIncomeFromLongCompanyLoans;
    //贴现利息收入
    private String discountInterestIncome;
    //同业拆借（拆出）利息收入
    private String interestIncomeFromInterbankLending;
    //中间业务收入
    private String intermediateBusinessIncome;
    //投资收益
    private String investIncome;
    //个人存款利息支出
    private String interestExpenseOnPersonalDeposits;
    //企业存款利息支出
    private String interestExpenseOnCompanyDeposits;
    //同业拆借（拆入）利息支出
    private String interbankInterestExpense;
    //再贷款利息支出
    private String interestExpenseOnReLending;
    //手续费支出
    private String commissionExpense;
    //综合费用支出
    private String comprehensiveExpenditure;
    //营业外收入
    private String nonOperatingIncome;
    //营业外支出
    private String nonOperatingExpenses;
    //专项风险准备
    private String specialRiskPreparation;
    //所得税
    private String incomeTax;
    //净利润
    private String netProfit;
    public void calcNetProfit(){
        BigDecimal result=new BigDecimal(interestIncomeFromConsumerLoans);
        result=result.add(new BigDecimal(interestIncomeFromShortCompanyLoans));
        result=result.add(new BigDecimal(interestIncomeFromLongCompanyLoans));
        result=result.add(new BigDecimal(discountInterestIncome));
        result=result.add(new BigDecimal(interestIncomeFromInterbankLending));
        result=result.add(new BigDecimal(intermediateBusinessIncome));
        result=result.add(new BigDecimal(investIncome));
        result=result.subtract(new BigDecimal(interestExpenseOnPersonalDeposits));
        result=result.subtract(new BigDecimal(interestExpenseOnCompanyDeposits));
        result=result.subtract(new BigDecimal(interbankInterestExpense));
        result=result.subtract(new BigDecimal(interestExpenseOnReLending));
        result=result.subtract(new BigDecimal(commissionExpense));
        result=result.subtract(new BigDecimal(comprehensiveExpenditure));
        result=result.add(new BigDecimal(nonOperatingIncome));
        result=result.subtract(new BigDecimal(nonOperatingExpenses));
        result=result.subtract(new BigDecimal(incomeTax));
        netProfit=result.toString();
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getInterestIncomeFromConsumerLoans() {
        return interestIncomeFromConsumerLoans;
    }

    public void setInterestIncomeFromConsumerLoans(String interestIncomeFromConsumerLoans) {
        this.interestIncomeFromConsumerLoans = interestIncomeFromConsumerLoans;
    }

    public String getInterestIncomeFromShortCompanyLoans() {
        return interestIncomeFromShortCompanyLoans;
    }

    public void setInterestIncomeFromShortCompanyLoans(String interestIncomeFromShortCompanyLoans) {
        this.interestIncomeFromShortCompanyLoans = interestIncomeFromShortCompanyLoans;
    }

    public String getInterestIncomeFromLongCompanyLoans() {
        return interestIncomeFromLongCompanyLoans;
    }

    public void setInterestIncomeFromLongCompanyLoans(String interestIncomeFromLongCompanyLoans) {
        this.interestIncomeFromLongCompanyLoans = interestIncomeFromLongCompanyLoans;
    }

    public String getDiscountInterestIncome() {
        return discountInterestIncome;
    }

    public void setDiscountInterestIncome(String discountInterestIncome) {
        this.discountInterestIncome = discountInterestIncome;
    }

    public String getInterestIncomeFromInterbankLending() {
        return interestIncomeFromInterbankLending;
    }

    public void setInterestIncomeFromInterbankLending(String interestIncomeFromInterbankLending) {
        this.interestIncomeFromInterbankLending = interestIncomeFromInterbankLending;
    }

    public String getIntermediateBusinessIncome() {
        return intermediateBusinessIncome;
    }

    public void setIntermediateBusinessIncome(String intermediateBusinessIncome) {
        this.intermediateBusinessIncome = intermediateBusinessIncome;
    }

    public String getInvestIncome() {
        return investIncome;
    }

    public void setInvestIncome(String investIncome) {
        this.investIncome = investIncome;
    }

    public String getInterestExpenseOnPersonalDeposits() {
        return interestExpenseOnPersonalDeposits;
    }

    public void setInterestExpenseOnPersonalDeposits(String interestExpenseOnPersonalDeposits) {
        this.interestExpenseOnPersonalDeposits = interestExpenseOnPersonalDeposits;
    }

    public String getInterestExpenseOnCompanyDeposits() {
        return interestExpenseOnCompanyDeposits;
    }

    public void setInterestExpenseOnCompanyDeposits(String interestExpenseOnCompanyDeposits) {
        this.interestExpenseOnCompanyDeposits = interestExpenseOnCompanyDeposits;
    }

    public String getInterbankInterestExpense() {
        return interbankInterestExpense;
    }

    public void setInterbankInterestExpense(String interbankInterestExpense) {
        this.interbankInterestExpense = interbankInterestExpense;
    }

    public String getInterestExpenseOnReLending() {
        return interestExpenseOnReLending;
    }

    public void setInterestExpenseOnReLending(String interestExpenseOnReLending) {
        this.interestExpenseOnReLending = interestExpenseOnReLending;
    }

    public String getCommissionExpense() {
        return commissionExpense;
    }

    public void setCommissionExpense(String commissionExpense) {
        this.commissionExpense = commissionExpense;
    }

    public String getComprehensiveExpenditure() {
        return comprehensiveExpenditure;
    }

    public void setComprehensiveExpenditure(String comprehensiveExpenditure) {
        this.comprehensiveExpenditure = comprehensiveExpenditure;
    }

    public String getNonOperatingIncome() {
        return nonOperatingIncome;
    }

    public void setNonOperatingIncome(String nonOperatingIncome) {
        this.nonOperatingIncome = nonOperatingIncome;
    }

    public String getNonOperatingExpenses() {
        return nonOperatingExpenses;
    }

    public void setNonOperatingExpenses(String nonOperatingExpenses) {
        this.nonOperatingExpenses = nonOperatingExpenses;
    }

    public String getSpecialRiskPreparation() {
        return specialRiskPreparation;
    }

    public void setSpecialRiskPreparation(String specialRiskPreparation) {
        this.specialRiskPreparation = specialRiskPreparation;
    }

    public String getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(String incomeTax) {
        this.incomeTax = incomeTax;
    }
}
