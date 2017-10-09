package com.htsec.service.impl;

import com.htsec.commons.utils.CommonKeys;
import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.UserAccountStockInfoService;
import com.htsec.service.dto.StockInfo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by bernard on 2017/9/22.
 */
@Service
public class UserAccountStockInfoServiceImpl implements UserAccountStockInfoService{
    private static final Logger logger = Logger.getLogger(UserAccountStockInfoService.class);

    @Autowired
    UserDataBaseService userDataBaseService;

    @Override
    public void getAccountStockInfo(JSONObject input, HttpServletResponse response) {
        logger.info("start to handle user account stockinfo service");
       List<StockInfo> stockInfoByAccountDesc = userDataBaseService.getStockInfoByAccount(input.getString(CommonKeys.ACCOUNT));
       List<StockInfo> stockInfoByAccountAsc =userDataBaseService.getStockInfoByAccountASC(input.getString(CommonKeys.ACCOUNT));
       JSONObject result = new JSONObject();
       result.put("stockInfoByAccountDesc",stockInfoByAccountDesc);
       result.put("stockInfoByAccountAsc",stockInfoByAccountAsc);
        logger.info(result.toString());
        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
