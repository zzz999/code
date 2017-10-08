package com.htsec.Student.init.bean;

import java.util.ArrayList;
import java.util.List;

public class CompanyInfo {
    private List<ArrayList<String>> ZHFYtable;
    private List<ArrayList<String>> LRtable;
    private List<ArrayList<String>> ZCFZtable;

    public List<ArrayList<String>> getZHFYtable() {
        return ZHFYtable;
    }

    public void setZHFYtable(List<ArrayList<String>> ZHFYtable) {
        this.ZHFYtable = ZHFYtable;
    }

    public List<ArrayList<String>> getLRtable() {
        return LRtable;
    }

    public void setLRtable(List<ArrayList<String>> LRtable) {
        this.LRtable = LRtable;
    }

    public List<ArrayList<String>> getZCFZtable() {
        return ZCFZtable;
    }

    public void setZCFZtable(List<ArrayList<String>> ZCFZtable) {
        this.ZCFZtable = ZCFZtable;
    }
}
