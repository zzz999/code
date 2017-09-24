package com.htsec.service.impl;

import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.UserOperationAnalysisService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/9/24.
 */
public class UserOperationServiceImpl implements UserOperationAnalysisService{
    @Autowired
    private UserDataBaseService userDataBaseService;
    @Override
    public void getOperationAnalysisByAccount(JSONObject input, HttpServletResponse response) {
       // userDataBaseService.

    }
}
