package com.htsec.service.impl;

import com.htsec.commons.utils.CommonKeys;
import com.htsec.commons.utils.TimeUtil;
import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.UserAccountOverviewService;
import com.htsec.service.dto.MasterOverview;
import com.htsec.service.dto.UserOverview;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * Created by bernard on 2017/9/19.
 */

@Service
public class UserAccountOverviewServiceImpl implements UserAccountOverviewService {
    private static final Logger logger = Logger.getLogger(UserAccountOverviewServiceImpl.class);
    @Autowired
    private UserDataBaseService userDataBaseService;

    @Override
    public void getOverview(JSONObject input, HttpServletResponse response) {
        String account=input.getString(CommonKeys.ACCOUNT);
        String range =input.getString(CommonKeys.RANGE);
        switch (range){
            case "week":
                getOverviewByWeek( account,response);
                break;
            case "month":
                getOverviewByMonth(account,response);
                break;
            case "year":
                getOverviewByYear(account,response);
                break;
            default:
                logger.error("fail to match range:"+range);
        }

    }


    private void getOverviewByWeek(String account, HttpServletResponse response) {
        JSONObject weekOverview = new JSONObject();
        List<String> week = null;
        try {
            week = TimeUtil.getThisWeekBeginEnd();
        } catch (ParseException e) {
            logger.error(e);
        }
        List<UserOverview> list = userDataBaseService.getUserOverviewByAccout(Integer.parseInt(account),Integer.parseInt(week.get(0)),Integer.parseInt(week.get(1)));
        BigDecimal totoalPro =new BigDecimal(1);
        BigDecimal totalMoney = new BigDecimal(0);
        for(UserOverview overview:list){
            BigDecimal todayPro = overview.getPorfitToday();
            totoalPro =(todayPro.add(new BigDecimal(1))).multiply(totoalPro);
            BigDecimal todayMoney =overview.getProfitMoney();
            totalMoney = totalMoney.add(todayMoney);
            overview.setTotalProfitMoney(totalMoney);
            overview.setTotalProfit(totoalPro.setScale(5,BigDecimal.ROUND_HALF_UP));
        }
        List<MasterOverview> masterList = userDataBaseService.getMasterOverview(Integer.parseInt(week.get(0)),Integer.parseInt(week.get(1)));
        BigDecimal masterTotoalPro =new BigDecimal(1);
        for(MasterOverview masterOverview:masterList){
            BigDecimal todayPro = masterOverview.getPorfitToday();
            masterTotoalPro =(todayPro.add(new BigDecimal(1)).multiply(masterTotoalPro));
            masterOverview.setTotalProfit(masterTotoalPro.setScale(5,BigDecimal.ROUND_HALF_UP));
        }
        weekOverview.put("userOverview",list);
        weekOverview.put("masterOverview",masterList);

        try {
            response.getWriter().write(weekOverview.toString());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void getOverviewByMonth(String account, HttpServletResponse response){

    }

    private void getOverviewByYear(String account, HttpServletResponse response){

    }
}
