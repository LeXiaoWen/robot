package com.leo.robot.netty.command;

import com.leo.robot.utils.ConvertCode;

/**
 * created by Leo on 2019/4/19 21 : 49
 */


public class CommandConst {
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

}
