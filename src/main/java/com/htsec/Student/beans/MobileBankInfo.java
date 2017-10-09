package com.htsec.Student.beans;

public class MobileBankInfo {
    private String mobileBankBuildTime;

    public MobileBankInfo(){
        this.mobileBankBuildTime ="0";
    }


    public String getMobileBankBuildTime() {
        return mobileBankBuildTime;
    }

    public void setMobileBankBuildTime(String mobileBankBuildTime) {
        this.mobileBankBuildTime = mobileBankBuildTime;
    }
}
