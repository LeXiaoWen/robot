package com.leo.robot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by Leo on 2019/4/25 22 : 00
 */


public class DateUtils {
    private String format = "yyyy年MM月dd日 HH:mm:ss";

    public static String getCurrentDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
