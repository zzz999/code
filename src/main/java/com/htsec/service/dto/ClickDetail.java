package com.htsec.service.dto;

/**
 * Created by bernard on 2017/3/27.
 */
public class ClickDetail {
    private String name;
    private String subType;
    private String version;
    private String channel;
    private String clickKey;
    private String clickType;
    private String count;
    private String cacheTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClickKey() {
        return clickKey;
    }

    public void setClickKey(String clickKey) {
        this.clickKey = clickKey;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(String cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public String toString() {
        return "ClickDetail{" +
                "name='" + name + '\'' +
                ", subType='" + subType + '\'' +
                ", version='" + version + '\'' +
                ", channel='" + channel + '\'' +
                ", clickKey='" + clickKey + '\'' +
                ", clickType='" + clickType + '\'' +
                ", count='" + count + '\'' +
                ", cacheTime='" + cacheTime + '\'' +
                '}';
    }
}
