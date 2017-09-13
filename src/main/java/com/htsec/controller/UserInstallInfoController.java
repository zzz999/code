package com.htsec.controller;

import com.htsec.service.UserInstallInfoService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/5/4.
 */

@Controller
public class UserInstallInfoController {
    private static final Logger logger = Logger.getLogger(UserClickDetailController.class);
    @Autowired
    private UserInstallInfoService userInstallInfoService;

    @RequestMapping(value = "/installinfo", method = RequestMethod.GET)
    public void userclickDetail(HttpServletRequest request, HttpServletResponse response) {

        userInstallInfoService.getRegistInfo(new JSONObject(), response);

    }


}
