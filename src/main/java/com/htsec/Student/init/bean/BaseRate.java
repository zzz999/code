package com.htsec.Student.init.bean;

import java.util.HashMap;
import java.util.List;

public class BaseRate {
    private HashMap<String ,List<BaseRateBean>> hashMap;//<年，listBean>

    public HashMap<String, List<BaseRateBean>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, List<BaseRateBean>> hashMap) {
        this.hashMap = hashMap;
    }

    public BaseRate(){
        this.hashMap =new HashMap<String,List<BaseRateBean>>();
    }
}
