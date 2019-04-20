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
     * @author Leo
     * created at 2019/4/20 5:03 PM
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
     * @author Leo
     * created at 2019/4/20 5:03 PM
     */
    public static String getMainArmStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕1加
     *
     * @author Leo
     * created at 2019/4/20 5:39 PM
     */
    public static String getMainArmWrist1Add() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x08;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕1减
     *
     * @author Leo
     * created at 2019/4/20 5:40 PM
     */
    public static String getMainArmWrist1Dec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕2加
     *
     * @author Leo
     * created at 2019/4/20 5:39 PM
     */
    public static String getMainArmWrist2Add() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0A;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕2减
     *
     * @author Leo
     * created at 2019/4/20 5:40 PM
     */
    public static String getMainArmWrist2Dec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x09;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕3加
     *
     * @author Leo
     * created at 2019/4/20 5:39 PM
     */
    public static String getMainArmWrist3Add() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0C;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕3减
     *
     * @author Leo
     * created at 2019/4/20 5:40 PM
     */
    public static String getMainArmWrist3Dec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0B;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肘部减
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getMainArmElbowDec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x05;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肘部加
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getMainArmElbowAdd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x06;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肩部减
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getMainArmShoulderDec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肩部加
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getMainArmShoulderAdd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 基座减
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getMainArmPedestalDec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 基座加
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getMainArmPedestalAdd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }


    /**
     * 主臂动作停止
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmActionStop() {
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
     * @author Leo
     * created at 2019/4/20 5:03 PM
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
     * @author Leo
     * created at 2019/4/20 5:03 PM
     */
    public static String getFlowArmStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕1加
     *
     * @author Leo
     * created at 2019/4/20 5:39 PM
     */
    public static String getFlowArmWrist1Add() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x08;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕1减
     *
     * @author Leo
     * created at 2019/4/20 5:40 PM
     */
    public static String getFlowArmWrist1Dec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕2加
     *
     * @author Leo
     * created at 2019/4/20 5:39 PM
     */
    public static String getFlowArmWrist2Add() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0A;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕2减
     *
     * @author Leo
     * created at 2019/4/20 5:40 PM
     */
    public static String getFlowArmWrist2Dec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x09;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕3加
     *
     * @author Leo
     * created at 2019/4/20 5:39 PM
     */
    public static String getFlowArmWrist3Add() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0C;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 手腕3减
     *
     * @author Leo
     * created at 2019/4/20 5:40 PM
     */
    public static String getFlowArmWrist3Dec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0B;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肘部减
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getFlowArmElbowDec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x05;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肘部加
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getFlowArmElbowAdd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x06;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肩部减
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getFlowArmShoulderDec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 肩部加
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getFlowArmShoulderAdd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 基座减
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getFlowArmPedestalDec() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 基座加
     *
     * @author Leo
     * created at 2019/4/20 5:42 PM
     */
    public static String getFlowArmPedestalAdd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 从臂动作停止
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmActionStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x21;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }


    //------------------------ 从臂命令 end --------------------------


    //------------------------ 剥线工具命令 start --------------------------

    /**
     * 从臂初始化动作
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingInit() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 剥线工具就绪
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingToolReady() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 主线夹紧
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingMainLineClamping() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 夹具闭合
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingClampClosure() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 旋转剥皮
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingRotaryPeeling() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 切断绝缘皮
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingCutOff() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x05;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 解锁
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getStrippingUnlock() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x06;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    //------------------------ 剥线工具 end --------------------------

    //------------------------ 剪线工具 start --------------------------
    /**
     * 剪线开始
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getCutToolStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0A;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }/**
     * 钳口复位
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getCutToolReset() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0A;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }/**
     * 剪线结束
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getCutToolStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0A;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }
    //------------------------ 剪线工具 end --------------------------
}
