package com.htsec.controller;

import com.htsec.commons.utils.CodeHelper;
import com.htsec.security.AESUtil;
import com.htsec.service.UserAccountStockInfoService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by bernard on 2017/9/22.
 */
@Controller
public class UserStockInfoController {
    private static final Logger logger = Logger.getLogger(UserStockInfoController.class);
    private static AESUtil aes = new AESUtil();
    @Autowired
    UserAccountStockInfoService userAccountStockInfoService;
    @RequestMapping(value = "/stockInfo", method = RequestMethod.GET)
    public void userRegister(HttpServletRequest request, HttpServletResponse response) {
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        String requestQ= null;
        try {
            requestQ = aes.decrypt(requestQueryString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject requestJSON = JSONObject.fromObject(requestQ);
        userAccountStockInfoService.getAccountStockInfo(requestJSON,response);
    }

}
