package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/9/24.
 */
public interface UserOperationAnalysisService {
    public void getOperationAnalysisByAccount(JSONObject input,HttpServletResponse response);
}
