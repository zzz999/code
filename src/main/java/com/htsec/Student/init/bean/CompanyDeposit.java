package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class CompanyDeposit {
    private List<CompanyDepositBean> companyDepositBeanList;

    public List<CompanyDepositBean> getCompanyDepositBeanList() {
        return companyDepositBeanList;
    }

    public void setCompanyDepositBeanList(List<CompanyDepositBean> companyDepositBeanList) {
        this.companyDepositBeanList = companyDepositBeanList;
    }

    public CompanyDeposit(){
        this.companyDepositBeanList = new ArrayList<>();
    }
}
