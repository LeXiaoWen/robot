package com.leo.robot.utils;

import com.leo.robot.JNIUtils;

/**
 * created by Leo on 2019/6/25 22 : 47
 */


public class PowerUtils {
    public static String getPowerByType(String msg, String type){
        String s = msg.substring(8, 64);
        JNIUtils.GetDevicePowerMsg(s);
        float v = JNIUtils.ReadDevicePower(type);
        int v1 = (int)(v * 100);
        String s1 = String.valueOf(v1);
        return s1+"%";
    }
}
