package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class PersonalDeposit {
    private List<PersonalDepositBean> personalDepositBeanList;

    public List<PersonalDepositBean> getPersonalDepositBeanList() {
        return personalDepositBeanList;
    }

    public void setPersonalDepositBeanList(List<PersonalDepositBean> personalDepositBeanList) {
        this.personalDepositBeanList = personalDepositBeanList;
    }
    public PersonalDeposit(){
        this.personalDepositBeanList = new ArrayList<>();
    }

}
