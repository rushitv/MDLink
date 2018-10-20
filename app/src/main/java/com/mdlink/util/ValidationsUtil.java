package com.mdlink.util;

public class ValidationsUtil {

    public static boolean isPasswordValid(String nPwd, String cPwd){
        return (nPwd == cPwd);
    }
}
