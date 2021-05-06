package com.xana.acg.com.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static com.xana.acg.com.Common.DIGIT.BAN;
import static com.xana.acg.com.Common.DIGIT.HYAKUBAN_MIN;
import static com.xana.acg.com.Common.DIGIT.HYAKUBAN;
import static com.xana.acg.com.Common.DIGIT.OKU;

public class TextUtils {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    public static boolean isEmpty(String str){
        return str==null || str.trim().length()==0;
    }

    public static String getCountStr(long count){
        return count>=OKU?count/OKU+"亿":count>=HYAKUBAN? count/BAN+"万":""+count;
    }


    public static final String[] week = { "日", "月", "水", "木", "火", "金", "土" };

    public static String dayForWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return week[w]+"曜日 "+ DateFormat.format("dd/MM", date);
    }

    public static String time(long mill){

        int sec = (int) (mill/1000);
        int min = sec/60;
        int s = sec%60;
        if(mill< HYAKUBAN_MIN){
            return String.format("%02d:%02d", min, s);
        }
        return String.format("%03d:%02d", min, s);
    }

}
