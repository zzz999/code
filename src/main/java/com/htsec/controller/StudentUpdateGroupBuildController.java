package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.FHinfo;
import com.htsec.Student.beans.GroupInfo;
import com.htsec.Student.beans.ZHInfo;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentUpdateGroupBuildController {
    private static final Logger logger = Logger.getLogger(StudentUpdateGroupBuildController.class);

    @RequestMapping(value = "/queryGroupInfo", method = RequestMethod.GET)
    public void queryGroupInfo(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        JSONObject result = new JSONObject();
        String code =requestJson.getString("code");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo==null){
            result.put("result","false");
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }else {
            if(bankInfo.getZhInfoList()==null||bankInfo.getZhInfoList().size()==0){
                result.put("result","false");
                result.put("info","请先建设分行");
                try {
                    response.getWriter().write(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            List<FHinfo> fHinfoList=bankInfo.getZhInfoList();
            List<ZHInfo>zhInfoList=fHinfoList.get(0).getZhInfoList();
            if(zhInfoList==null||zhInfoList.size()==0){
                result.put("result","false");
                result.put("info","请先建设支行");
                try {
                    response.getWriter().write(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            JSONArray jsonArray = new JSONArray();
            for(ZHInfo zhInfo:zhInfoList){
                JSONObject jsonObject  = new JSONObject();
                jsonObject.put("zhName",zhInfo.getZHname());
                jsonObject.put("groupInfo",zhInfo.getGroupInfo());
                jsonArray.add(jsonObject);
            }
            result.put("groupInfo",jsonArray);
            result.put("result","true");

        }

    }

    @RequestMapping(value = "/updateGroupInfo", method = RequestMethod.GET)
    public void updateGroupInfo(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        JSONObject result = new JSONObject();
        String code =requestJson.getString("code");
        JSONArray updateGroupInfo=requestJson.getJSONArray("updateGroupInfo");

        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo==null){
            result.put("result","false");
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }else {
            if (bankInfo.getZhInfoList() == null || bankInfo.getZhInfoList().size() == 0) {
                result.put("result", "false");
                result.put("info", "请先建设分行");
                try {
                    response.getWriter().write(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            List<FHinfo> fHinfoList = bankInfo.getZhInfoList();
            List<ZHInfo> zhInfoList = fHinfoList.get(0).getZhInfoList();

            for(Object gropInfo:updateGroupInfo){
                JSONObject obj = (JSONObject) gropInfo;
                String zhName=obj.getString("zhName");
                String dkGroup=obj.getString("dkGroup");
                String ckGroup =obj.getString("ckGroup");
                boolean match =false;
                for(ZHInfo  zhInfo:zhInfoList){
                    if(zhInfo.getZHname().equalsIgnoreCase(zhName)){
                        match =true;
                        if(zhInfo.getGroupInfo()==null){
                            zhInfo.setGroupInfo(new GroupInfo());
                        }
                        if(dkGroup.equalsIgnoreCase("true")){
                            zhInfo.getGroupInfo().getLoanGroup().setLoanGroupBuildTime(bankInfo.getTime());
                        }
                        if(ckGroup.equalsIgnoreCase("true")){
                            zhInfo.getGroupInfo().getDepositGroup().setDepositGroupBuiltTime(bankInfo.getTime());
                        }
                    }
                }
                if(match==false){
                    result.put("result","false");
                    result.put("info","未找到支行名："+zhName);
                    try {
                        response.getWriter().write(result.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

            }

            result.put("result","true");
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
