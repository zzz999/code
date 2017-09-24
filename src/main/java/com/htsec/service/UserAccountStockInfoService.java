package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/9/22.
 */
public interface UserAccountStockInfoService {
    public void getAccountStockInfo(JSONObject input, HttpServletResponse response);
}
