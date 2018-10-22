package com.mdlink.util;

public class ValidationsUtil {

    public static boolean isPasswordValid(String nPwd, String cPwd){
        return (nPwd == cPwd);
    }
    public static String getPaddedNumber(int number) {
        return String.format("%02d", number);
    }
}
