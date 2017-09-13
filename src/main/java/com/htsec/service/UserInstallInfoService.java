package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/5/4.
 */
public interface UserInstallInfoService {
    public void getRegistInfo(JSONObject input, HttpServletResponse response);
}
