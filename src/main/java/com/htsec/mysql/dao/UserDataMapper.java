package com.htsec.mysql.dao;

import com.htsec.mysql.dto.swcompositescore;
import com.htsec.service.dto.MasterOverview;
import com.htsec.service.dto.StockInfo;
import com.htsec.service.dto.SwfundAssetmonth;
import com.htsec.service.dto.UserOverview;

import java.util.List;

public interface UserDataMapper {
    public swcompositescore getbyaccount(int account);
    public List<UserOverview> getListByAccountAndTime(int account,int timestart,int timeend);
    public List<MasterOverview> getMasterOverviewByAccountAndTime(int timestart,int timeend);
    public List<StockInfo> getStockInfoByAccount(String account,int month);
    public List<StockInfo> getStockInfoByAccountASC(String account,int month);
    public List<SwfundAssetmonth> getSwfundAssetMonthInfo(String account,int monthBegin,int monthEnd);
}
