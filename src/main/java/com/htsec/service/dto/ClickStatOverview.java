package com.htsec.service.dto;

import org.apache.log4j.Logger;

/**
 * Description : TODO
 * Date : 2017/3/31 10:48
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public class ClickStatOverview {
    static Logger logger = Logger.getLogger(ClickStatOverview.class);

    private long pvToday = 0;
    private long pvPreday = 0;
    private long uvToday = 0;
    private long uvPreday = 0;
    private long ipToday = 0;
    private long ipPreday = 0;
    private long onlineToday = 0;
    private long onlinePreday = 0;
    private boolean pvAscend;
    private boolean uvAscend;
    private boolean ipAscend;
    private boolean onlineAscend;

    public ClickStatOverview(long pvToday, long pvPreday, long uvToday, long uvPreday, long ipToday, long ipPreday, long onlineToday, long onlinePreday) {
        this.pvToday = pvToday;
        this.pvPreday = pvPreday;
        this.uvToday = uvToday;
        this.uvPreday = uvPreday;
        this.ipToday = ipToday;
        this.ipPreday = ipPreday;
        this.onlineToday = onlineToday;
        this.onlinePreday = onlinePreday;
        this.pvAscend = pvToday > pvPreday;
        this.uvAscend = uvToday > uvPreday;
        this.ipAscend = ipToday > ipPreday;
        this.onlineAscend = onlineToday > onlinePreday;
    }

    public ClickStatOverview(long pvToday, long pvPreday, long uvToday, long uvPreday, long ipToday, long ipPreday) {
        this.pvToday = pvToday;
        this.pvPreday = pvPreday;
        this.uvToday = uvToday;
        this.uvPreday = uvPreday;
        this.ipToday = ipToday;
        this.ipPreday = ipPreday;
        this.pvAscend = pvToday > pvPreday;
        this.uvAscend = uvToday > uvPreday;
        this.ipAscend = ipToday > ipPreday;
    }

    public ClickStatOverview() {
    }

    public void setPvToday(long pvToday) {
        this.pvToday = pvToday;
    }

    public void setPvPreday(long pvPreday) {
        this.pvPreday = pvPreday;
    }

    public void setUvToday(long uvToday) {
        this.uvToday = uvToday;
    }

    public void setUvPreday(long uvPreday) {
        this.uvPreday = uvPreday;
    }

    public void setIpToday(long ipToday) {
        this.ipToday = ipToday;
    }

    public void setIpPreday(long ipPreday) {
        this.ipPreday = ipPreday;
    }

    public void setOnlineToday(long onlineToday) {
        this.onlineToday = onlineToday;
    }

    public void setOnlinePreday(long onlinePreday) {
        this.onlinePreday = onlinePreday;
    }
}
