package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.process.StudentProcessManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentLoginController {
    private static final Logger logger = Logger.getLogger(StudentQueryCompanyInfoController.class);


    @RequestMapping(value = "/studentLogin", method = RequestMethod.GET)
    public void studentLogin(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        if(StudentProcessManager.getBankInfoHashMap().containsKey(queryObj.getString("code"))==false){
            result.put("result","false");
        }else {
           BankInfo info= StudentProcessManager.getBankInfoHashMap().get(queryObj.getString("code"));
           info.setName(queryObj.getString("name"));
           result.put("result","true");
           result.put("code",queryObj.getString("code"));
        }

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;



    }

    @RequestMapping(value = "/teacherInitStudent", method = RequestMethod.GET)
    public void teacherInitStudent(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        int studentNum = Integer.parseInt(queryObj.getString("studentNum"));
        if(StudentProcessManager.getBankInfoHashMap().size()>=studentNum){
            ArrayList<String> arrayList = new ArrayList<>();
            for(String key:StudentProcessManager.getBankInfoHashMap().keySet()){
                arrayList.add(key);
            }
            result.put("codes",arrayList);
        }else {
            int oldSize =studentNum-StudentProcessManager.getBankInfoHashMap().size();
            for(int i=0;i<oldSize;i++){
                BankInfo bankInfo = new BankInfo();
                String code = Math.abs((System.currentTimeMillis()+"bank"+i).hashCode())+"";
                StudentProcessManager.getBankInfoHashMap().put(code,bankInfo);
            }
            ArrayList<String> arrayList = new ArrayList<>();
            for(String key:StudentProcessManager.getBankInfoHashMap().keySet()){
                arrayList.add(key);
            }
            result.put("codes",arrayList);
        }

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/queryStudents", method = RequestMethod.GET)
    public void queryStudents(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        List<Map> arrayList = new ArrayList<>();
        HashMap<String, BankInfo> bankInfoHashMap=StudentProcessManager.getBankInfoHashMap();
        for(String key:bankInfoHashMap.keySet()){
            if(bankInfoHashMap.get(key).getName()==null) continue;
            Map<String,String> fields=new HashMap<>();
            fields.put("code",key);
            fields.put("name",bankInfoHashMap.get(key).getName());
            arrayList.add(fields);
        }
        result.put("list",arrayList);

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
