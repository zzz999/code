package com.htsec.service.impl;

import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.UserAccountStockInfoService;
import com.htsec.service.UserBreakevenAnalysisService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserBreakevenAnalysisServiceImpl implements UserBreakevenAnalysisService {
    private static final Logger logger = Logger.getLogger(UserBreakevenAnalysisServiceImpl.class);

    @Autowired
    UserDataBaseService userDataBaseService;
    @Override
    public void getBreakevenAnalysisInfo(JSONObject input, HttpServletResponse response) {


    }
}
