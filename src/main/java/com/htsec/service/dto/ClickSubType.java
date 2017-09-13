package com.htsec.service.dto;

/**
 * Description : TODO
 * Date : 2017/4/5 16:01
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public class ClickSubType {
    private String id;
    private String clickTypeId;
    private String subTypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClickTypeId() {
        return clickTypeId;
    }

    public void setClickTypeId(String clickTypeId) {
        this.clickTypeId = clickTypeId;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }
}
