package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class QYLongOrder {
    private List<QYLongOrderBean> longOrders;

    public List<QYLongOrderBean> getLongOrders() {
        return longOrders;
    }

    public void setLongOrders(List<QYLongOrderBean> longOrders) {
        this.longOrders = longOrders;
    }

    public QYLongOrder (){
        this.longOrders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "QYLongOrder{" +
                "longOrders=" + longOrders +
                '}';
    }
}
