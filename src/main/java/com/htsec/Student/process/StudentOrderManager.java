package com.htsec.Student.process;

import com.htsec.Student.beans.CompanyDepositOrder;
import com.htsec.Student.beans.CompanyLoanOrder;
import com.htsec.Student.beans.PersonalDepositOrder;
import com.htsec.Student.beans.PersonalLoanOrder;
import com.htsec.Student.init.bean.PersonalDeposit;
import org.jcodings.util.Hash;

import java.util.HashMap;
import java.util.List;

public class StudentOrderManager {
    private static HashMap<String,HashMap<String,CompanyLoanOrder>> companLoanOrderMap = new HashMap<>();
    private static HashMap<String,HashMap<String,CompanyDepositOrder>> companyDepositOrderMap =new HashMap<>();
    private static HashMap<String,HashMap<String,PersonalDepositOrder>> personalDepositOrderMap = new HashMap<>();
    private static HashMap<String,HashMap<String,PersonalLoanOrder>> personalLoanOrderMap = new HashMap<>();


    public static HashMap<String, HashMap<String, CompanyLoanOrder>> getCompanLoanOrderMap() {
        return companLoanOrderMap;
    }

    public static void setCompanLoanOrderMap(HashMap<String, HashMap<String, CompanyLoanOrder>> companLoanOrderMap) {
        StudentOrderManager.companLoanOrderMap = companLoanOrderMap;
    }

    public static HashMap<String, HashMap<String, CompanyDepositOrder>> getCompanyDepositOrderMap() {
        return companyDepositOrderMap;
    }

    public static void setCompanyDepositOrderMap(HashMap<String, HashMap<String, CompanyDepositOrder>> companyDepositOrderMap) {
        StudentOrderManager.companyDepositOrderMap = companyDepositOrderMap;
    }

    public static HashMap<String, HashMap<String, PersonalDepositOrder>> getPersonalDepositOrderMap() {
        return personalDepositOrderMap;
    }

    public static void setPersonalDepositOrderMap(HashMap<String, HashMap<String, PersonalDepositOrder>> personalDepositOrderMap) {
        StudentOrderManager.personalDepositOrderMap = personalDepositOrderMap;
    }

    public static HashMap<String, HashMap<String, PersonalLoanOrder>> getPersonalLoanOrderMap() {
        return personalLoanOrderMap;
    }

    public static void setPersonalLoanOrderMap(HashMap<String, HashMap<String, PersonalLoanOrder>> personalLoanOrderMap) {
        StudentOrderManager.personalLoanOrderMap = personalLoanOrderMap;
    }


}
