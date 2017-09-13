package com.htsec.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.htsec.hbase.utils.HBaseHelper;
import com.htsec.service.UserClickStatService;
import com.htsec.service.dto.ClickStat;
import com.htsec.service.dto.KV;
import com.htsec.service.dto.ClickStatOverview;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

/**
 * Description : 功能统计第一页结果集MVC业务类.
 * Date : 2017/3/30 10:01
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
@Service
public class UserClickStatServiceImpl implements UserClickStatService {
    private static String tableName = "UserClickStat";
    private static String family = "stat";
    private static List<String> rowKeySuffixs = Lists.newArrayList("ClickTrend", "TypeTopN", "FuncTopN");
    public static final String CUR = "cur";
    public static final String PRE = "pre";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    static Gson gson = new Gson();
    static Logger logger = Logger.getLogger(UserClickStatServiceImpl.class);

    /**
     * 计算统计所需要读取hbase的健值并获取结果.
     *
     * @param unit   时间维度 例如 day month.
     * @param period 时间区间 例如 1 7 12.
     * @return 当期结果/环比结果
     */
    @Override
    public String findUserClickStatByUnitAndPeriod(String unit, int period) throws Exception {
        List<ResultScanner> curResults = Lists.newArrayList(), preResults = Lists.newArrayList();

        String nowStr = DateTime.now().withTimeAtStartOfDay().toString(DATE_FORMAT);
        String preStr = DateTime.now().minusDays(1).withTimeAtStartOfDay().toString(DATE_FORMAT);
        long uvToday = getOrElse(HBaseHelper.getResult(tableName, nowStr + "_UV").getValue(toBytes(family), toBytes("count")), 0L);
        long uvPreday = getOrElse(HBaseHelper.getResult(tableName, preStr + "_UV").getValue(toBytes(family), toBytes("count")), 0L);
        long pvToday = getOrElse(HBaseHelper.getResult(tableName, nowStr + "_PV").getValue(toBytes(family), toBytes("count")), 0L);
        long pvPreday = getOrElse(HBaseHelper.getResult(tableName, preStr + "_PV").getValue(toBytes(family), toBytes("count")), 0L);
        long ipToday = getOrElse(HBaseHelper.getResult(tableName, nowStr + "_IP").getValue(toBytes(family), toBytes("count")), 0L);
        long ipPreday = getOrElse(HBaseHelper.getResult(tableName, preStr + "_IP").getValue(toBytes(family), toBytes("count")), 0L);
        ClickStatOverview pvUvStat = new ClickStatOverview(pvToday, pvPreday, uvToday, uvPreday, ipToday, ipPreday);//PV/UV/IP统计对象.

        Object[] timeObjs = calcDateIntervalByUnitPeriod(unit, period);
        DateTime preStartDate = (DateTime) timeObjs[0];
        int interval = (int) timeObjs[1];

        FilterList curFilterList = new FilterList(FilterList.Operator.MUST_PASS_ONE), preFilterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        for (int i = 0; i < interval; i++) {
            for (String suffix : rowKeySuffixs) {
                int minus = i;
                if (StringUtils.equals(unit, "yesterday")) { //如果是昨天的数据 则基线往前移1天.
                    minus += 1;
                }
                String curDay = DateTime.now().minusDays(minus).toString(DATE_FORMAT);
                String preDay = preStartDate.minusDays(minus).toString(DATE_FORMAT);

                //如果选项卡选择今天或者昨天 对于点击量趋势栏目而言 需要获取分时 而不是日数据. 环比也是分时数据
                if (StringUtils.equals(suffix, "ClickTrend") && (StringUtils.equals(unit, "today") || StringUtils.equals(unit, "yesterday"))) {
                    for (int j = 0; j < 24; j++) {
                        curFilterList.addFilter(new PrefixFilter(toBytes(curDay + "-" + StringUtils.leftPad(String.valueOf(j), 2, "0") + "_" + suffix)));
                        preFilterList.addFilter(new PrefixFilter(toBytes(preDay + "-" + StringUtils.leftPad(String.valueOf(j), 2, "0") + "_" + suffix)));
                    }
                } else {
                    curFilterList.addFilter(new PrefixFilter(toBytes(curDay + "_" + suffix)));
                    preFilterList.addFilter(new PrefixFilter(toBytes(preDay + "_" + suffix)));
                }
            }
        }
        curResults.add(HBaseHelper.scanByFilter(tableName, curFilterList));
        preResults.add(HBaseHelper.scanByFilter(tableName, preFilterList));

        //合并当期以及环比数据集.
        Map<String, List<ClickStat>> cur = hbaseResultConverter(curResults, unit, CUR);
        Map<String, List<ClickStat>> pre = hbaseResultConverter(preResults, unit, PRE);
        List<ClickStat> clickStats;
        for (Map.Entry<String, List<ClickStat>> entry : cur.entrySet()) {
            clickStats = Lists.newArrayList();
            clickStats.addAll(entry.getValue());
            clickStats.addAll(pre.get(entry.getKey()));
            cur.put(entry.getKey(), clickStats);
        }

        return "[" + gson.toJson(pvUvStat) + "," + gson.toJson(cur) + "]";
    }

    /**
     * 转换Hbase表查询结果为待加工源数据集.
     *
     * @param results Hbase查询结果
     * @return
     */
    static Map<String, List<ClickStat>> hbaseResultConverter(List<ResultScanner> results, String unit, String period) throws IOException {
        Map<String, List<ClickStat>> convertRes = Maps.newHashMap();
        List<KV> clickTrend = Lists.newArrayList(), typeTopN = Lists.newArrayList(), funcTopN = Lists.newArrayList();

        for (ResultScanner scanner : results) {
            Iterator<Result> it = scanner.iterator();
            while (it.hasNext()) {
                Result result = it.next();
                if (!result.isEmpty()) {
                    String count = Bytes.toString(result.getValue(toBytes(family), toBytes("count")));
                    String[] rowKeyArray = Bytes.toString(result.getRow()).split("_");
                    switch (rowKeyArray.length) {
                        case 2:
                            clickTrend.add(new KV("clickTrend", unit, rowKeyArray[0], count));
                            break;
                        case 3:
                            typeTopN.add(new KV("typeTopN", unit, rowKeyArray[2], count));
                            break;
                        case 4:
                            funcTopN.add(new KV("funcTopN", unit, rowKeyArray[3], count));
                            break;
                    }
                }
            }
        }

        List<ClickStat> clickStats = groupByValueAndSort(clickTrend.stream().collect(groupingBy(KV::getKey, summingLong(KV::getVaule))), period, "byKey", "asc", 0, true);
        logger.info("clickTrend Size : " + clickStats.size());

        // 使用Java8 stream特性合并结果集.所以编译等级一定要>1.8 否则编译期报错
        convertRes.put("clickTrend", clickStats);
        convertRes.put("typeTopN", groupByValueAndSort(typeTopN.stream().collect(groupingBy(KV::getKey, summingLong(KV::getVaule))), period, "byValue", "desc", 5, false));
        convertRes.put("funcTopNAsc", groupByValueAndSort(funcTopN.stream().collect(groupingBy(KV::getKey, summingLong(KV::getVaule))), period, "byValue", "asc", 10, true));
        convertRes.put("funcTopNDesc", groupByValueAndSort(funcTopN.stream().collect(groupingBy(KV::getKey, summingLong(KV::getVaule))), period, "byValue", "desc", 10, true));

        return convertRes;
    }

    /**
     * 对HashMap进行排序(通过Key Or Value)然后返回前topN.
     *
     * @param original   原始Map集合
     * @param sortBy     通过Key还是Value排序
     * @param sortOrder  是否升序排列 'asc' - 升序, 'desc' - 降序
     * @param topN       返回前多少行? 为0 代表返回所有
     * @param xAixsByKey key作为x坐标轴
     */
    static List<ClickStat> groupByValueAndSort(Map<String, Long> original, String period, String sortBy, String sortOrder, int topN, boolean xAixsByKey) {
        if (original.isEmpty()) return Collections.emptyList();

        List<Map.Entry<String, Long>> list = new ArrayList<>(original.entrySet());

        Collections.sort(list, (o1, o2) -> {
            if (sortBy.equals("byKey")) {
                if (sortOrder.equals("asc"))
                    return o1.getKey().compareTo(o2.getKey());
                else
                    return o2.getKey().compareTo(o1.getKey());
            } else {
                if (sortOrder.equals("asc"))
                    return o1.getValue().compareTo(o2.getValue());
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });

        int size = list.size();
        List<Map.Entry<String, Long>> subList = list.subList(0, (topN == 0 || topN > size) ? size : topN);
        List<ClickStat> clickStats = Lists.newArrayList();

        Object xAixs, yAixs;

        for (Map.Entry<String, Long> entry : subList) {
            if (xAixsByKey) {
                xAixs = entry.getKey();
                yAixs = entry.getValue();
            } else {
                xAixs = entry.getValue();
                yAixs = entry.getKey();
            }
            clickStats.add(new ClickStat(period, xAixs, yAixs));
        }

        return clickStats;
    }

    /**
     * 获取查询条件的时间周期.
     *
     * @param unit   时间维度 "DAY"-天 "MONTH"-"月"
     * @param period 统计时间长度
     */
    private Object[] calcDateIntervalByUnitPeriod(String unit, int period) {
        int diff = calcDateDiff(unit, period), interval = 0;
        DateTime now = DateTime.now();
        DateTime preStartDate = now;

        switch (unit) {
            case "today":
            case "yesterday":
            case "day":
                preStartDate = now.minusDays(diff);
                interval = period;
                break;
            case "month":
                preStartDate = now.minusMonths(diff);
                interval = period * 30;
                break;
            case "year":
                preStartDate = now.minusYears(diff);
                interval = period * 365;
                break;
        }

        return new Object[]{preStartDate, interval};
    }

    /**
     * 获取环比时间间隔.
     * <p>
     * 判断逻辑：
     * 如果传入天数大于2 并且小于 那么认为是取7天数据 并且环比上周
     */
    private int calcDateDiff(String unit, int period) {
        int diff = 0;
        if (StringUtils.equals(unit, "today") || StringUtils.equals(unit, "yesterday")) {
            diff = 1;
        } else if (unit.equals("day")) {
            if (period > 2 && period <= 7) {
                diff = 7;
            } else if (period > 7 && period <= 30) {
                diff = 30;
            } else {
                diff = 7;
            }
        } else if (unit.equals("month")) {
            diff = 12;
        } else if (unit.equals("year")) {
            diff = 1;
        }

        return diff;
    }

    /**
     * 过滤空值.
     *
     * @return
     */
    static Long getOrElse(byte[] obj, Long defaultVal) {
        if (obj == null) return defaultVal;
        else return Long.parseLong(Bytes.toString(obj));
    }
}