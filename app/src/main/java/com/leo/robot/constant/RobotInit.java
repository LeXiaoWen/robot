package com.leo.robot.constant;

/**
 * created by Leo on 2019/4/20 23 : 02
 */


public interface RobotInit {
    String PUSH_KEY = "push";
    String PUSH_MSG = "msg";
    String WIRE_STRIPPING_ACTIVITY = "wireStripping";
    String WIRING_ACTIVITY = "wiring";
    String CUT_LINE_ACTIVITY = "cutLine";
    int CLICK_TAG = 1;
    int UNCLICK_TAG = 2;

    //------------------------ 剥线 start --------------------------
    /**
     * 就绪
     */
    String WIRE_STRIPPING_READY = "WIRE_STRIPPING_READY";
    String WIRE_STRIPPING_NOT_READY = "WIRE_STRIPPING_NOT_READY";

    /**
     * 初始化动作
     */
    String WIRE_STRIPPING_INIT = "WIRE_STRIPPING_INIT";
    String WIRE_STRIPPING_NOT_INIT = "WIRE_STRIPPING_NOT_INIT";

    /**
     * 剥线工具
     */
    String WIRE_STRIPPING_TOOL_READY = "WIRE_STRIPPING_TOOL_READY";
    String WIRE_STRIPPING_TOOL_NOT_READY = "WIRE_STRIPPING_TOOL_NOT_READY";

    /**
     * 主线夹紧
     */
    String WIRE_STRIPPING_CLAMPING = "WIRE_STRIPPING_CLAMPING";
    String WIRE_STRIPPING_NOT_CLAMPING = "WIRE_STRIPPING_NOT_CLAMPING";

    /**
     * 夹具闭合
     */

    String WIRE_STRIPPING_CLOSURE = "WIRE_STRIPPING_CLOSURE";
    String WIRE_STRIPPING_NOT_CLOSURE = "WIRE_STRIPPING_NOT_CLOSURE";

    /**
     * 旋转剥皮
     */

    String WIRE_STRIPPING_PEELING = "WIRE_STRIPPING_PEELING";
    String WIRE_STRIPPING_NOT_PEELING = "WIRE_STRIPPING_NOT_PEELING";

    /**
     * 切断绝缘皮
     */
    String WIRE_STRIPPING_CUT_OFF = "WIRE_STRIPPING_CUT_OFF";
    String WIRE_STRIPPING_NOT_CUT_OFF = "WIRE_STRIPPING_NOT_CUT_OFF";

    /**
     * 解锁
     */
    String WIRE_STRIPPING_UNLOCK = "WIRE_STRIPPING_UNLOCK";
    String WIRE_STRIPPING_NOT_UNLOCK = "WIRE_STRIPPING_NOT_UNLOCK";
    /**
     * 结束
     */
    String WIRE_STRIPPING_END = "WIRE_STRIPPING_END";
    String WIRE_STRIPPING_NOT_END = "WIRE_STRIPPING_NOT_END";

    //------------------------ 剥线 end --------------------------


    //------------------------ 接线 start --------------------------
    /**
     * 就绪
     */
    String WIRING_READY = "WIRING_READY";
    String WIRING_NOT_READY = "WIRING_NOT_READY";

    /**
     * 初始化动作
     */
    String WIRING_INIT = "WIRING_INIT";
    String WIRING_NOT_INIT = "WIRING_NOT_INIT";

    /**
     * 剥线工具
     */
    String WIRING_TOOL_READY = "WIRING_TOOL_READY";
    String WIRING_NOT_TOOL_READY = "WIRING_TOOL_NOT_READY";

    /**
     * 主线夹紧
     */
    String WIRING_CLAMPING = "WIRING_CLAMPING";
    String WIRING_NOT_CLAMPING = "WIRING_NOT_CLAMPING";

    /**
     * 夹具闭合
     */

    String WIRING_CLOSURE = "WIRING_CLOSURE";
    String WIRING_NOT_CLOSURE = "WIRING_NOT_CLOSURE";

    /**
     * 旋转剥皮
     */

    String WIRING_PEELING = "WIRING_PEELING";
    String WIRING_NOT_PEELING = "WIRING_NOT_PEELING";

    /**
     * 切断绝缘皮
     */
    String WIRING_CUT_OFF = "WIRING_CUT_OFF";
    String WIRING_NOT_CUT_OFF = "WIRING_NOT_CUT_OFF";

    /**
     * 解锁
     */
    String WIRING_UNLOCK = "WIRING_UNLOCK";
    String WIRING_NOT_UNLOCK = "WIRING_NOT_UNLOCK";
    /**
     * 结束
     */
    String WIRING_END = "WIRING_END";
    String WIRING_NOT_END = "WIRING_NOT_END";
    //------------------------ 接线 end --------------------------

    // ------------------------ 剪线界面 start --------------------------

    /**
     * 到位
     */
    String CUT_READY = "CUT_READY";
    String CUT_NOT_READY = "CUT_NOT_READY";
    /**
     * 剪线开始
     */
    String CUT_START = "CUT_START";
    String CUT_NOT_START = "CUT_NOT_START";
    /**
     * 剪线停止
     */
    String CUT_STOP = "CUT_STOP";
    String CUT_NOT_STOP = "CUT_NOT_STOP";
    /**
     * 钳口复位
     */
    String CUT_RESET = "CUT_RESET";
    String CUT_NOT_RESET= "CUT_NOT_RESET";
    /**
     * 结束
     */
    String CUT_END = "670A050101FF";
    String CUT_NOT_END = "670A050100FF";


    //------------------------ 剪线界面 end --------------------------

}
