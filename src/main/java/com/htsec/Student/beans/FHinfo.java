package com.htsec.Student.beans;

import java.util.ArrayList;
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

    public FHinfo (){
        this.FHname ="1";
        this.setFHbuildTime("1");
        this.zhInfoList =new ArrayList<>();
        ZHInfo zhInfo = new ZHInfo();
        zhInfo.setZHname("1");
        zhInfo.setZHbuildTime("1");
        GroupInfo groupInfo = new GroupInfo();

        LoanGroup loanGroup = new LoanGroup();
        loanGroup.setLoanGroupBuildTime("1");
        DepositGroup depositGroup = new DepositGroup();
        depositGroup.setDepositGroupBuiltTime("1");
        groupInfo.setLoanGroup(loanGroup);
        groupInfo.setDepositGroup(depositGroup);
        zhInfo.setGroupInfo(groupInfo);
        this.zhInfoList.add(zhInfo);
        //this.zh

    }

    public void setZhInfoList(List<ZHInfo> zhInfoList) {
        this.zhInfoList = zhInfoList;
    }
}
