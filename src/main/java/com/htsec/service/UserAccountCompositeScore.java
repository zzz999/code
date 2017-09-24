package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/9/24.
 */
public interface UserAccountCompositeScore {
    public void getCompositeScoreByAccount(JSONObject input, HttpServletResponse response);

}
