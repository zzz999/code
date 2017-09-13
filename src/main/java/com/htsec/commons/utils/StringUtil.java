package com.htsec.commons.utils;

/**
 * Created by bernard on 2017/3/28.
 */
public class StringUtil {
    /**
     * 首字母大写方法
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }
}
