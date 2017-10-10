package com.htsec.Student.beans;

public class CompanyLoanOrder {
    private String companyLongLoanOrderADmoney;
    private String companyLongLoanOrderRate;
    private String companyShortLoanADmoney;
    private String companyShortLoanRate;
    private String code;
    private String name;


    public String getCompanyLongLoanOrderADmoney() {
        return companyLongLoanOrderADmoney;
    }

    public void setCompanyLongLoanOrderADmoney(String companyLongLoanOrderADmoney) {
        this.companyLongLoanOrderADmoney = companyLongLoanOrderADmoney;
    }

    public String getCompanyLongLoanOrderRate() {
        return companyLongLoanOrderRate;
    }

    public void setCompanyLongLoanOrderRate(String companyLongLoanOrderRate) {
        this.companyLongLoanOrderRate = companyLongLoanOrderRate;
    }

    public String getCompanyShortLoanADmoney() {
        return companyShortLoanADmoney;
    }

    public void setCompanyShortLoanADmoney(String companyShortLoanADmoney) {
        this.companyShortLoanADmoney = companyShortLoanADmoney;
    }

    public String getCompanyShortLoanRate() {
        return companyShortLoanRate;
    }

    public void setCompanyShortLoanRate(String companyShortLoanRate) {
        this.companyShortLoanRate = companyShortLoanRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
