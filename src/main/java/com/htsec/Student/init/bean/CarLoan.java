package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class CarLoan {
    private List<CarLoanBean> carLoanBeanList;

    public List<CarLoanBean> getCarLoanBeanList() {
        return carLoanBeanList;
    }

    public void setCarLoanBeanList(List<CarLoanBean> carLoanBeanList) {
        this.carLoanBeanList = carLoanBeanList;
    }

    public CarLoan(){
        this.carLoanBeanList = new ArrayList<>();
    }
}
