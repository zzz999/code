package com.htsec.Student.beans;

public class ZHInfo {
    private String ZHname;
    private String ZHbuildTime;
    private GroupInfo groupInfo;

    public String getZHname() {
        return ZHname;
    }

    public void setZHname(String ZHname) {
        this.ZHname = ZHname;
    }

    public String getZHbuildTime() {
        return ZHbuildTime;
    }

    public void setZHbuildTime(String ZHbuildTime) {
        this.ZHbuildTime = ZHbuildTime;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}
