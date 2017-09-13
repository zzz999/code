package com.htsec.commons.utils;

import java.text.NumberFormat;

/**
 * Created by bernard on 2017/5/23.
 */
public class PercentUtil {
    public static String getIntPercent(String num1,String num2){
       int numInt1= Integer.parseInt(num1);
        int numInt2 = Integer.parseInt(num2);

        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位

        numberFormat.setMaximumFractionDigits(3);

        String result = numberFormat.format((float) numInt1 / (float) numInt2 * 100);

       return result;
    }
}
