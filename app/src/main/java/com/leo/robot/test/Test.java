package com.leo.robot.test;

import com.leo.robot.utils.ByteUtils;

/**
 * created by Leo on 2019/4/15 20 : 25
 */


public class Test {

//    static {
//        System.loadLibrary("libUR10");
//    }
    private static String msg = "6704030401020300FF";
//    public static native String ActionMove(String msg);

    static char[] tmp = new char[]{0x3F, 0xAB, 0xAE, 0xE2, 0x3C, 0x40, 0x70, 0xEB};

    public static void main(String[] args) {

//        String msg = "BFBFE320113D5F9B";

//        System.out.println(getValue(msg));
//        hexToDouble();

//        byte[] bytes = ByteUtils.hex2byte(msg);
//        System.out.println(v);

//        int i = 9;
//        byte[] bytes = ByteUtils.intToByteArray(i);
//        for (byte aByte : bytes) {
//            System.out.println(aByte);
//        }
//        System.out.println(bytes);



//        String s = ActionMove("ACTION_MOVE_1");
//        System.out.println(s);

        String s = msg.substring(8, 16);
        byte[] bytes = ByteUtils.hex2byte(s);
        int i = ByteUtils.byteArrayToInt(bytes);
        System.out.println(i);


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


}
