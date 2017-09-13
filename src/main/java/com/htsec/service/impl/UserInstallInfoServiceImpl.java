package com.htsec.service.impl;

import com.htsec.commons.utils.*;
import com.htsec.hbase.utils.HBaseHelper;
import com.htsec.hbase.utils.HBaseUtil;
import com.htsec.service.UserClickDictionaryService;
import com.htsec.service.UserInstallInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.util.*;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.random;

/**
 * Created by bernard on 2017/5/4.
 */

@Service
public class UserInstallInfoServiceImpl implements UserInstallInfoService {
    private static Logger logger = Logger.getLogger(UserInstallInfoServiceImpl.class);
    @Autowired
    private UserClickDictionaryService userClickDictionaryService;


    @Override
    public void getRegistInfo(JSONObject input, HttpServletResponse response) {
        logger.info("start to get regist info");

        JSONObject res = new JSONObject();
        String dateString= TimeUtil.getNow();
        String lastDateString =TimeUtil.getYesterday();
        Result uvresult= null;
        Result lasteRestul =null;
        try {
            logger.info("start to get UserClickStat info");
            uvresult = HBaseHelper.getResult(CommonTableName.USER_CLICK_STAT,dateString+"_UV");
            lasteRestul =HBaseHelper.getResult(CommonTableName.USER_CLICK_STAT,lastDateString+"_UV");
            String active=Bytes.toString(uvresult.getValue(Bytes.toBytes("stat"),Bytes.toBytes("count")));
            String yesterdayActive=Bytes.toString(lasteRestul.getValue(Bytes.toBytes("stat"),Bytes.toBytes("count")));
            if(active==null){
                active="";
            }
            if(yesterdayActive==null){
                yesterdayActive="";
            }
            res.put(CommonKeys.ACTIVE,active);
            res.put(CommonKeys.YESTERDAYACT,yesterdayActive);
        } catch (Exception e) {
            logger.error("failed to get UserClickStat info from hbase",e);
        }
        logger.info("get UserClickStat info end");

        try {
            logger.info("start to get registUser info");
          Result result =  HBaseHelper.getResult(CommonTableName.REGIST_USER, dateString);
          Result result1 =  HBaseHelper.getResult(CommonTableName.REGIST_USER, lastDateString);
            String regist="0";
            String yesterdayReg="0";
            if(result!=null) {
               regist = Bytes.toString(result.getValue(Bytes.toBytes("usercid"), Bytes.toBytes("regist")));
                if(regist==null){
                    regist="0";
                }
          }
          if(result1!=null) {
               yesterdayReg = Bytes.toString(result1.getValue(Bytes.toBytes("usercid"), Bytes.toBytes("regist")));
              if(yesterdayReg==null){
                  yesterdayReg="0";
              }
          }
          res.put(CommonKeys.TOTAL_REGIST,getTotalRegist());
          res.put(CommonKeys.TOTAL_ACTIVE,getTotalRegist());
          res.put(CommonKeys.REGIST,regist);
          res.put(CommonKeys.YESTERDAYREG,yesterdayReg);
          res.put(CommonKeys.PROVICEARRAY,getTop10provice());
          res.put(CommonKeys.CHANNELARRAY,getTop10channel());
          res.put(CommonKeys.DEVICEARRAY,getTop10device());
          res.put(CommonKeys.INSTALLHIS,getWeekInstallInfo());
          res.put(CommonKeys.NEWPERCENT, PercentUtil.getIntPercent(regist,getTotalRegist()));
          res.put(CommonKeys.YESTERDAYNEWPERCENT,PercentUtil.getIntPercent(yesterdayReg,getTotalRegist()));
          res.put(CommonKeys.ACTIVEPERCENT,PercentUtil.getIntPercent(res.getString(CommonKeys.ACTIVE),getTotalRegist()));
          res.put(CommonKeys.YESTERDAYACTIVEPERCENT,PercentUtil.getIntPercent(res.getString(CommonKeys.YESTERDAYACT),getTotalRegist()));
          CommonResponseWriter.write(res.toString(),response);
        }catch (Exception e){
            logger.info("failed to get registUser info from hbase",e);

        }
    }

