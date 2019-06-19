package com.leo.robot.utils;

import com.google.gson.Gson;
import com.leo.robot.bean.*;
import com.leo.robot.constant.PushMsgCode;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.arm.ArmBean;
import cree.mvp.util.bus.BusUtils;
import cree.mvp.util.data.SPUtils;

/**
 * 处理服务器推送消息
 * created by Leo on 2019/4/20 23 : 08
 */


public class ResultUtils {
    public static final int PUSH_MSG = 1, ERRO_MSG = 0;
    private static Gson mGson = new Gson();
    private static boolean isFirst = false;

    /**
     * 消息的统一分发
     *
     * @param
     * @param type
     */
    public static void onResult(String msg, int type) {
//        switch (type) {
//            case PUSH_MSG:
//                pushMsg(msg);
//                break;
//            case ERRO_MSG:
//                erroMsg(msg);
//                break;
//        }
    }




    /**
     * 主控服务器推送消息信息
     *
     * @param msg
     * @author Leo
     * created at 2019/4/20 11:25 PM
     */

    private static void masterControlpushMsg(String msg) {
//        SocketStatusBean testBean = new SocketStatusBean();
//        testBean.setType(RobotInit.MASTER_CONTROL_NETTY);
//        testBean.setMsg("接收到主控服务器发送的消息       ：" + msg);
//        BusUtils.postMessage(testBean);

        AllMsg allMsg = new AllMsg();
        LocationMsg locationMsg = new LocationMsg();
        String s = msg.substring(2, 4);
        if (PushMsgCode.WIRE_STRIPPING.equals(s)) {//剥线

            if (msg.contains(PushMsgCode.LINE_LOCATION)){//主控服务器回复行线、引流线位置
                LineLocationMsg lineLocationMsg = new LineLocationMsg();
                lineLocationMsg.setMsg("行线、引流线位置");
                lineLocationMsg.setCode(msg);
                BusUtils.postMessage(lineLocationMsg);
            }else {
                onWiringStripping(msg);
                allMsg.setMsg("剥线指令");
                allMsg.setCode(msg);
                BusUtils.postMessage(allMsg);
            }

        } else if (PushMsgCode.WIRING.equals(s)) {//接线
            onWiring(msg);
            allMsg.setMsg("接线指令");
            allMsg.setCode(msg);
            BusUtils.postMessage(allMsg);
        } else if (PushMsgCode.CUT_LINE.equals(s)) {//剪线
            onCutLine(msg);
            allMsg.setMsg("剪线指令");
            allMsg.setCode(msg);
            BusUtils.postMessage(allMsg);
        }else if (PushMsgCode.IS_AUTO_CONTROL.equals(s)){//是否能手动控制
            onControlModel(msg);
            allMsg.setMsg("是否能手动控制命令");
            allMsg.setCode(msg);
            BusUtils.postMessage(msg);
        }else if (msg.contains(PushMsgCode.VERTICAL_SLIDE_TABLE)){//垂直滑台

            locationMsg.setMsg(msg);
            locationMsg.setCode("0");
            BusUtils.postMessage(locationMsg);
        }else if (msg.contains(PushMsgCode.LAND_SLIDE_TABLE)){//水平滑台
            locationMsg.setMsg(msg);
            locationMsg.setCode("1");
            BusUtils.postMessage(locationMsg);
        }else if (PushMsgCode.CHOOSE_LOCATION.equals(s)){//选点指令
            ChooseCameraLocationMsg chooseCameraLocationMsg = new ChooseCameraLocationMsg();
            chooseCameraLocationMsg.setMsg("相机选点指令");
            chooseCameraLocationMsg.setCode(msg);
            BusUtils.postMessage(chooseCameraLocationMsg);
        }
//        else if (msg.contains("InfoArm")){
//            TakeBackBean takeBackBean =mGson.fromJson(msg,TakeBackBean.class);
//            BusUtils.postMessage(takeBackBean);
//        }else if (msg.contains("InfoOperatingMode")){
//            OperatingModeBean modeBean = mGson.fromJson(msg, OperatingModeBean.class);
//            BusUtils.postMessage(modeBean);
//        }
    }

