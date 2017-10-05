package com.htsec.Student.beans;

import java.util.List;

/**
 * Created by bernard on 2017/9/26.
 */
public class FHinfo {
    private String FHname;
    private String FHbuildTime;
    private List<ZHInfo> zhInfoList;

    public String getFHname() {
        return FHname;
    }

    public void setFHname(String FHname) {
        this.FHname = FHname;
    }

    public String getFHbuildTime() {
        return FHbuildTime;
    }

    public void setFHbuildTime(String FHbuildTime) {
        this.FHbuildTime = FHbuildTime;
    }

    public List<ZHInfo> getZhInfoList() {
        return zhInfoList;
    }

    public void setZhInfoList(List<ZHInfo> zhInfoList) {
        this.zhInfoList = zhInfoList;
    }
}
