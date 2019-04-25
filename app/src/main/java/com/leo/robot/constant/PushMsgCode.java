package com.leo.robot.constant;

/**
 * created by Leo on 2019/4/20 23 : 35
 */


public interface PushMsgCode {

    String WIRING = "09";
    String WIRE_STRIPPING = "08";
    String CUT_LINE = "0A";

    //------------------------ 剥线界面 start --------------------------
    /**
     * 就绪
     */
    String WIRE_STRIPPING_READY = "6708010101FF";
    String WIRE_STRIPPING_NOT_READY = "6708010100FF";

    /**
     * 初始化动作
     */
    String WIRE_STRIPPING_INIT = "6708020101FF";
    String WIRE_STRIPPING_NOT_INIT = "6708020100FF";

    /**
     * 剥线工具
     */
    String WIRE_STRIPPING_TOOL_READY = "6708030101FF";
    String WIRE_STRIPPING_TOOL_NOT_READY = "6708030100FF";

    /**
     * 主线夹紧
     */
    String WIRE_STRIPPING_CLAMPING = "6708040101FF";
    String WIRE_STRIPPING_NOT_CLAMPING = "6708040100FF";

    /**
     * 夹具闭合
     */

    String WIRE_STRIPPING_CLOSURE = "6708050101FF";
    String WIRE_STRIPPING_NOT_CLOSURE = "6708050100FF";

    /**
     * 旋转剥皮
     */

    String WIRE_STRIPPING_PEELING = "6708060101FF";
    String WIRE_STRIPPING_NOT_PEELING = "6708060100FF";

    /**
     * 切断绝缘皮
     */
    String WIRE_STRIPPING_CUT_OFF = "6708070101FF";
    String WIRE_STRIPPING_NOT_CUT_OFF = "6708070100FF";

    /**
     * 解锁
     */
    String WIRE_STRIPPING_UNLOCK = "6708080101FF";
    String WIRE_STRIPPING_NOT_UNLOCK = "6708080100FF";
    /**
     * 结束
     */
    String WIRE_STRIPPING_END = "6708090101FF";
    String WIRE_STRIPPING_NOT_END = "6708090100FF";
    //------------------------ 剥线界面 end  --------------------------


    //------------------------ 接线界面 start --------------------------

    /**
     * 就绪
     */
    String WIRING_READY = "6709010101FF";
    String WIRING_NOT_READY = "6709010100FF";
    /**
     * 引流抓取
     */
    String WIRING_GRAB = "6709020101FF";
    String WIRING_NOT_GRAB = "6709020100FF";
    /**
     * 引线进入
     */
    String WIRING_ENTER = "6709030101FF";
    String WIRING_NOT_ENTER = "6709030100FF";
    /**
     * 引线固定
     */
    String WIRING_FIXED = "6709040101FF";
    String WIRING_NOT_FIXED = "6709040100FF";
    /**
     * 接线工具就位
     */
    String WIRING_TOOL_READY = "6709050101FF";
    String WIRING_NOT_TOOL_READY = "6709050100FF";
    /**
     * 并勾线夹就位
     */
    String WIRING_LINE_READY = "6709060101FF";
    String WIRING_NOT_LINE_READY = "6709060100FF";
    /**
     * 拧断螺母
     */
    String WIRING_TWIST = "6709070101FF";
    String WIRING_NOT_TWIST = "6709070100FF";
    /**
     * 线夹解锁
     */
    String WIRING_CLIP_UNLOCK = "6709080101FF";
    String WIRING_NOT_CLIP_UNLOCK = "6709080100FF";
    /**
     * 套筒解锁
     */
    String WIRING_SLEEVE_UNLOCK = "6709090101FF";
    String WIRINGNOT_SLEEVE_UNLOCK = "6709080100FF";
    /**
     * 结束
     */
    String WIRING_END = "67090A0101FF";
    String WIRING_NOT_END = "67090A0100FF";


    //------------------------ 接线界面 end --------------------------


    // ------------------------ 剪线界面 start --------------------------

    /**
     * 到位
     */
    String CUT_READY = "670A010101FF";
    String CUT_NOT_READY = "670A010100FF";
    /**
     * 剪线开始
     */
    String CUT_START = "670A020101FF";
    String CUT_NOT_START = "670A020100FF";
    /**
     * 剪线停止
     */
    String CUT_STOP = "670A030101FF";
    String CUT_NOT_STOP = "670A030100FF";
    /**
     * 钳口复位
     */
    String CUT_RESET = "670A040101FF";
    String CUT_NOT_RESET= "670A040101FF";
    /**
     * 结束
     */
    String CUT_END = "670A050101FF";
    String CUT_NOT_END = "670A050100FF";


    //------------------------ 剪线界面 end --------------------------
}
