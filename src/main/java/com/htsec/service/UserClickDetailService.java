package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/3/27.
 */
public interface UserClickDetailService {
    public void  getClickDetailInfo(JSONObject input, HttpServletResponse response);

}
