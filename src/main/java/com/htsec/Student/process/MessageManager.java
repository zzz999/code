package com.htsec.Student.process;

import com.htsec.Student.beans.BankLoanForm;
import com.htsec.Student.beans.StudentMessage;

import java.util.ArrayList;
import java.util.List;

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
}
