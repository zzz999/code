package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class HouseLoan {
    private List<HouseLoanBean> houseLoanBeanList;

    public List<HouseLoanBean> getHouseLoanBeanList() {
        return houseLoanBeanList;
    }

    public void setHouseLoanBeanList(List<HouseLoanBean> houseLoanBeanList) {
        this.houseLoanBeanList = houseLoanBeanList;
    }

    public HouseLoan(){
        this.houseLoanBeanList = new ArrayList<>();
    }

}
