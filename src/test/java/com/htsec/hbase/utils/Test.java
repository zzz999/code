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
    @org.junit.Test
    public void test3(){
        List<BankLoanForm> list = new ArrayList<>();
        BankLoanForm blf4= new BankLoanForm();
        blf4.setMoney("3.7");
        blf4.setScale("100");
        list.add(blf4);
        BankLoanForm blf1= new BankLoanForm();
        blf1.setMoney("3.8");
        blf1.setScale("300");
        list.add(blf1);
        BankLoanForm blf7= new BankLoanForm();
        blf7.setMoney("3.8");
        blf7.setScale("100");
        list.add(blf7);
        BankLoanForm blf6= new BankLoanForm();
        blf6.setMoney("3.9");
        blf6.setScale("300");
        list.add(blf6);
        BankLoanForm blf5= new BankLoanForm();
        blf5.setMoney("3.79");
        blf5.setScale("100");
        list.add(blf5);
        BankLoanForm blf2= new BankLoanForm();
        blf2.setMoney("3.9");
        blf2.setScale("100");
        list.add(blf2);
        BankLoanForm blf3= new BankLoanForm();
        blf3.setMoney("3.9");
        blf3.setScale("200");
        list.add(blf3);

        Collections.sort(list,new Comparator<BankLoanForm>(){
            public int compare(BankLoanForm arg0, BankLoanForm arg1) {
                int result=new BigDecimal(arg1.getMoney()).compareTo(new BigDecimal(arg0.getMoney()));
                if(result==0){
                    return new BigDecimal(arg1.getScale()).compareTo(new BigDecimal(arg0.getScale()));
                }
                return result;
            }
        });
        System.out.println(list);
    }
}
