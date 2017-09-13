package com.htsec.commons.utils;

import com.htsec.service.dto.ClickDetail;

import java.util.Comparator;

/**
 * Created by bernard on 2017/3/28.
 */
public class CountCompare implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        ClickDetail s1 = (ClickDetail) o1;
        ClickDetail s2 = (ClickDetail) o2;
        return Integer.parseInt(s2.getCount())-Integer.parseInt(s1.getCount());
    }
}
