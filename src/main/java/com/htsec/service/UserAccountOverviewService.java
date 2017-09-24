package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/9/19.
 */
public interface UserAccountOverviewService {
    public void  getOverview(JSONObject input, HttpServletResponse response);
}
