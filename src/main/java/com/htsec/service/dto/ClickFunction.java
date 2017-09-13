package com.htsec.service.dto;

/**
 * Description : TODO
 * Date : 2017/4/5 16:02
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public class ClickFunction {
    private String id;
    private String functionCode;
    private String functionName;
    private String clickSubTypeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getClickSubTypeId() {
        return clickSubTypeId;
    }

    public void setClickSubTypeId(String clickSubTypeId) {
        this.clickSubTypeId = clickSubTypeId;
    }
}
