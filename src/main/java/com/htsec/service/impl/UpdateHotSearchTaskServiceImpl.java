package com.htsec.service.impl;

import com.htsec.commons.utils.TimeUtil;
import com.htsec.hbase.utils.HBaseHelper;
import com.htsec.mysql.dto.HotSearch;
import com.htsec.mysql.service.UserHotSearchService;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by bernard on 2017/7/10.
 */
@Service
public class UpdateHotSearchTaskServiceImpl extends TimerTask {
    private static final Logger logger = Logger.getLogger(UpdateHotSearchTaskServiceImpl.class);
    private  static final String HOTSEARCHTABLE="userhotsearch";
    private static final String HOTSEARCHFAMILY="hotsearchfamily";

    @Autowired
    private UserHotSearchService userHotSearchService;

    @Override
    public void run() {
        logger.info("Task Start: UpdateHotSearchTask");
        try {
            ResultScanner rs=HBaseHelper.getResultScann(HOTSEARCHTABLE, TimeUtil.getNow(), TimeUtil.getSpecifiedDayAfter(TimeUtil.getNow()));
            if(rs==null){
                logger.error("get a null result from "+HOTSEARCHTABLE+" startRow:"+TimeUtil.getNow()+" endRow:"+ TimeUtil.getSpecifiedDayAfter(TimeUtil.getNow()));
                return;
            }
            List<HotSearch> hotSearchList = new ArrayList<>();
            for(Result result:rs){
                String count=Bytes.toString(result.getValue(Bytes.toBytes(HOTSEARCHFAMILY),Bytes.toBytes("count")));
                String clickKey=Bytes.toString(result.getValue(Bytes.toBytes(HOTSEARCHFAMILY),Bytes.toBytes("clickKey")));
                String rowKey=Bytes.toString(result.getRow());
                HotSearch hotSearchbean = new HotSearch();
                if(clickKey!=null&&clickKey!=""){
                    hotSearchbean.setClickKey(clickKey);
                    hotSearchbean.setCount(Long.parseLong(count));
                    hotSearchbean.setRowKey(rowKey);
                    if(hotSearchbean.getCount()!=0) {
                        hotSearchList.add(hotSearchbean);
                    }
                }
            }
            logger.info("有效数据条数："+hotSearchList.size());
            userHotSearchService.updateHotSearchService(hotSearchList);
            List<Put> putList= formatPut(hotSearchList);
            HBaseHelper.save(putList,HOTSEARCHTABLE);
        }catch (Exception e){
            logger.error("get info from hbase failed",e);
        }

    }

    private List<Put> formatPut( List<HotSearch> hotSearchList){
        ArrayList<Put> puts = new ArrayList<>();
        for(HotSearch hotSearch:hotSearchList){
            Put put = new Put(Bytes.toBytes(hotSearch.getRowKey()));
            put.add(Bytes.toBytes(HOTSEARCHFAMILY), Bytes.toBytes("count"),
                    Bytes.toBytes("0"));
            puts.add(put);

        }
        return puts;
    }


}
