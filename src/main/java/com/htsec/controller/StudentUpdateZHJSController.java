package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.FHinfo;
import com.htsec.Student.beans.ZHInfo;
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
import java.util.List;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class StudentUpdateZHJSController {
    private static final Logger logger = Logger.getLogger(StudentUpdateZHJSController.class);

    @RequestMapping(value = "/updateZHJS", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String requestQueryString = CodeHelper.decode(request.getQueryString());
       // String requestQueryString = request.getQueryString();
        JSONObject requestJson = JSONObject.fromObject(requestQueryString);
        String code =requestJson.getString("code");
        String zhCount =requestJson.getString("zhCount");
        BankInfo bankInfo = StudentProcessManager.getBankInfoHashMap().get(code);
        JSONObject result = new JSONObject();
        if(bankInfo==null){
            result.put("result","false");
        }else {
          List<FHinfo> bankInfoZhInfoList= bankInfo.getZhInfoList();
          if(bankInfoZhInfoList==null|| bankInfoZhInfoList.size()==0){
              result.put("result","false");
              result.put("info","请先建设分行");
          }else {
                FHinfo fHinfo =bankInfoZhInfoList.get(0);
                List<ZHInfo> zhInfoList =fHinfo.getZhInfoList();
                if(zhInfoList ==null){
                    fHinfo.setZhInfoList(new ArrayList<ZHInfo>());
                }

                //TODO 扣钱

                int count = fHinfo.getZhInfoList().size();
                for(int i=0;i<Integer.parseInt(zhCount);i++){
                    ZHInfo zhInfov= new ZHInfo();
                    zhInfov.setZHname((count+1)+"");
                    zhInfov.setZHbuildTime(bankInfo.getTime());
                    fHinfo.getZhInfoList().add(zhInfov);
                }
                result.put("result","true");
            }

        }
        //JSONObject result = new JSONObject();
        //result.put("result","ok");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