    private static JSONObject getWeekInstallInfo(){
        JSONObject obj = new JSONObject();
        JSONArray now =new JSONArray();
        JSONArray last = new JSONArray();
        try {
            List<String> week =TimeUtil.getThisWeek();
            for(String day:week){
                JSONObject data = new JSONObject();
                data.put("day",day);
                data.put("count",getInstallInfobyDay(day));
                now.add(data);
            }
            List<String> lastweek =TimeUtil.getLastWeek();
            for(String lastday:lastweek){
                JSONObject data = new JSONObject();
                data.put("day",lastday);
                data.put("count",getInstallInfobyDay(lastday));
                last.add(data);
            }
            obj.put("now",now);
            obj.put("last",last);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;

    }

     private static String getInstallInfobyDay(String day) throws Exception {
         Result result =  HBaseHelper.getResult(CommonTableName.REGIST_USER, day);
         if (result!=null){
             if(Bytes.toString(result.getValue(Bytes.toBytes("usercid"), Bytes.toBytes("regist")))!=null){
                 return Bytes.toString(result.getValue(Bytes.toBytes("usercid"), Bytes.toBytes("regist")));
             }else {
                 return "";
             }

         }else {
             return "";
         }
     }


    private static String getTotalRegist() throws Exception {
        logger.info("start to get Total Regist");
        Scan scan = new Scan();
        ResultScanner rs = null;
        HTableInterface table = HBaseUtil.getHConnection().getTable(CommonTableName.REGIST_USER);
        rs=table.getScanner(scan);
        long Totalcount =0;
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                String count= Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("regist")));
                Totalcount+=Long.parseLong(count);
            }
        } catch (Exception e){
            logger.info("getTotalRegist failed",e);
        }finally {
            rs.close();
        }
        return  Long.toString(Totalcount);

    }

    private static JSONArray getTop10provice() throws Exception {
        logger.info("start to get top 10 Provice");
        //String dateString= TimeUtil.getNow();
        JSONArray array = new JSONArray();
        Scan scan = new Scan();
        //RowFilter rf = new RowFilter(CompareFilter.CompareOp.EQUAL , new SubstringComparator( TimeUtil.getNow()));
        //scan.setFilter(rf);
        //scan.setFilter(rf);
        ResultScanner rs = null;
        HTableInterface table = HBaseUtil.getHConnection().getTable(CommonTableName.REGIST_PROVICE);
        rs=table.getScanner(scan);
        long Totalcount =0;
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                JSONObject obj = new JSONObject();
                String provice= Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("provice")));
                String count =Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("count")));
                obj.put("provice",provice);
                obj.put("count",count);
                if(provice!=null&&provice!="") {
                    array.add(obj);
                }
            }
        }catch (Exception e){
            logger.info("get Top10provece failed",e);
        } finally{
            rs.close();
        }
       array= mergeArray(array,"provice","count");
        sort(array,"count",false);
        array= getCount(array,10);
        return  array;
    }

    private static JSONArray getTop10device() throws Exception {
        logger.info("start to get top 10 device");
        JSONArray array = new JSONArray();
        Scan scan = new Scan();
       // RowFilter rf = new RowFilter(CompareFilter.CompareOp.EQUAL , new SubstringComparator( TimeUtil.getNow()));
       // scan.setFilter(rf);
        ResultScanner rs = null;
        HTableInterface table = HBaseUtil.getHConnection().getTable(CommonTableName.REGIST_DEVICE);
        rs=table.getScanner(scan);
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                JSONObject obj = new JSONObject();
                String channel= Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("device")));
                String count =Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("count")));
                obj.put("channel",channel);
                obj.put("count",count);
                array.add(obj);
            }
        }catch (Exception e){
            logger.error("get top 10 device failed",e);
        } finally {
            rs.close();
        }
        array= mergeArray(array,"channel","count");
        sort(array,"count",false);
        array= getCount(array,10);
        return  array;
    }




    private  JSONArray getTop10channel() throws Exception {
        JSONArray array = new JSONArray();
        JSONArray channelJson=JSONArray.fromObject(userClickDictionaryService.queryChannelsString());
        Map<String,String> channelMap = new HashMap<>();
        for(Object obj:channelJson){
            channelMap.put(((JSONObject)obj).getString("channelId"),((JSONObject)obj).getString("channelName"));
        }

        Scan scan = new Scan();
      //  RowFilter rf = new RowFilter(CompareFilter.CompareOp.EQUAL , new SubstringComparator( TimeUtil.getNow()));
     //   scan.setFilter(rf);
        ResultScanner rs = null;
        HTableInterface table = HBaseUtil.getHConnection().getTable(CommonTableName.REGIST_CHANNEL);
        rs=table.getScanner(scan);
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                JSONObject obj = new JSONObject();
                String channel= Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("channel")));
                String count =Bytes.toString( r.getValue(Bytes.toBytes("usercid"),Bytes.toBytes("count")));
                if(channelMap.get(channel)!=null){
                    channel = channelMap.get(channel);
                }
                obj.put("channel",channel);
                obj.put("count",count);
                array.add(obj);
            }
        }catch (Exception e){
            logger.error("get top 10 channel failed",e);
        } finally{
            rs.close();
        }
        array= mergeArray(array,"channel","count");
        sort(array,"count",false);
        array= getCount(array,10);
        return  array;
    }

    private static JSONArray mergeArray(JSONArray array,String key,String valuekey){
        HashMap<String,JSONObject> map = new HashMap();
        for(Object obj: array){
           String jsonKey= ((JSONObject) obj).getString(key);
           String jsonValue=((JSONObject)obj).getString(valuekey);
            if(map.containsKey(jsonKey)){
                JSONObject oldJson=  map.get(jsonKey);
                String oldvalue=oldJson.getString(valuekey);
                oldJson.put(valuekey, Long.parseLong(oldvalue)+Long.parseLong(jsonValue)+"");

            }else {
                map.put(jsonKey,(JSONObject) obj);
            }
        }
        JSONArray result = new JSONArray();
        for(Map.Entry entry:map.entrySet()){
            result.add(entry.getValue());
        }
        return result;
    }

    private static JSONArray getCount(JSONArray array,int count){
        if(array==null){
            return array;
        }
        JSONArray newAr = new JSONArray();
        int i =0;
        for(Object a:array){
            newAr.add(a);
            i++;
            if(i>=count){
                break;
            }
        }
        return newAr;
    }


    private static void sort(JSONArray ja,final String field, boolean isAsc){
        Collections.sort(ja, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String f1 = o1.getString(field);
                String f2 = o2.getString(field);
               return Integer.parseInt(String.valueOf((Long.parseLong(f1)-Long.parseLong(f2))));
            }
        });
        if(!isAsc){
            Collections.reverse(ja);
        }
    }

    public static void main(String[] args) {
        System.out.println( TimeUtil.getNow());

        System.out.println(TimeUtil.getYesterday());
    }




}
