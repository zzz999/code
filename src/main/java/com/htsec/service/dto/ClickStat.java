package com.htsec.service.dto;

/**
 * Description : 返回前台用户行为统计Json数据序列化之前的POJO.
 * Date : 2017/3/31 9:08
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public class ClickStat {
    private String period;
    //X坐标轴
    private Object xAxis;
    //Y坐标轴
    private Object yAxis;

    public ClickStat(String period, Object xAxis, Object yAxis) {
        this.period = period;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Object getxAxis() {
        return xAxis;
    }

    public void setxAxis(Object xAxis) {
        this.xAxis = xAxis;
    }

    public Object getyAxis() {
        return yAxis;
    }

    public void setyAxis(Object yAxis) {
        this.yAxis = yAxis;
    }
}