    private static void onControlModel(String msg) {

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
        if (msg.equals(PushMsgCode.WIRE_STRIPPING_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_READY, true);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_READY);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_READY);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_READY, false);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_READY);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_READY);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_INIT)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_INIT, true);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_INIT);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_INIT);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_INIT)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_INIT, false);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_INIT);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_INIT);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_TOOL_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_TOOL_READY, true);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_TOOL_READY);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_TOOL_READY);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_TOOL_NOT_READY)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_TOOL_READY, false);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_TOOL_NOT_READY);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_TOOL_NOT_READY);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_CLAMPING)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLAMPING, true);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_CLAMPING);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_CLAMPING);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_CLAMPING)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLAMPING, false);
            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_CLAMPING);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_CLAMPING);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_CLOSURE)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLOSURE, true);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_CLOSURE);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_CLOSURE);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_CLOSURE)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CLOSURE, false);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_CLOSURE);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_CLOSURE);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_PEELING)) {
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_PEELING);
            utils.putBoolean(RobotInit.WIRE_STRIPPING_PEELING, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_PEELING);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_PEELING)) {
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_PEELING);
            utils.putBoolean(RobotInit.WIRE_STRIPPING_PEELING, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_PEELING);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_CUT_OFF)) {
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_CUT_OFF);
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CUT_OFF, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_CUT_OFF);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_CUT_OFF)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_CUT_OFF, false);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_CUT_OFF);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_CUT_OFF);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_UNLOCK)) {
            utils.putBoolean(RobotInit.WIRE_STRIPPING_UNLOCK, true);
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_UNLOCK);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_UNLOCK);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_UNLOCK)) {
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_UNLOCK);
            utils.putBoolean(RobotInit.WIRE_STRIPPING_UNLOCK, false);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_NOT_UNLOCK);
            strippingMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_END)) {

            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_END);
            utils.putBoolean(RobotInit.WIRE_STRIPPING_END, true);

            strippingMsg.setMsg(RobotInit.WIRE_STRIPPING_END);
            strippingMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.WIRE_STRIPPING_NOT_END)) {
            strippingMsg.setCode(PushMsgCode.WIRE_STRIPPING_NOT_END);
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
        WiringMsg wiringMsg = new WiringMsg();

        if (msg.equals(PushMsgCode.WIRING_READY)) {

            utils.putBoolean(RobotInit.WIRING_READY, true);
            wiringMsg.setMsg(RobotInit.WIRING_READY);
            wiringMsg.setCode(PushMsgCode.WIRING_READY);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_READY)) {

            utils.putBoolean(RobotInit.WIRING_READY, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_READY);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_READY);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_GRAB)) {

            utils.putBoolean(RobotInit.WIRING_GRAB, true);
            wiringMsg.setMsg(RobotInit.WIRING_GRAB);
            wiringMsg.setCode(PushMsgCode.WIRING_GRAB);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_GRAB)) {

            utils.putBoolean(RobotInit.WIRING_GRAB, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_GRAB);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_GRAB);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_ENTER)) {

            utils.putBoolean(RobotInit.WIRING_ENTER, true);
            wiringMsg.setMsg(RobotInit.WIRING_ENTER);
            wiringMsg.setCode(PushMsgCode.WIRING_ENTER);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_ENTER)) {

            utils.putBoolean(RobotInit.WIRING_ENTER, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_ENTER);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_ENTER);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_FIXED)) {

            utils.putBoolean(RobotInit.WIRING_FIXED, true);
            wiringMsg.setMsg(RobotInit.WIRING_FIXED);
            wiringMsg.setCode(PushMsgCode.WIRING_FIXED);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_FIXED)) {

            utils.putBoolean(RobotInit.WIRING_FIXED, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_FIXED);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_FIXED);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_TOOL_READY)) {

            utils.putBoolean(RobotInit.WIRING_TOOL_READY, true);
            wiringMsg.setMsg(RobotInit.WIRING_TOOL_READY);
            wiringMsg.setCode(PushMsgCode.WIRING_TOOL_READY);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_TOOL_READY)) {

            utils.putBoolean(RobotInit.WIRING_TOOL_READY, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_TOOL_READY);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_TOOL_READY);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_LINE_READY)) {

            utils.putBoolean(RobotInit.WIRING_LINE_READY, true);
            wiringMsg.setMsg(RobotInit.WIRING_LINE_READY);
            wiringMsg.setCode(PushMsgCode.WIRING_LINE_READY);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_LINE_READY)) {

            utils.putBoolean(RobotInit.WIRING_LINE_READY, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_LINE_READY);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_LINE_READY);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_TWIST)) {

            utils.putBoolean(RobotInit.WIRING_TWIST, true);
            wiringMsg.setMsg(RobotInit.WIRING_TWIST);
            wiringMsg.setCode(PushMsgCode.WIRING_TWIST);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_TWIST)) {

            utils.putBoolean(RobotInit.WIRING_TWIST, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_TWIST);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_TWIST);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_CLIP_UNLOCK)) {

            utils.putBoolean(RobotInit.WIRING_CLIP_UNLOCK, true);
            wiringMsg.setMsg(RobotInit.WIRING_CLIP_UNLOCK);
            wiringMsg.setCode(PushMsgCode.WIRING_CLIP_UNLOCK);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_CLIP_UNLOCK)) {

            utils.putBoolean(RobotInit.WIRING_CLIP_UNLOCK, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_CLIP_UNLOCK);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_CLIP_UNLOCK);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_SLEEVE_UNLOCK)) {

            utils.putBoolean(RobotInit.WIRING_SLEEVE_UNLOCK, true);
            wiringMsg.setMsg(RobotInit.WIRING_SLEEVE_UNLOCK);
            wiringMsg.setCode(PushMsgCode.WIRING_SLEEVE_UNLOCK);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRINGNOT_SLEEVE_UNLOCK)) {

            utils.putBoolean(RobotInit.WIRING_SLEEVE_UNLOCK, false);
            wiringMsg.setMsg(RobotInit.WIRINGNOT_SLEEVE_UNLOCK);
            wiringMsg.setCode(PushMsgCode.WIRINGNOT_SLEEVE_UNLOCK);
            wiringMsg.setHighlight(false);

        } else if (msg.equals(PushMsgCode.WIRING_END)) {

            utils.putBoolean(RobotInit.WIRING_END, true);
            wiringMsg.setMsg(RobotInit.WIRING_END);
            wiringMsg.setCode(PushMsgCode.WIRING_END);
            wiringMsg.setHighlight(true);

        } else if (msg.equals(PushMsgCode.WIRING_NOT_END)) {

            utils.putBoolean(RobotInit.WIRING_END, false);
            wiringMsg.setMsg(RobotInit.WIRING_NOT_END);
            wiringMsg.setCode(PushMsgCode.WIRING_NOT_END);
            wiringMsg.setHighlight(false);

        }

        BusUtils.postMessage(wiringMsg);
    }

    /**
     * 剪线界面
     *
     * @author Leo
     * created at 2019/4/20 11:11 PM
     */
    private static void onCutLine(String msg) {
        SPUtils utils = new SPUtils(RobotInit.CUT_LINE_ACTIVITY);
        CutLineMsg cutLineMsg = new CutLineMsg();
        if (msg.equals(PushMsgCode.CUT_INIT)){//就绪
            utils.putBoolean(RobotInit.CUT_INIT, true);
            cutLineMsg.setCode(PushMsgCode.CUT_INIT);

            cutLineMsg.setMsg(RobotInit.CUT_INIT);
            cutLineMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.CUT_NOT_INIT)){
            utils.putBoolean(RobotInit.CUT_INIT, false);
            cutLineMsg.setCode(PushMsgCode.CUT_NOT_INIT);

            cutLineMsg.setMsg(RobotInit.CUT_NOT_INIT);
            cutLineMsg.setHighlight(false);

        }else if (msg.equals(PushMsgCode.CUT_READY)) {//到位
            utils.putBoolean(RobotInit.CUT_READY, true);
            cutLineMsg.setCode(PushMsgCode.CUT_READY);

            cutLineMsg.setMsg(RobotInit.CUT_READY);
        } else if (msg.equals(PushMsgCode.CUT_NOT_READY)) {//未到位
            utils.putBoolean(RobotInit.CUT_READY, false);
            cutLineMsg.setCode(PushMsgCode.CUT_NOT_READY);

            cutLineMsg.setMsg(RobotInit.CUT_NOT_READY);
        } else if (msg.equals(PushMsgCode.CUT_START)) {//剪线开始
            utils.putBoolean(RobotInit.CUT_START, true);
            cutLineMsg.setCode(PushMsgCode.CUT_START);

            cutLineMsg.setMsg(RobotInit.CUT_START);
            cutLineMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.CUT_NOT_START)) {//剪线未开始
            utils.putBoolean(RobotInit.CUT_START, false);
            cutLineMsg.setCode(PushMsgCode.CUT_NOT_START);

            cutLineMsg.setMsg(RobotInit.CUT_NOT_START);
            cutLineMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.CUT_STOP)) {//剪线停止
            utils.putBoolean(RobotInit.CUT_STOP, true);
            cutLineMsg.setCode(PushMsgCode.CUT_STOP);

            cutLineMsg.setMsg(RobotInit.CUT_STOP);
            cutLineMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.CUT_NOT_STOP)) {//剪线未停止
            utils.putBoolean(RobotInit.CUT_STOP, false);
            cutLineMsg.setCode(PushMsgCode.CUT_NOT_STOP);

            cutLineMsg.setMsg(RobotInit.CUT_NOT_STOP);
            cutLineMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.CUT_RESET)) {//钳口复位
            utils.putBoolean(RobotInit.CUT_RESET, true);
            cutLineMsg.setCode(PushMsgCode.CUT_RESET);

            cutLineMsg.setMsg(RobotInit.CUT_RESET);
            cutLineMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.CUT_NOT_RESET)) {//钳口未复位
            utils.putBoolean(RobotInit.CUT_RESET, false);
            cutLineMsg.setCode(PushMsgCode.CUT_NOT_RESET);

            cutLineMsg.setMsg(RobotInit.CUT_NOT_RESET);
            cutLineMsg.setHighlight(false);
        } else if (msg.equals(PushMsgCode.CUT_END)) {//结束
            utils.putBoolean(RobotInit.CUT_END, true);
            cutLineMsg.setCode(PushMsgCode.CUT_END);

            cutLineMsg.setMsg(RobotInit.CUT_END);
            cutLineMsg.setHighlight(true);
        } else if (msg.equals(PushMsgCode.CUT_NOT_END)) {//未结束
            utils.putBoolean(RobotInit.CUT_END, false);
            cutLineMsg.setCode(PushMsgCode.CUT_NOT_END);

            cutLineMsg.setMsg(RobotInit.CUT_NOT_END);
            cutLineMsg.setHighlight(false);
        }

        BusUtils.postMessage(cutLineMsg);
    }



    /**
     * 连接成功
     *
     * @author Leo
     * created at 2019/4/27 10:08 PM
     */
    public static void onConnectSuccess(String type) {
        SocketStatusBean testBean = new SocketStatusBean();
        if (RobotInit.MASTER_CONTROL_NETTY.equals(type)) {//主控服务器连接成功
            testBean.setMsg("主控服务器连接成功  ");
            testBean.setType(RobotInit.MASTER_CONTROL_NETTY);
            testBean.setCode("1");
            String s = mGson.toJson(CommandUtils.getMasterControlBean());
            NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
            if (client != null) {
                client.sendMsgTest(s);
            }
        } else if (RobotInit.VISION_NETTY.equals(type)) {//视觉服务器连接成功
            testBean.setMsg("视觉服务器连接成功  ");
            testBean.setType(RobotInit.VISION_NETTY);
            testBean.setCode("1");
            String s = mGson.toJson(CommandUtils.getVisionBean());
            NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
            if (client != null) {
                client.sendMsgTest(s);
            }
        }
        if (!isFirst){
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                    BusUtils.postMessage(testBean);
                    isFirst = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }else {
            BusUtils.postMessage(testBean);
        }


    }

    /**
     * 连接失败
     *
     * @author Leo
     * created at 2019/4/27 10:08 PM
     */
    public static void onConnectErro(String type) {
        SocketStatusBean testBean = new SocketStatusBean();
        if (RobotInit.MASTER_CONTROL_NETTY.equals(type)) {//主控服务器连接失败
            testBean.setType(RobotInit.MASTER_CONTROL_NETTY);
            testBean.setMsg("主控服务器连接失败  ");
            testBean.setCode("0");
        } else if (RobotInit.VISION_NETTY.equals(type)) {//视觉服务器连接失败
            testBean.setType(RobotInit.VISION_NETTY);
            testBean.setMsg("视觉服务器连接失败  ");
            testBean.setCode("0");

        }
        BusUtils.postMessage(testBean);
    }

    /**
     * 接收服务器发送的消息
     *
     * @author Leo
     * created at 2019/4/27 10:14 PM
     */
    public static void onResultByType(String msg, String type) {
        if (RobotInit.MASTER_CONTROL_NETTY.equals(type)) {//主控服务器消息
            masterControlpushMsg(msg);
        } else if (RobotInit.VISION_NETTY.equals(type)) {//视觉服务器消息
            onVisionMsg(msg);
        } else if (RobotInit.MAIN_ARM_NETTY.equals(type)){//主臂
            onMainArm(msg);
        }else if (RobotInit.FLOW_ARM_NETTY.equals(type)){//从臂
            onFlowArm(msg);
        }
    }

    /**
    * 从臂数据
    *
    *@author Leo
    *created at 2019/6/14 9:21 PM
    */
    private static void onFlowArm(String msg) {
        if (msg.length()>1000) {//30003端口数据

        }else {//29999端口数据

        }
    }

    /**
    * 主臂数据
    *
    *@author Leo
    *created at 2019/6/14 9:21 PM
    */
    private static void onMainArm(String msg) {
        ArmBean armBean = new ArmBean();
        if (msg.length()>1000) {//30003端口数据
            armBean.setCode("0");
            armBean.setMsg(msg);
            BusUtils.postMessage(armBean);
        }else {//29999端口数据
            armBean.setCode("1");
            armBean.setMsg(msg);
            BusUtils.postMessage(armBean);
        }
    }

    /**
     * 视觉服务器消息接收
     *
     * @author Leo
     * created at 2019/4/27 10:18 PM
     */
    private static void onVisionMsg(String msg) {
//        SocketStatusBean testBean = new SocketStatusBean();
//        testBean.setType(RobotInit.VISION_NETTY);
//        testBean.setMsg("接收到视觉服务器发送的消息       ：" + msg);
//        BusUtils.postMessage(testBean);

        VisionMsg visionMsg = new VisionMsg();
        visionMsg.setMsg(msg);
        BusUtils.postMessage(visionMsg);
//        ToastUtils.showShortToast("视觉服务器消息： " + msg);
    }


}
