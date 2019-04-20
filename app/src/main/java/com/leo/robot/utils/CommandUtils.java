package com.leo.robot.utils;

/**
 * created by Leo on 2019/4/20 10 : 48
 */


public class CommandUtils {

    private static byte[] msg = new byte[5];

    //------------------------ 主臂命令 start --------------------------
    /**
     * 急停
     *
     * @author Leo
     * created at 2019/4/20 5:00 PM
     */
    public static String getMainArmShutdown() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x1E;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);

    }

    /**
     * 恢复急停
     *
     * @author Leo
     * created at 2019/4/20 5:00 PM
     */
    public static String getMainArmResume() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x1F;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 一键收回
     *
     * @author Leo
     * created at 2019/4/20 4:56 PM
     */
    public static String getMainArmRecover() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x22;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 开始
     *
     *@author Leo
     *created at 2019/4/20 5:03 PM
     */
    public static String getMainArmStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x20;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 停止
     *
     *@author Leo
     *created at 2019/4/20 5:03 PM
     */
    public static String getMainArmStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }
    //------------------------ 主臂命令 end --------------------------


    //------------------------ 从臂命令 start --------------------------
    /**
     * 急停
     *
     * @author Leo
     * created at 2019/4/20 5:00 PM
     */
    public static String getFlowArmShutdown() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x1E;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);

    }

    /**
     * 恢复急停
     *
     * @author Leo
     * created at 2019/4/20 5:00 PM
     */
    public static String getFlowArmResume() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x1F;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 一键收回
     *
     * @author Leo
     * created at 2019/4/20 4:56 PM
     */
    public static String getFlowArmRecover() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x22;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 开始
     *
     *@author Leo
     *created at 2019/4/20 5:03 PM
     */
    public static String getFlowArmStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x20;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 停止
     *
     *@author Leo
     *created at 2019/4/20 5:03 PM
     */
    public static String getFlowArmStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }
    //------------------------ 从臂命令 end --------------------------

}
