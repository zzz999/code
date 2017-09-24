package com.htsec.mysql.service.impl;

import com.htsec.commons.utils.TimeUtil;
import com.htsec.mysql.dao.UserDataMapper;
import com.htsec.mysql.dto.swcompositescore;
import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.cache.MasterOverViewCache;
import com.htsec.service.dto.MasterOverview;
import com.htsec.service.dto.OperationAnalysis;
import com.htsec.service.dto.StockInfo;
import com.htsec.service.dto.UserOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataBaseServiceImpl implements UserDataBaseService{

    @Autowired
    private UserDataMapper userDataMapper;


    @Override
    public swcompositescore getswbyaccount(int account) {
       return userDataMapper.getbyaccount(account);
    }


    @Override
    public List<UserOverview> getUserOverviewByAccout(int account, int starttime, int endtime) {
        return userDataMapper.getListByAccountAndTime(account,starttime,endtime);
    }

    @Override
    public List<MasterOverview> getMasterOverview(int starttime, int endtime) {
        if(checkMasterOverviewCache(starttime,endtime)==true){
           return MasterOverViewCache.getCacheData();
        }else {
            List<MasterOverview> result = userDataMapper.getMasterOverviewByAccountAndTime(starttime,endtime);
            if(result.size()!=0){
                MasterOverViewCache.setCacheData(result);
                MasterOverViewCache.setCatcheTime(System.currentTimeMillis());
                MasterOverViewCache.setStarttime(starttime);
                MasterOverViewCache.setEndtime(endtime);
            }
            return result;
        }
    }



    @Override
    public List<StockInfo> getStockInfoByAccount(String account) {
       return userDataMapper.getStockInfoByAccount(account, Integer.parseInt(TimeUtil.getMonth()));
    }

    @Override
    public OperationAnalysis getOperationAnalysis() {
        return null;
    }

    private boolean checkMasterOverviewCache(int starttime,int endtime){
        if(MasterOverViewCache.getStarttime()==0
                ||MasterOverViewCache.getStarttime()!=starttime
                ||MasterOverViewCache.getEndtime()==0
                ||MasterOverViewCache.getEndtime()!=endtime
                ||(System.currentTimeMillis()-MasterOverViewCache.getCatcheTime())>60*60*1000)
        {
            return false;
        }else {
            return true;
        }
    }

}
