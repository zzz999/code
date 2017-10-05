package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class ContryDeposit {
   private List<CountryDepositBean> countryDepositBeanList;

   public ContryDeposit(){
       this.countryDepositBeanList = new ArrayList<>();
   }

    public List<CountryDepositBean> getCountryDepositBeanList() {
        return countryDepositBeanList;
    }

    public void setCountryDepositBeanList(List<CountryDepositBean> countryDepositBeanList) {
        this.countryDepositBeanList = countryDepositBeanList;
    }
}
