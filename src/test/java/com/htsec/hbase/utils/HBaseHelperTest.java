package com.htsec.hbase.utils;

import com.google.common.collect.Lists;
import com.htsec.service.impl.UserClickStatServiceImpl;
import org.apache.commons.lang.time.StopWatch;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Description : TODO
 * Date : 2017/3/31 14:27
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public class HBaseHelperTest {
    private static List<String> rowKeySuffixs = Lists.newArrayList("ClickTrend", "TypeTopN", "FuncTopNASC", "FuncTopNDESC");
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static DateTime now = DateTime.now();
    private static String tableName = "UserClickBasicCalc";
    private static String family = "stat";
    StopWatch stopWatch = new StopWatch();
    static Logger logger = Logger.getLogger(UserClickStatServiceImpl.class);

    /**
     * 测试通过Filter查询HBase表数据性能.
     */
    @Test
    @Ignore
    public void scanByFilterPerformance() throws Exception {
        stopWatch.reset();
        stopWatch.start();
        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        fl.addFilter(new KeyOnlyFilter());
        ResultScanner scanner = HBaseHelper.scanByFilter(tableName, fl);

        Iterator<Result> it = scanner.iterator();
        while (it.hasNext()) {
            Result result = it.next();
            if (!result.isEmpty()) {
                System.out.println(Bytes.toString(result.getRow()));
            }
        }
        stopWatch.split();
        logger.info("使用PrefixFilter获取HBase数据总共耗时:" + stopWatch.getSplitTime() / 1000 + "秒, 共循环" + 1000 * 4 + "次.");
        stopWatch.stop();
    }
}
