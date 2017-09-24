package com.htsec.service.cache;

import com.htsec.service.dto.MasterOverview;

import java.util.List;


/**
 * Created by bernard on 2017/9/20.
 */

public class MasterOverViewCache {
    private static long catcheTime;
    private static int starttime;
    private static int endtime;
    private static List<MasterOverview> cacheData;

    public synchronized static long getCatcheTime() {
        return catcheTime;
    }

    public synchronized static void setCatcheTime(long catcheTime) {
        MasterOverViewCache.catcheTime = catcheTime;
    }

    public synchronized static List<MasterOverview> getCacheData() {
        return cacheData;
    }

    public synchronized static void setCacheData(List<MasterOverview> cacheData) {
        MasterOverViewCache.cacheData = cacheData;
    }

    public static int getStarttime() {
        return starttime;
    }

    public static void setStarttime(int starttime) {
        MasterOverViewCache.starttime = starttime;
    }

    public static int getEndtime() {
        return endtime;
    }

    public static void setEndtime(int endtime) {
        MasterOverViewCache.endtime = endtime;
    }
}
