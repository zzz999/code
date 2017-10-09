package com.htsec.Student.process;

import com.htsec.Student.init.bean.*;

public class TeacherInitManager {
    private static BaseRate baseRate;
    private static QYLongOrder qyLongOrder;
    private static QYShortOrder qyShortOrder;
    private static TXOrder txOrder;
    private static PersonalDeposit personalDeposit;
    private static HouseLoan houseLoan;
    private static CarLoan carLoan;
    private static OtherLoan otherLoan;
    private static ContryDeposit contryDeposit;

    public static BaseRate getBaseRate() {
        return baseRate;
    }

    public static void setBaseRate(BaseRate Rate) {
        baseRate = Rate;
    }

    public static QYLongOrder getQyLongOrder() {
        return qyLongOrder;
    }

    public static void setQyLongOrder(QYLongOrder qyLongOrder) {
        TeacherInitManager.qyLongOrder = qyLongOrder;
    }

    public static QYShortOrder getQyShortOrder() {
        return qyShortOrder;
    }

    public static void setQyShortOrder(QYShortOrder qyShortOrder) {
        TeacherInitManager.qyShortOrder = qyShortOrder;
    }

    public static TXOrder getTxOrder() {
        return txOrder;
    }

    public static void setTxOrder(TXOrder txOrder) {
        TeacherInitManager.txOrder = txOrder;
    }

    public static PersonalDeposit getPersonalDeposit() {
        return personalDeposit;
    }

    public static void setPersonalDeposit(PersonalDeposit personalDeposit) {
        TeacherInitManager.personalDeposit = personalDeposit;
    }

    public static HouseLoan getHouseLoan() {
        return houseLoan;
    }

    public static void setHouseLoan(HouseLoan houseLoan) {
        TeacherInitManager.houseLoan = houseLoan;
    }

    public static CarLoan getCarLoan() {
        return carLoan;
    }

    public static void setCarLoan(CarLoan carLoan) {
        TeacherInitManager.carLoan = carLoan;
    }

    public static OtherLoan getOtherLoan() {
        return otherLoan;
    }

    public static void setOtherLoan(OtherLoan otherLoan) {
        TeacherInitManager.otherLoan = otherLoan;
    }

    public static ContryDeposit getContryDeposit() {
        return contryDeposit;
    }

    public static void setContryDeposit(ContryDeposit contryDeposit) {
        TeacherInitManager.contryDeposit = contryDeposit;
    }
}
