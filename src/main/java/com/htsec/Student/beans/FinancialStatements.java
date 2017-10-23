package com.htsec.Student.beans;

/**
 * Created by zzz on 2017/10/22.
 */
public class FinancialStatements {
    private String name;
    private String time;
    private ConsolidatedExpenseStatement consolidatedExpenseStatement;
    private BalanceSheet balanceSheet;
    private ProfitStatement profitStatement;

    public ConsolidatedExpenseStatement getConsolidatedExpenseStatement() {
        return consolidatedExpenseStatement;
    }

    public void setConsolidatedExpenseStatement(ConsolidatedExpenseStatement consolidatedExpenseStatement) {
        this.consolidatedExpenseStatement = consolidatedExpenseStatement;
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public ProfitStatement getProfitStatement() {
        return profitStatement;
    }

    public void setProfitStatement(ProfitStatement profitStatement) {
        this.profitStatement = profitStatement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
