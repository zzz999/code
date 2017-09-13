package com.htsec.mysql.dto;

/**
 * Created by bernard on 2017/7/10.
 */
public class HotSearch {
    private String clickKey;
    private String clickType;
    private long count;
    private String rowKey;

    public String getClickKey() {
        return clickKey;
    }

    public void setClickKey(String clickKey) {
        this.clickKey = clickKey;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }
}
