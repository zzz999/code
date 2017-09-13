package com.htsec.controller;

import com.htsec.commons.utils.*;
import com.htsec.service.UserClickDetailService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by bernard on 2017/3/24.
 */

@Controller
public class UserClickDetailController {
    private static final Logger logger = Logger.getLogger(UserClickDetailController.class);
    @Autowired
    private UserClickDetailService userClickDetailService;

    public static void main(String[] args) throws UnsupportedEncodingException {
        JSONObject A = new JSONObject();
        A.put(CommonKeys.STARTTIME, "2017-03-24");
        A.put(CommonKeys.ENDTIME, "2017-03-25");
        A.put(CommonKeys.OFFSET, "0");
        A.put("length", 10);
        try {
            //System.out.println(URLEncoder.encode("param="+A.toString(),"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(URLDecoder.decode("%5B%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%224%22%2C%22clickKey%22%3A%2221005%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E5%BD%93%E6%97%A5%E6%88%90%E4%BA%A4%EF%BC%88%E6%99%AE%E9%80%9A%E4%BA%A4%E6%98%93%EF%BC%89%22%2C%22subType%22%3A%22%E8%B5%84%E8%AE%AF%22%2C%22version%22%3A%220.1.1%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%229%22%2C%22clickKey%22%3A%2221015%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E7%8E%B0%E5%88%B8%E8%BF%98%E5%88%B8%EF%BC%88%E4%B8%A4%E8%9E%8D%EF%BC%89%22%2C%22subType%22%3A%22%E4%B8%9A%E5%8A%A1%E5%8A%9E%E7%90%86%22%2C%22version%22%3A%221.0.2%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%228%22%2C%22clickKey%22%3A%2221015%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E7%8E%B0%E5%88%B8%E8%BF%98%E5%88%B8%EF%BC%88%E4%B8%A4%E8%9E%8D%EF%BC%89%22%2C%22subType%22%3A%22%E4%B8%9A%E5%8A%A1%E5%8A%9E%E7%90%86%22%2C%22version%22%3A%221.0.1%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%227%22%2C%22clickKey%22%3A%2221005%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E5%BD%93%E6%97%A5%E6%88%90%E4%BA%A4%EF%BC%88%E6%99%AE%E9%80%9A%E4%BA%A4%E6%98%93%EF%BC%89%22%2C%22subType%22%3A%22%E8%B5%84%E8%AE%AF%22%2C%22version%22%3A%220.1.8%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%225%22%2C%22clickKey%22%3A%2221005%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E5%BD%93%E6%97%A5%E6%88%90%E4%BA%A4%EF%BC%88%E6%99%AE%E9%80%9A%E4%BA%A4%E6%98%93%EF%BC%89%22%2C%22subType%22%3A%22%E8%B5%84%E8%AE%AF%22%2C%22version%22%3A%220.1.7%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%228%22%2C%22clickKey%22%3A%2221005%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E5%BD%93%E6%97%A5%E6%88%90%E4%BA%A4%EF%BC%88%E6%99%AE%E9%80%9A%E4%BA%A4%E6%98%93%EF%BC%89%22%2C%22subType%22%3A%22%E8%B5%84%E8%AE%AF%22%2C%22version%22%3A%220.1.6%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%228%22%2C%22clickKey%22%3A%2221015%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E7%8E%B0%E5%88%B8%E8%BF%98%E5%88%B8%EF%BC%88%E4%B8%A4%E8%9E%8D%EF%BC%89%22%2C%22subType%22%3A%22%E4%B8%9A%E5%8A%A1%E5%8A%9E%E7%90%86%22%2C%22version%22%3A%221.0.7%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%229%22%2C%22clickKey%22%3A%2221005%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E5%BD%93%E6%97%A5%E6%88%90%E4%BA%A4%EF%BC%88%E6%99%AE%E9%80%9A%E4%BA%A4%E6%98%93%EF%BC%89%22%2C%22subType%22%3A%22%E8%B5%84%E8%AE%AF%22%2C%22version%22%3A%220.1.5%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%229%22%2C%22clickKey%22%3A%2221015%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E7%8E%B0%E5%88%B8%E8%BF%98%E5%88%B8%EF%BC%88%E4%B8%A4%E8%9E%8D%EF%BC%89%22%2C%22subType%22%3A%22%E4%B8%9A%E5%8A%A1%E5%8A%9E%E7%90%86%22%2C%22version%22%3A%221.0.5%22%7D%2C%7B%22cacheTime%22%3A%221490781416123%22%2C%22channel%22%3A%229%22%2C%22clickKey%22%3A%2221005%22%2C%22clickType%22%3A%22CAction%22%2C%22count%22%3A%221%22%2C%22name%22%3A%22%E5%BD%93%E6%97%A5%E6%88%90%E4%BA%A4%EF%BC%88%E6%99%AE%E9%80%9A%E4%BA%A4%E6%98%93%EF%BC%89%22%2C%22subType%22%3A%22%E8%B5%84%E8%AE%AF%22%2C%22version%22%3A%220.1.3%22%7D%5D", "UTF-8"));
    }

    @RequestMapping(value = "/clickDetail", method = RequestMethod.GET)
    public void userclickDetail(HttpServletRequest request, HttpServletResponse response) {
        CommonResponseFomatter.format(response);
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        logger.error("message Received;" + requestQueryString);
        JSONObject requestJson = JSONObject.fromObject(requestQueryString.split("=")[1]);
        logger.error("message after decode;" + requestJson.toString());
        if (requestJson == null) {
            logger.error("Input Error: Json Format error or empty input String");
            CommonResponseWriter.write("Input Error: Json Format error or empty input String", response);
            return;
        }

        String startTime = JSONUtil.safeGetString(requestJson, CommonKeys.STARTTIME);
        String endTime = JSONUtil.safeGetString(requestJson, CommonKeys.ENDTIME);

        if (checkParams(startTime) == false || checkParams(endTime) == false) {
            CommonResponseWriter.write("Input Error: StartTime or endTime is missing!", response);
            return;
        }
        userClickDetailService.getClickDetailInfo(requestJson, response);

    }

    private boolean checkParams(String value) {
        if (value == null || value == "") {
            return false;
        }
        return true;
    }


}
