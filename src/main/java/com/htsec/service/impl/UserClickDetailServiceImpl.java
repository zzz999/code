package com.htsec.service.impl;

import com.htsec.commons.utils.*;
import com.htsec.service.UserClickDetailService;
import com.htsec.service.cache.ClickDetailCache;
import com.htsec.service.dto.ClickDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;


/**
 * 用户点击量详情
 * Created by bernard on 2017/3/27.
 */
@Service
public class UserClickDetailServiceImpl implements UserClickDetailService{
   private static Logger logger = Logger.getLogger(UserClickDetailServiceImpl.class);
    @Override
    public void getClickDetailInfo(JSONObject requestJson,HttpServletResponse response) {
        String startTime= JSONUtil.safeGetString(requestJson, CommonKeys.STARTTIME);
        String endTime=JSONUtil.safeGetString(requestJson,CommonKeys.ENDTIME);
        String channel=JSONUtil.safeGetString(requestJson,CommonKeys.CHANNEL);
        String version=JSONUtil.safeGetString(requestJson,CommonKeys.VERSION);
        String levelone=JSONUtil.safeGetString(requestJson,CommonKeys.LEVELONE);
        String leveltwo=JSONUtil.safeGetString(requestJson,CommonKeys.LEVELTWO);
        String offset =JSONUtil.safeGetString(requestJson,CommonKeys.OFFSET);
        String filterName=JSONUtil.safeGetString(requestJson,CommonKeys.NAME);
        String length=JSONUtil.safeGetString(requestJson,CommonKeys.LENGTH);
        List<String> dayList = null;
        try {
            dayList= TimeUtil.findDates(startTime,endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dayList==null||dayList.isEmpty()){
            try {
                response.getWriter().write("Input date error");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, ClickDetail> results = new  HashMap<String, ClickDetail>();
        for(String e:dayList){
            mergeMap(ClickDetailCache.getData(e),results);
        }
        ArrayList<ClickDetail> arrayList = new ArrayList<ClickDetail>();
        for(Map.Entry<String,ClickDetail> a:results.entrySet()){
            arrayList.add(a.getValue());
        }
        logger.info("after merge:");
        for (ClickDetail entry: arrayList){
           // logger.info(entry.toString());
        }
        Collections.sort(arrayList, new CountCompare());
        logger.info("after sort:");
        for (ClickDetail entry: arrayList){
           // logger.info(entry.toString());
        }
        if(channel!=null){

        }
        if(filterName!=null&&filterName!=""){
            filterByName(filterName,arrayList);
        }
        if(levelone!=null&&levelone!=""){
            filterBySubType(levelone,arrayList);
        }
       ArrayList A = ArrayListUtil.getDefinedList(Integer.parseInt(offset),Integer.parseInt(length),arrayList);
        JSONObject result = new JSONObject();
        JSONArray dataList=JSONArray.fromObject(A);
        result.put(CommonKeys.TOTALNUM,arrayList.size());
        result.put(CommonKeys.DATALIST,dataList);
        logger.info("result:"+result.toString());
        CommonResponseWriter.write(result.toString(),response);

    }

    private static void filterByName(String value,ArrayList<ClickDetail> arrayList){
        ArrayList<ClickDetail> deletList= new ArrayList<>();
        for(ClickDetail e:arrayList){
            if(e.getName().contains(value)==false){
                deletList.add(e);
            }
        }
        for(ClickDetail del:deletList){
            arrayList.remove(del);
        }
    }

    private static void filterByVersion(String version,ArrayList<ClickDetail> arrayList){
        ArrayList<ClickDetail> deletList= new ArrayList<>();
        for(ClickDetail e:arrayList){
            if(e.getVersion().equalsIgnoreCase(version)==false){
                deletList.add(e);
            }
        }
        for(ClickDetail del:deletList){
            arrayList.remove(del);
        }
    }

    private static void filterBySubType(String type,ArrayList<ClickDetail> arrayList){
        ArrayList<ClickDetail> deletList= new ArrayList<>();
        for(ClickDetail e:arrayList){
            if(e.getSubType().equalsIgnoreCase(type)==false){
                deletList.add(e);
            }
        }
        for(ClickDetail del:deletList){
            arrayList.remove(del);
        }
    }



    private static  HashMap<String, ClickDetail> mergeMap( HashMap<String, ClickDetail> resource, HashMap<String, ClickDetail> temp){
        if(resource.isEmpty()){
            return temp;
        }
        for(Map.Entry<String,ClickDetail> entry:resource.entrySet()){
            ClickDetail tempDetail=temp.get(entry.getKey());
            ClickDetail resDetail = entry.getValue();
            if(tempDetail==null){
                ClickDetail detail = new ClickDetail();
                detail.setChannel(resDetail.getChannel());
                detail.setCacheTime(Long.toString(System.currentTimeMillis()));
                detail.setClickKey(resDetail.getClickKey());
                detail.setClickType(resDetail.getClickType());
                detail.setCount(resDetail.getCount());
                detail.setName(resDetail.getName());
                detail.setSubType(resDetail.getSubType());
                detail.setVersion(resDetail.getVersion());
                temp.put(entry.getKey(),detail);
            }else {
                tempDetail.setCount(Long.toString(Long.parseLong(tempDetail.getCount())+Long.parseLong(resDetail.getCount())));
            }
        }

        return temp;

    }
}
