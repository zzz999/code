package com.htsec.Student.process;

import com.htsec.Student.beans.BankLoanForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzz on 2017/10/10.
 */
public class BankLoanManager {
    //再贷款
    private static List<BankLoanForm> againLoanList=new ArrayList<>();
    //国债
    private static List<BankLoanForm> nationalLoanList=new ArrayList<>();
    //同业拆借
    private static List<BankLoanForm> interBankBorrowingList=new ArrayList<>();
    //金融债管理
    private static List<BankLoanForm> financialBondsList=new ArrayList<>();
    public static BankLoanForm findByIdAndRemove(List<BankLoanForm> list,String id){
        for(BankLoanForm blf:list){
            if(blf.getId().equals(id)){
                list.remove(blf);
                return blf;
            }
        }
        return null ;
    };
    public static BankLoanForm findById(List<BankLoanForm> list,String id){
        for(BankLoanForm blf:list){
            if(blf.getId().equals(id)){
                return blf;
            }
        }
        return null ;
    };
    public static List<BankLoanForm> getAgainLoanList() {
        return againLoanList;
    }

    public static void setAgainLoanList(List<BankLoanForm> againLoanList) {
        BankLoanManager.againLoanList = againLoanList;
    }

    public static List<BankLoanForm> getNationalLoanList() {
        return nationalLoanList;
    }

    public static void setNationalLoanList(List<BankLoanForm> nationalLoanList) {
        BankLoanManager.nationalLoanList = nationalLoanList;
    }

    public static List<BankLoanForm> getInterBankBorrowingList() {
        return interBankBorrowingList;
    }

    public static void setInterBankBorrowingList(List<BankLoanForm> interBankBorrowingList) {
        BankLoanManager.interBankBorrowingList = interBankBorrowingList;
    }

    public static List<BankLoanForm> getFinancialBondsList() {
        return financialBondsList;
    }

    public static void setFinancialBondsList(List<BankLoanForm> financialBondsList) {
        BankLoanManager.financialBondsList = financialBondsList;
    }
}
