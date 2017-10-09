package com.htsec.Student.beans;

public class QDInfo {
    private NetBankInfo netBankInfo;
    private MobileBankInfo mobileBankInfo;

    public QDInfo() {
        this.netBankInfo = new NetBankInfo();
        this.mobileBankInfo = new MobileBankInfo();
    }

    public NetBankInfo getNetBankInfo() {
        return netBankInfo;
    }

    public void setNetBankInfo(NetBankInfo netBankInfo) {
        this.netBankInfo = netBankInfo;
    }

    public MobileBankInfo getMobileBankInfo() {
        return mobileBankInfo;
    }

    public void setMobileBankInfo(MobileBankInfo mobileBankInfo) {
        this.mobileBankInfo = mobileBankInfo;
    }
}
