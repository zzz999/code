package com.htsec.service.cache;

import com.htsec.commons.utils.StringUtil;
import com.htsec.commons.utils.TimeUtil;
import com.htsec.controller.UserClickDetailController;
import com.htsec.hbase.utils.HBaseHelper;
import com.htsec.service.dto.ClickDetail;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bernard on 2017/3/27.
 */
public class ClickDetailCache {
    private static final Logger logger = Logger.getLogger(ClickDetailCache.class);

    private static ConcurrentHashMap<String, HashMap<String, ClickDetail>> cache = new ConcurrentHashMap<String, HashMap<String, ClickDetail>>();

    public static HashMap<String, ClickDetail> getData(String day) {

        HashMap<String, ClickDetail> cacheMap = cache.get(day);
        //没有数据 或者 数据为空 再取一遍
        if (cacheMap == null || cacheMap.isEmpty()) {
            cacheMap= getDataFromHbase(day, TimeUtil.getSpecifiedDayAfter(day));
        }
        //数据过期  再取一遍
        if (isExpired(cacheMap, System.currentTimeMillis(), 1 * 60 * 1000)) {
            logger.info("reCache Data;");
            cacheMap=getDataFromHbase(day, TimeUtil.getSpecifiedDayAfter(day));
        }
        logger.info("getData from hbase or cache day"+day);
        for (Map.Entry<String,ClickDetail> entry: cacheMap.entrySet()){
            logger.info("key:"+entry.getKey()+"|value:"+entry.getValue().toString());
        }

        return cacheMap;
    }

    private static HashMap<String, ClickDetail> getDataFromHbase(String startTime, String endTime) {
        HashMap<String, ClickDetail> map = new HashMap<String, ClickDetail>();
        ResultScanner rs = null;
        try {
            rs = HBaseHelper.getResultScann("userClickDetail", "click_" + startTime, "click_" + endTime);
            for (Result r : rs) {
                for (KeyValue kv : r.list()) {
                    //System.out.println("row:" + Bytes.toString(kv.getRow()));
                    String row =  Bytes.toString(kv.getRow());
                    String [] keys=row.split("_");
                    String key = keys[2]+"_"+keys[3]+"_"+keys[4];
                    ClickDetail detail=map.get(key);
                    if(detail==null){
                        detail = new ClickDetail();
                        detail.setCacheTime(Long.toString(System.currentTimeMillis()));
                        map.put(key,detail);
                    }
                    String name =Bytes.toString(kv.getQualifier());
                    String value= Bytes.toString(kv.getValue());
                    Method setMethod =detail.getClass().getMethod("set"+ StringUtil.captureName(name),String.class);
                    setMethod.invoke(detail,value);
                    //Bytes.toString(kv.getQualifier());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return map;
    }

    private static boolean isExpired(HashMap<String, ClickDetail> map, long now, long saveTime) {
        if (map.isEmpty()) {
            return true;
        }
        for (Map.Entry<String, ClickDetail> entry : map.entrySet()) {
            ClickDetail clickDetail = entry.getValue();
            if (now - Long.parseLong(clickDetail.getCacheTime()) > saveTime) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        HashMap<String, ClickDetail> map =  getDataFromHbase("2017-03-24","2017-03-25");
       map.size();

    }
}
