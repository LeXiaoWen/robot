package com.leo.robot.utils;

/**
 * created by Leo on 2019/4/20 10 : 48
 */


public class CommandUtils {

    private static byte[] msg = new byte[5];

    //急停
    public static String getStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x1E;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);

    }

    //恢复急停
    public static String getResumeStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x1F;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

//    0x68,0x02,0x22,0x00,0xFF

    /**
     * 剥线、接线页面从臂回收
     *
     * @author Leo
     * created at 2019/4/20 2:50 PM
     */
    public static String getRecover() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x22;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

//    0x68,0x01,0x22,0x00,0xFF

    /**
     * 剪线界面主臂回收
     *
     * @author Leo
     * created at 2019/4/20 2:51 PM
     */
    public static String getCutLineRecover() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x22;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

//    0x68,0x02,0x20,0x00,0xFF

    /**
     * 剥线页面从臂开始
     *
     * @author Leo
     * created at 2019/4/20 2:55 PM
     */
    public static String getFlowArmStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x20;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

//    0x68,0x02,0x21,0x00,0xFF

    /**
     * 剥线页面从臂停止
     *
     * @author Leo
     * created at 2019/4/20 2:55 PM
     */
    public static String getFlowArmStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

//    0x68,0x01,0x20,0x00,0xFF

    /**
     * 接线、剪线页面主臂开始
     *
     * @author Leo
     * created at 2019/4/20 2:55 PM
     */
    public static String getMainArmStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x20;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

//    0x68,0x01,0x21,0x00,0xFF

    /**
     * 接线、剪线页面主臂停止
     *
     * @author Leo
     * created at 2019/4/20 2:55 PM
     */
    public static String getMainArmStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    //------------------------  --------------------------

    /**
     * 剪线开始
     *
     * @author Leo
     * created at 2019/4/20 3:04 PM
     */
    public static String getCutStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 剪线停止
     *
     * @author Leo
     * created at 2019/4/20 3:04 PM
     */
    public static String getCutStopt() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

      /**
      * 钳口复位
      *
      *@author Leo
      *created at 2019/4/20 3:05 PM
      */
    public static String getJawReset() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }


}
