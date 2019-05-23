package com.leo.robot.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * created by Leo on 2019/4/19 22 : 23
 */


public class MyDecoder extends ByteToMessageDecoder {

    private static final int MAX_FRAME_SIZE = 1108;

    private static final String TAG = "MyDecoder";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
//创建字节数组,buffer.readableBytes可读字节长度
//        byte[] b = new byte[buffer.readableBytes()];


        int readable = buffer.readableBytes();
        if (readable > MAX_FRAME_SIZE) {        // 缓冲区数据过大
            buffer.skipBytes(readable);                // 忽略所有可读的字节
            // 抛出异常通知这个帧数据超长
            throw new TooLongFrameException("帧数据超长");
        } else {

        }
        byte[] b = new byte[readable];
//复制内容到字节数组b
        buffer.readBytes(b);
//字节数组转字符串


        out.add(b);
    }

    public String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

}
