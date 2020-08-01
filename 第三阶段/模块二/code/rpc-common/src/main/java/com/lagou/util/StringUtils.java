package com.lagou.util;

public class StringUtils {
    public static int toInt(String str, int defaultValue){
        int result = defaultValue;
        try {
            result = Integer.parseInt(str);
        } catch (Exception e){

        }
        return result;
    }
}
