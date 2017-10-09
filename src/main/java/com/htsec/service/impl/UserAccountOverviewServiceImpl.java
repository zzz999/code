package com.htsec.service.impl;

import com.htsec.commons.utils.CommonKeys;
import com.htsec.commons.utils.TimeUtil;
import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.service.UserAccountOverviewService;
import com.htsec.service.dto.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

        //moneychange
        JSONObject moneyChange = new JSONObject();
        moneyChange.put("moneyBegin",1000.10);
        moneyChange.put("moneyEnd",5000.10);
        moneyChange.put("moneyInput",1000.00);
        moneyChange.put("moneyOutput",2000.01);
        weekOverview.put("moneyChange",moneyChange);


        try {
            response.getWriter().write(weekOverview.toString());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void getOverviewByMonth(String account, HttpServletResponse response){
        List<SwfundAssetmonth> swfundAssetmonthList=userDataBaseService.getSwfundAssetMonthInfo(account,Integer.parseInt(TimeUtil.getMonth()),Integer.parseInt(TimeUtil.getMonth()));
        if(swfundAssetmonthList==null||swfundAssetmonthList.isEmpty()){
            try {
                response.getWriter().write(new JSONObject().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        SwfundAssetmonth monthInfo =swfundAssetmonthList.get(0);
        String profitRateInfo=monthInfo.getProfitRateByday().trim();//当日收益率
        String profitHoderInfo= monthInfo.getHolderRateByday().trim();//当日仓位
        String profitInfo =monthInfo.getProfitByday().trim();//当日收益

        HashMap<String,OverViewInfo> overViewInfoHashMap = new HashMap<>();
        String[] prfitRateInfoList = profitRateInfo.split(",");
        for(String profitRate:prfitRateInfoList){
            String[] datas =profitRate.split(":");
            if(overViewInfoHashMap.containsKey(datas[0])){
                overViewInfoHashMap.get(datas[0]).setTodayRate(datas[1]);
            }else {
                overViewInfoHashMap.put(datas[0],new OverViewInfo());
                overViewInfoHashMap.get(datas[0]).setTodayRate(datas[1]);
                overViewInfoHashMap.get(datas[0]).setTime(datas[0]);
            }
        }
        String[] prfitHoldInfoList = profitHoderInfo.split(",");
        for(String profitHold:prfitHoldInfoList){
            String[] datas =profitHold.split(":");
            if(overViewInfoHashMap.containsKey(datas[0])){
                overViewInfoHashMap.get(datas[0]).setTodayHold(datas[1]);
            }else {
                overViewInfoHashMap.put(datas[0],new OverViewInfo());
                overViewInfoHashMap.get(datas[0]).setTodayHold(datas[1]);
                overViewInfoHashMap.get(datas[0]).setTime(datas[0]);
            }
        }
        String[] profitInfoList = profitInfo.split(",");
        for(String profit:profitInfoList){
            String[] datas =profit.split(":");
            if(overViewInfoHashMap.containsKey(datas[0])){
                overViewInfoHashMap.get(datas[0]).setTodayProfit(datas[1]);
            }else {
                overViewInfoHashMap.put(datas[0],new OverViewInfo());
                overViewInfoHashMap.get(datas[0]).setTodayProfit(datas[1]);
                overViewInfoHashMap.get(datas[0]).setTime(datas[0]);
            }
        }
        ArrayList<OverViewInfo> overViewInfos = new ArrayList<>();
        for(String key : overViewInfoHashMap.keySet()){
            overViewInfos.add(overViewInfoHashMap.get(key));
        }
        Collections.sort(overViewInfos);
        float totalrate =1;
        float totalProfit =0;
        for(OverViewInfo info: overViewInfos){
            totalrate =totalrate*(1+Float.parseFloat(info.getTodayRate()));
            totalProfit =totalProfit+Float.parseFloat(info.getTodayProfit());
            info.setTotalProfit(totalProfit+"");
            info.setTotalRate((totalrate-1)+"");
        }

        AssertChange assertChange = new AssertChange();
        assertChange.setAssertIncome(monthInfo.getPeriodInBalance().toString());
        assertChange.setAssertOutcome(monthInfo.getPeriodOutBalance().toString());
        assertChange.setBeginAssert(monthInfo.getBeginTotalAsset().toString());
        assertChange.setEndAssert(monthInfo.getEndTotalAsset().toString());

        String factorprofit=monthInfo.getFactorProfit();
        String riskExp =monthInfo.getRiskExp();
        List<ReasonAnalysis> reasonAnalysisList = new ArrayList<>();
        String[] factors =factorprofit.trim().split(",");
        for(String factor:factors){
            String[] dat = factor.split(":");
            ReasonAnalysis reasonAnalysis = new ReasonAnalysis();
            reasonAnalysis.setKey(dat[0]);
            reasonAnalysis.setValue(dat[1]);
            reasonAnalysisList.add(reasonAnalysis);
        }

        List<ReasonAnalysis> riskAnalysisList = new ArrayList<>();
        String[] risks =riskExp.trim().split(",");
        for(String risk:risks){
            String[] dat = risk.split(":");
            ReasonAnalysis riskA = new ReasonAnalysis();
            riskA.setKey(dat[0]);
            riskA.setValue(dat[1]);
            riskAnalysisList.add(riskA);
        }

        OperationAnalysis operationAnalysis =new OperationAnalysis();
        operationAnalysis.setStockSelection(monthInfo.getStockSelection().toString());
        operationAnalysis.setProfitSecCount(monthInfo.getProfitSecCount()+"");
        operationAnalysis.setTimeSelection(monthInfo.getMarketTiming().toString());
        operationAnalysis.setTradeSuccessRate(monthInfo.getTradeSuccessRate().toString());
        operationAnalysis.setTradeTime(monthInfo.getTradingTimes().toString());

        List<ShootIndustry> shootIndustries = new ArrayList<>();
        String industryProfit = monthInfo.getIndustryProfit().trim();
        String [] industrys = industryProfit.split(",");
        for(String industry:industrys){
            String[] data = industry.split(":");
            ShootIndustry shootIndustry = new ShootIndustry();
            shootIndustry.setName(data[0]);
            shootIndustry.setRate(data[1]);
            shootIndustries.add(shootIndustry);
        }

        List<InvestStyle> investStyles = new ArrayList<>();
        String investStyleInfo = monthInfo.getInvestStyle();
        for(String style: investStyleInfo.trim().split(",")){
            String[] data = style.split(":");
            InvestStyle investStyle = new InvestStyle();
            investStyle.setNum(data[0]);
            investStyle.setStyle(data[1]);
            investStyles.add(investStyle);
        }


        RiskControl riskControl = new RiskControl();
        riskControl.setCalmarRatio(monthInfo.getCalmarRatio().toString());
        riskControl.setSharpeRatio(monthInfo.getSharpeRatio().toString());
        riskControl.setSortinoRatio(monthInfo.getSortinoRatio().toString());


        ComprehensivePerformance comprehensivePerformance = new ComprehensivePerformance();
        comprehensivePerformance.setAssetAllocation(monthInfo.getAssetAllocation().toString());
        comprehensivePerformance.setMarketTiming(monthInfo.getMarketTiming().toString());
        comprehensivePerformance.setOperate(monthInfo.getOperate().toString());
        comprehensivePerformance.setRiskControl(monthInfo.getRiskControl().toString());
        comprehensivePerformance.setStability(monthInfo.getStability().toString());
        comprehensivePerformance.setStockSelection(monthInfo.getStockSelection().toString());













        JSONObject result = new JSONObject();
        result.put("overviewInfos",overViewInfos);
        result.put("assertchanges",assertChange);
        result.put("reasonAnalysis",reasonAnalysisList);
        result.put("riskAnalysis",riskAnalysisList);
        result.put("operateAnalysis",operationAnalysis);
        result.put("shootIndustry",shootIndustries);
        result.put("investStyle",investStyles);
        result.put("riskControl",riskControl);
        result.put("comprehensivePerformance",comprehensivePerformance);

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getOverviewByYear(String account, HttpServletResponse response){

    }
}
