package com.htsec.service.dto;

import org.apache.commons.lang.StringUtils;

/**
 * Description : TODO
 * Date : 2017/3/30 14:12
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public class KV {
    private String type;
    private String unit;
    private String key;
    private long vaule;

    public KV(String type, String unit, String key, String vaule) {
        this.type = type;
        this.unit = unit;
        this.key = key;
        this.vaule = Long.parseLong(vaule);
    }

    /**
     * 针对点击量趋势 可能会按照年、月、日、小时维度进行查询.
     *
     * @return
     */
    public String getKey() {
        if (StringUtils.equals(type, "clickTrend")) {
            if (StringUtils.equals(unit, "today") || StringUtils.equals(unit, "yesterday")) {
                return hourOfDay(key);
            } else if (StringUtils.equals(unit, "month") || StringUtils.equals(unit, "year")) {
                return monthOfYear(key);
            } else {
                return key;
            }
        } else {
            return key;
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getVaule() {
        return vaule;
    }

    public void setVaule(long vaule) {
        this.vaule = vaule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取年份-月份
     *
     * @return
     */
    public String monthOfYear(String key) {
        return StringUtils.substringBeforeLast(key, "-");
    }

    /**
     * 获取小时
     *
     * @return
     */
    public String hourOfDay(String key) {
        return StringUtils.substringAfterLast(String.valueOf(key), "-");
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
