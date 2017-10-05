package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class OtherLoan {
  private List<OtherLoanBean> otherLoanBeanList;

    public List<OtherLoanBean> getOtherLoanBeanList() {
        return otherLoanBeanList;
    }

    public void setOtherLoanBeanList(List<OtherLoanBean> otherLoanBeanList) {
        this.otherLoanBeanList = otherLoanBeanList;
    }

    public OtherLoan(){
      this.otherLoanBeanList= new ArrayList<>();
  }


}
