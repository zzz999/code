package com.htsec.mysql.service;

import com.htsec.mysql.dto.swcompositescore;
import com.htsec.service.dto.MasterOverview;
import com.htsec.service.dto.OperationAnalysis;
import com.htsec.service.dto.StockInfo;
import com.htsec.service.dto.UserOverview;

import java.util.List;

public interface UserDataBaseService {
    public swcompositescore getswbyaccount(int account);
    public List<UserOverview> getUserOverviewByAccout(int account,int starttime,int endtime);
    public List<MasterOverview> getMasterOverview(int starttime,int endtime);
    public List<StockInfo> getStockInfoByAccount(String account);
    public OperationAnalysis getOperationAnalysis();


}
