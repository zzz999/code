package com.htsec.Student.process;

import com.htsec.Student.beans.BankLoanForm;
import com.htsec.Student.beans.StudentMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zzz on 2017/10/11.
 */
public class MessageManager {
    private static List<StudentMessage> list=new ArrayList<>();

    public static List<StudentMessage> getList() {
        return list;
    }

    public static void setList(List<StudentMessage> list) {
        MessageManager.list = list;
    }
    public static List<StudentMessage> findByCode(String code){
        if(code==null)return null;
        List<StudentMessage> result=new ArrayList<>();
        for(StudentMessage sm:MessageManager.list){
            if(sm.getCode()!=null&&sm.getCode().equals(code)
                    ||sm.getSendCode()==null||sm.getSendCode().equals("")
                    ||sm.getSendCode().equals(code)
                    ){
                result.add(sm);
            }
        }
        return result;
    }
    public static List<StudentMessage> findByTeacher(){
        return findByCode("-1");
    }
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
