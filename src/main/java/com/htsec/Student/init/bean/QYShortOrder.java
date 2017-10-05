package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class QYShortOrder {
    private List<QYShortOrderBean> shortOrderBeans;

    public QYShortOrder() {
        this.shortOrderBeans = new ArrayList<>();
    }

    public List<QYShortOrderBean> getShortOrderBeans() {
        return shortOrderBeans;
    }

    public void setShortOrderBeans(List<QYShortOrderBean> shortOrderBeans) {
        this.shortOrderBeans = shortOrderBeans;
    }
}
