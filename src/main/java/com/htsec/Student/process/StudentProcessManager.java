package com.htsec.Student.process;

import com.htsec.Student.beans.BankInfo;

import java.util.HashMap;
import java.util.List;

public class StudentProcessManager {
    private static HashMap<String,BankInfo> bankInfoHashMap = new HashMap<>();

    public static HashMap<String, BankInfo> getBankInfoHashMap() {
        return bankInfoHashMap;
    }

    public static void setBankInfoHashMap(HashMap<String, BankInfo> bankInfoHashMap) {
        StudentProcessManager.bankInfoHashMap = bankInfoHashMap;
    }
}
