package com.leo.robot.test;

/**
 * created by Leo on 2019/4/15 20 : 25
 */


public class Test {
    public static void main(String[] args) {
//        System.out.println(getValue("bffeb60d5110b460"));
//        System.out.println(getValue("60b410510db6febf"));
        String msg = "6708010101FF";
        String s = msg.substring(2, 4);
        System.out.println(s);
    }


    public static Double getValue(String s) {
        return Double.longBitsToDouble(Long.parseLong(s, 16));
    }


}
