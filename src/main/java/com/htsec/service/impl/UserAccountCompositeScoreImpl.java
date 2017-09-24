package com.htsec.service.impl;

import com.htsec.commons.utils.CommonKeys;
import com.htsec.mysql.dto.swcompositescore;
import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.UserAccountCompositeScore;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bernard on 2017/9/24.
 */
@Service
public class UserAccountCompositeScoreImpl implements UserAccountCompositeScore {
    @Autowired
    UserDataBaseService userDataBaseService;

    @Override
    public void getCompositeScoreByAccount(JSONObject input, HttpServletResponse response) {
        swcompositescore info =userDataBaseService.getswbyaccount(Integer.parseInt(input.getString(CommonKeys.ACCOUNT)));
        if(info==null){
            try {
                response.getWriter().write(new JSONObject().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        JSONObject result = new JSONObject();
        result.put(CommonKeys.ASSETALLOCATION,info.getAssetllocation());
        result.put(CommonKeys.OPERATION,info.getOperate());
        result.put(CommonKeys.STOCKSELECTION,info.getStockSelection());
        result.put(CommonKeys.STABILITY,info.getStability());
        result.put(CommonKeys.MARKETIMING,info.getMarketTiming());
        result.put(CommonKeys.RISKCONTROL,info.getRiskControl());
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        System.out.println(Integer.parseInt("40119420"));
    }
}
