package com.xana.acg.fac.utils;

import java.util.regex.Pattern;

public class TextUtils {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    public static boolean isEmpty(String str){
        return str==null || str.trim().length()==0;
    }
}
