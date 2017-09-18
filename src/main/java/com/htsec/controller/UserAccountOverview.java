package com.htsec.controller;

import com.htsec.commons.utils.CodeHelper;
import com.htsec.security.AESUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by bernard on 2017/9/18.
 */
@Controller
public class UserAccountOverview {
    private static final Logger logger = Logger.getLogger(UserAccountOverview.class);
    private static AESUtil aes = new AESUtil();
    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public void userclickDetail(HttpServletRequest request, HttpServletResponse response){
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        String requestQ= null;
        try {
             requestQ = aes.decrypt(requestQueryString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject requestJSON = JSONObject.fromObject(requestQ);


    }

}
