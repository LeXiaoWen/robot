package com.leo.robot.utils;

import com.leo.robot.bean.AuthorBean;
import com.leo.robot.bean.GetPicBean;

/**
 * created by Leo on 2019/4/20 10 : 48
 */


public class CommandUtils {

    private static byte[] msg = new byte[5];
    private static byte[] msg1 = new byte[6];

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

    /**
     * 末端位移左旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmDisRotateLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0D;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移右旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmDisRotateRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0E;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向左
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmDisLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x11;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向右
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmDisRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x12;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向上
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmDisUp() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x0F;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向下
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmDisDown() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x10;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }


    /**
     * 末端位姿左旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmPosRotateLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x13;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿右旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmPosRotateRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x14;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿向左
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmPosLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x17;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿向右
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmPosRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x18;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿向上
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmPosUp() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x15;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿向下
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getMainArmPosDown() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0x16;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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
        msg[1] = (byte) 0x02;
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


    /**
     * 末端位移左旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmDisRotateLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x0D;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移右旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmDisRotateRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x0E;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向左
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmDisLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x11;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向右
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmDisRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x12;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向上
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmDisUp() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x0F;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向下
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmDisDown() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x10;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿左旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmPosRotateLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x13;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿右旋
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmPosRotateRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x14;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿向左
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmPosLeft() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x17;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿移向右
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmPosRight() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x18;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位姿向上
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmPosUp() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x15;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 末端位移向下
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getFlowArmPosDown() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x02;
        msg[2] = (byte) 0x16;
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

    /**
     * 状态复位
     *
     * @author Leo
     * created at 2019/5/2 2:32 PM
     */
    public static String getStrippingStatusReset() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 剥线停止
     *
     * @author Leo
     * created at 2019/5/2 2:37 PM
     */
    public static String getStrippingStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x08;
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
    }

    /**
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
    }

    /**
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

    //------------------------ 接线工具 start --------------------------

    /**
     * 打开设备
     *
     * @author Leo
     * created at 2019/5/2 1:50 PM
     */
    public static String getTwistOpen() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 支线夹紧
     *
     * @author Leo
     * created at 2019/5/2 1:51 PM
     */
    public static String getTwistFeederClamp() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x0D;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 支线夹紧复位
     *
     * @author Leo
     * created at 2019/5/2 1:56 PM
     */
    public static String getTwistFeederClampReset() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x0E;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 拧断开始
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getTwistStart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 拧断停止
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getTwistEnd() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x05;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 拧断翻转
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getTwistFlip() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x06;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 拧断初始
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getTwistInit() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 线夹解锁
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getClipUnlock() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x08;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 线夹锁定
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getClipLock() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x09;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 套筒解锁
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getSleeveUnlock() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x0B;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 套筒锁定
     *
     * @author Leo
     * created at 2019/4/20 6:57 PM
     */
    public static String getSleeveLock() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x0A;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 套筒停止
     *
     * @author Leo
     * created at 2019/5/2 1:54 PM
     */
    public static String getSleeveStop() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x09;
        msg[2] = (byte) 0x0C;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }


    public static GetPicBean getPicBean1(String location) {
        GetPicBean getPicBean = new GetPicBean();
        GetPicBean.ParamsBean paramsBean = new GetPicBean.ParamsBean();
        paramsBean.setWidth("640");
        paramsBean.setHeight("480");
        paramsBean.setCoordinates(location);
        getPicBean.setMsgType("CmdUploadPic1");
        getPicBean.setVersion("1.0");
        getPicBean.setParams(paramsBean);
        return getPicBean;
    }

    public static GetPicBean getPicBean2() {
        GetPicBean getPicBean = new GetPicBean();
        GetPicBean.ParamsBean paramsBean = new GetPicBean.ParamsBean();
        paramsBean.setWidth("640");
        paramsBean.setHeight("480");
        paramsBean.setCoordinates("100.0,210.0");
        getPicBean.setMsgType("CmdUploadPic1");
        getPicBean.setVersion("2.0");
        getPicBean.setParams(paramsBean);
        return getPicBean;
    }

    public static AuthorBean getMasterControlBean() {
        AuthorBean authorBean = new AuthorBean();
        AuthorBean.ParamsBean paramsBean = new AuthorBean.ParamsBean();
        authorBean.setMsgType("clientInfo");
        authorBean.setVersion("1.0");
        paramsBean.setMsg("Hello,主控服务器！");
        authorBean.setParams(paramsBean);
        return authorBean;
    }

    public static AuthorBean getMasterArmBean() {
        AuthorBean authorBean = new AuthorBean();
        AuthorBean.ParamsBean paramsBean = new AuthorBean.ParamsBean();
        authorBean.setMsgType("clientInfo");
        authorBean.setVersion("1.0");
        paramsBean.setMsg("Hello,主机械臂！");
        authorBean.setParams(paramsBean);
        return authorBean;
    }

    public static AuthorBean getVisionBean() {
        AuthorBean authorBean = new AuthorBean();
        AuthorBean.ParamsBean paramsBean = new AuthorBean.ParamsBean();
        authorBean.setMsgType("clientInfo");
        authorBean.setVersion("1.0");
        paramsBean.setMsg("Hello,视觉服务器！");
        authorBean.setParams(paramsBean);
        return authorBean;
    }

    //------------------------ 接线工具 end --------------------------

    //------------------------ 滑台控制命令 --------------------------

    /**
     * 水平滑台左移
     *
     * @author Leo
     * created at 2019/5/27 12:02 AM
     */
    public static String landSlideTableLeftMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x04;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 横向滑台右移
     *
     * @author Leo
     * created at 2019/5/27 12:03 AM
     */
    public static String landSlideTableRightMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x04;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 水平滑台复位
     *
     * @author Leo
     * created at 2019/5/27 12:03 AM
     */
    public static String landSlideTableResetMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x04;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 水平滑台停止
     *
     * @author Leo
     * created at 2019/5/27 9:08 PM
     */
    public static String landSlideTableStopMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x04;
        msg[2] = (byte) 0x05;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    //------------------------ 垂直滑台 --------------------------

    /**
     * 垂直滑台上移命令
     *
     * @author Leo
     * created at 2019/5/27 9:08 PM
     */
    public static String verticalSlideTableUpMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x05;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 垂直滑台下移命令
     *
     * @author Leo
     * created at 2019/5/27 9:08 PM
     */
    public static String verticalSlideTableDownMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x05;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 垂直滑台复位命令
     *
     * @author Leo
     * created at 2019/5/27 9:08 PM
     */
    public static String verticalSlideTableResetMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x05;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 垂直滑台停止命令
     *
     * @author Leo
     * created at 2019/5/27 9:08 PM
     */
    public static String verticalSlideTableStopMove() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x05;
        msg[2] = (byte) 0x05;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    //------------------------ 请求水平滑台位置 --------------------------

    /**
     * 请求水平滑台位置
     *
     * @author Leo
     * created at 2019/5/28 12:28 AM
     */
    public static String getLandSlideTable() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x04;
        msg[2] = (byte) 0x06;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 请求垂直滑台位置
     *
     * @author Leo
     * created at 2019/5/28 12:30 AM
     */
    public static String getVerticalSlideTable() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x05;
        msg[2] = (byte) 0x06;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 确认垂直滑台位置
     *
     * @author Leo
     * created at 2019/5/29 10:18 PM
     */
    public static String confirmVerticalSlideTable() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x05;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 确认水平滑台位置
     *
     * @author Leo
     * created at 2019/6/17 8:16 PM
     */
    public static String confirmLandSlideTable() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x04;
        msg[2] = (byte) 0x07;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    //------------------------ 线路作业指令 --------------------------


    /**
     * A线路作业指令
     *
     * @author Leo
     * created at 2019/5/29 9:06 PM
     */
    public static String aLineOrder() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0C;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * B线路作业指令
     *
     * @author Leo
     * created at 2019/5/29 9:06 PM
     */
    public static String bLineOrder() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0C;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * C线路作业指令
     *
     * @author Leo
     * created at 2019/5/29 9:06 PM
     */
    public static String cLineOrder() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0C;
        msg[2] = (byte) 0x03;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
     * 请求行线、引流线距离
     *
     * @author Leo
     * created at 2019/5/30 9:07 PM
     */
    public static String lineLocation() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x08;
        msg[2] = (byte) 0x0C;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }


    /**
     * 主控服务器心跳
     *
     * @author Leo
     * created at 2019/6/17 7:53 PM
     */
    public static String masterNettyHeart() {
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0C;
        msg[2] = (byte) 0x04;
        msg[3] = (byte) 0x01;
        msg[4] = (byte) 0x00;
        return ConvertCode.bytes2HexString(msg);
    }

    /**
    * 手爪张开
    *
    *@author Leo
    *created at 2019/6/19 12:55 AM
    */
    public static String clawOpen() {
        msg1[0] = (byte) 0x68;
        msg1[1] = (byte) 0x0C;
        msg1[2] = (byte) 0x03;
        msg1[3] = (byte) 0x01;
        msg1[4] = (byte) 0x00;
        msg1[5] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg1);
    }

    /**
    * 手爪夹紧
    *
    *@author Leo
    *created at 2019/6/19 12:55 AM
    */
    public static String clawClamping() {
        msg1[0] = (byte) 0x68;
        msg1[1] = (byte) 0x0C;
        msg1[2] = (byte) 0x03;
        msg1[3] = (byte) 0x01;
        msg1[4] = (byte) 0x01;
        msg1[5] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg1);
    }

    /**
    * 继续作业
    *
    *@author Leo
    *created at 2019/6/19 12:59 AM
    */
    public static String continueWork(){
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0C;
        msg[2] = (byte) 0x01;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }
    /**
    * 解除异常
    *
    *@author Leo
    *created at 2019/6/19 12:59 AM
    */
    public static String undoException(){
        msg[0] = (byte) 0x68;
        msg[1] = (byte) 0x0C;
        msg[2] = (byte) 0x02;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0xFF;
        return ConvertCode.bytes2HexString(msg);
    }




    /**
     * usb1 第一个点位 确认选点
     */
    public static String CAMERA1_LOCATION1 = "64021001000000";
    /**
     * usb1 第二个点位 确认选点
     */
    public static String CAMERA1_LOCATION2 = "64021002000000";
    /**
     * usb2 第一个点位 确认选点
     */
    public static String CAMERA2_LOCATION1 = "64061001000000";
    /**
     * usb2 第二个点位 确认选点
     */
    public static String CAMERA2_LOCATION2 = "64061002000000";

    /**
     * usb1  确认雷达锚点结果
     */
    public static String CAMERA1_CONFIRM = "640300FF";

    /**
     * usb2  确认雷达锚点结果
     */
    public static String CAMERA2_CONFIRM = "640700FF";
    /**
     * usb1  舍弃雷达锚点结果
     */
    public static String CAMERA1_CANCEL = "640400FF";

    /**
     * usb2  舍弃雷达锚点结果
     */
    public static String CAMERA2_CANCEL = "640800FF";


}
