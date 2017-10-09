package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class TXOrder {
    private List<TXOrderBean> txOrderBeans;

    public TXOrder(){
        this.txOrderBeans = new ArrayList<>();
    }

    public List<TXOrderBean> getTxOrderBeans() {
        return txOrderBeans;
    }

    public void setTxOrderBeans(List<TXOrderBean> txOrderBeans) {
        this.txOrderBeans = txOrderBeans;
    }
}
