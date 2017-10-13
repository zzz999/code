package com.htsec.hbase.utils;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.BankLoanForm;
import com.htsec.Student.process.BankLoanManager;
import com.htsec.Student.process.StudentProcessManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zzz on 2017/10/13.
 */
public class Test {
    @org.junit.Test
    public void test(){
        BigDecimal cash=new BigDecimal("0").add(new BigDecimal("2.32")).setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.print(cash.toString());
    }
    @org.junit.Test
    public void test2(){
        Integer  a=3;Integer  b=2;

        BigDecimal sum=new BigDecimal(2);
        BigDecimal money=new BigDecimal(2);
        System.out.println(sum.compareTo(money));
        System.out.println(a.compareTo(b));
    }

}
