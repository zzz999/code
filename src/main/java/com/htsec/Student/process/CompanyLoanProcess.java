package com.htsec.Student.process;

import com.htsec.Student.beans.StudentOrderSelectBean;
import com.htsec.Student.init.bean.QYLongOrder;
import com.htsec.Student.init.bean.QYShortOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyLoanProcess {
    private String nowCode;
    private boolean isStart =false;
    private QYLongOrder qyLongOrder;//企业长订单
    private QYShortOrder qyShortOrder;//企业短订单
    private String type;//类型
    private List<StudentOrderSelectBean> studentOrderSelectBeanList;
    private int next =0;
    private HashMap<String,List<String>> selectedOrders;

    public CompanyLoanProcess(){
        this.studentOrderSelectBeanList= new ArrayList<>();
        this.selectedOrders = new HashMap<>();
    }


    public String getNowCode() {
        return nowCode;
    }

    public void setNowCode(String nowCode) {
        this.nowCode = nowCode;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public QYLongOrder getQyLongOrder() {
        return qyLongOrder;
    }

    public void setQyLongOrder(QYLongOrder qyLongOrder) {
        this.qyLongOrder = qyLongOrder;
    }

    public QYShortOrder getQyShortOrder() {
        return qyShortOrder;
    }

    public void setQyShortOrder(QYShortOrder qyShortOrder) {
        this.qyShortOrder = qyShortOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<StudentOrderSelectBean> getStudentOrderSelectBeanList() {
        return studentOrderSelectBeanList;
    }

    public void setStudentOrderSelectBeanList(List<StudentOrderSelectBean> studentOrderSelectBeanList) {
        this.studentOrderSelectBeanList = studentOrderSelectBeanList;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public HashMap<String, List<String>> getSelectedOrders() {
        return selectedOrders;
    }

    public void setSelectedOrders(HashMap<String, List<String>> selectedOrders) {
        this.selectedOrders = selectedOrders;
    }
}
