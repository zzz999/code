package com.htsec.schedule.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by bernard on 2017/7/10.
 */
public class SingleThreadScheduleExecutor {
    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static ScheduledExecutorService getService() {
        return service;
    }

    public static void setService(ScheduledExecutorService service) {
        SingleThreadScheduleExecutor.service = service;
    }
}
