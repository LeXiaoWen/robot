package com.leo.robot.test;

import com.leo.robot.utils.ByteUtils;

/**
 * created by Leo on 2019/4/15 20 : 25
 */


public class Test {
    static char[] tmp = new char[]{0x3F,0xAB,0xAE,0xE2,0x3C,0x40,0x70,0xEB};
    static {
        // 加载库
        System.loadLibrary("ur5");
    }
    public static void main(String[] args) {

        String msg = "BFBFE320113D5F9B";

//        System.out.println(getValue(msg));
//        hexToDouble();

        byte[] bytes = ByteUtils.hex2byte(msg);
//        System.out.println(v);
    }


    public static Double getValue(String s) {
        long l = Long.parseLong(s, 16);
        System.out.println(l);
        return Double.longBitsToDouble(Long.parseLong(s, 16));
    }

    public static double parseHex4(String num) {
//        if (num.length() != 4) {
//            throw new NumberFormatException("Wrong length: " + num.length() + ", must be 4.");
//        }
        int ret = Integer.parseInt(num, 16);
        ret = ((ret & 0x8000) > 0) ? (ret - 0x10000) : (ret);
        return (double) ret;
    }


    /**
     * 十六进制转负数
     * <p>
     * (4个字节的)
     */
    public static float parseHex8(String num) {
        Float value = Float.intBitsToFloat(Integer.valueOf(num.trim(), 16));
        return value;
    }


    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;

    }


    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }


    public static native double hexToDouble(char buffer);
}
