package com.htsec.Student.beans;

public class DepositGroup {
    private String DepositGroupBuiltTime;

    public String getDepositGroupBuiltTime() {
        return DepositGroupBuiltTime;
    }

    public void setDepositGroupBuiltTime(String depositGroupBuiltTime) {
        DepositGroupBuiltTime = depositGroupBuiltTime;
    }
    public DepositGroup(){
        this.DepositGroupBuiltTime="";
    }
}
