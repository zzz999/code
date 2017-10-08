package com.htsec.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

public interface UserBreakevenAnalysisService {
    public void getBreakevenAnalysisInfo(JSONObject input, HttpServletResponse response);
}
