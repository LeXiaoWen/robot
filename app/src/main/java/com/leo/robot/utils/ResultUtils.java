package com.leo.robot.utils;

import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.PushMsg;
import com.leo.robot.constant.RobotInit;

import cree.mvp.util.bus.BusUtils;
import cree.mvp.util.data.SPUtils;

/**
 * 处理服务器推送消息
 * created by Leo on 2019/4/20 23 : 08
 */


public class ResultUtils {
    public static final int PUSH_MSG = 1, ERRO_MSG = 0;

    /**
     * 消息的统一分发
     *
     * @param
     * @param type
     */
    public static void onResult(String msg, int type) {
        switch (type) {
            case PUSH_MSG:
                pushMsg(msg);
                break;
            case ERRO_MSG:
                erroMsg(msg);
                break;
        }
    }

    /**
     * 处理连接失败消息
     *
     * @author Leo
     * created at 2019/4/20 11:26 PM
     */
    private static void erroMsg(String msg) {
        ErroMsg erroMsg = new ErroMsg();
        erroMsg.setCode(400);
        erroMsg.setMsg(msg);
        BusUtils.postMessage(erroMsg);
    }

    /**
     * 推送消息信息
     *
     * @param msg
     * @author Leo
     * created at 2019/4/20 11:25 PM
     */

    private static void pushMsg(String msg) {
        String s = msg.substring(2, 4);
        if (PushMsg.WIRE_STRIPPING.equals(s)) {//剥线
            onWiringStripping(msg);
        } else if (PushMsg.WIRING.equals(s)) {//接线
            onWiring(msg);
        } else if (PushMsg.CUT_LINE.equals(s)) {//剪线
            onCutLine(msg);
        }
    }

    /**
     * 剥线界面
     *
     * @author Leo
     * created at 2019/4/20 11:11 PM
     */

    private static void onWiringStripping(String msg) {
        SPUtils utils = new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY);
        WireStrippingMsg strippingMsg = new WireStrippingMsg();
        if (msg.equals(PushMsg.WIRE_STRIPPING_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_READY, true);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_READY);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_READY, false);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_READY);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_INIT)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_INIT, true);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_INIT);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_INIT)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_INIT, false);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_INIT);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_TOOL_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_TOOL_READY, true);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_TOOL_READY);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_TOOL_NOT_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_TOOL_READY, false);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_TOOL_NOT_READY);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_CLAMPING)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLAMPING, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_CLAMPING);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_CLAMPING)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLAMPING, false);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_CLAMPING);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_CLOSURE)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLOSURE, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_CLOSURE);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_CLOSURE)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLOSURE, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_CLOSURE);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_PEELING)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_PEELING, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_PEELING);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_PEELING)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_PEELING, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_PEELING);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_CUT_OFF)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CUT_OFF, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_CUT_OFF);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_CUT_OFF)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CUT_OFF, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_CUT_OFF);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_UNLOCK)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_UNLOCK, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_UNLOCK);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_UNLOCK)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_UNLOCK, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_UNLOCK);
            strippingMsg.setHighlight(false);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_END)) {

            utils.putBoolean(RobotInit.WIRE_STRIPPING_END, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_END);
            strippingMsg.setHighlight(true);
        }
        else if (msg.equals(PushMsg.WIRE_STRIPPING_NOT_END)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_END, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_END);
            strippingMsg.setHighlight(false);
        }

        BusUtils.postMessage(strippingMsg);

    }

    /**
     * 接线界面
     *
     * @author Leo
     * created at 2019/4/20 11:11 PM
     */
    private static void onWiring(String msg) {
        SPUtils utils = new SPUtils(RobotInit.WIRING_ACTIVITY);
        if (msg.equals(PushMsg.WIRING_TOOL_READY)){
            utils.putBoolean(RobotInit.WIRING_TOOL_READY,true);
        }else if (msg.equals(PushMsg.WIRING_NOT_TOOL_READY)){
            utils.putBoolean(RobotInit.WIRING_TOOL_READY, false);
        }
    }

    /**
     * 剪线界面
     *
     * @author Leo
     * created at 2019/4/20 11:11 PM
     */
    private static void onCutLine(String msg) {
        SPUtils utils = new SPUtils(RobotInit.CUT_LINE_ACTIVITY);
        if (msg.equals(PushMsg.CUT_READY)){
            utils.putBoolean(RobotInit.CUT_READY,true);
        }else {
            utils.putBoolean(RobotInit.CUT_READY,false);
        }
    }

    /**
     * 主界面
     *
     * @author Leo
     * created at 2019/4/20 11:11 PM
     */
    private static void onMain(String msg) {

    }
}
