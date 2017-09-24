package com.htsec.controller;

import com.htsec.commons.utils.CodeHelper;
import com.htsec.commons.utils.CommonKeys;
import com.htsec.security.AESUtil;
import com.htsec.service.UserAccountCompositeScore;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by bernard on 2017/9/24.
 */

@Controller
public class UserCompositeScore {
    @Autowired
    private UserAccountCompositeScore userAccountCompositeScore;
    private static AESUtil aes = new AESUtil();
    @RequestMapping(value = "/compositeSocre", method = RequestMethod.GET)
    public void userclickDetail(HttpServletRequest request, HttpServletResponse response){
        String requestQueryString = CodeHelper.decode(request.getQueryString());
        String requestQ= null;
        try {
            requestQ = aes.decrypt(requestQueryString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject requestJSON = JSONObject.fromObject(requestQ);
        requestJSON.getString(CommonKeys.ACCOUNT);
        userAccountCompositeScore.getCompositeScoreByAccount(requestJSON,response);

    }
}
