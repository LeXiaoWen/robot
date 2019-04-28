package com.leo.robot.utils;

import com.google.gson.Gson;
import com.leo.robot.bean.AllMsg;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.TestBean;
import com.leo.robot.bean.VisionMsg;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.PushMsgCode;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;

import cree.mvp.util.bus.BusUtils;
import cree.mvp.util.data.SPUtils;

/**
 * 处理服务器推送消息
 * created by Leo on 2019/4/20 23 : 08
 */


public class ResultUtils {
    public static final int PUSH_MSG = 1, ERRO_MSG = 0;
    private static Gson mGson = new Gson();
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

//    public static void onResultByType(String tag){
//        if ("client1".equals(tag)){
//            final String s = mGson.toJson(CommandUtils.getPicBean1());
//            NettyManager.getInstance().getClientByTag(tag).sendMsgTest(s);
//        }else if ("client2".equals(tag)){
//            final String s = mGson.toJson(CommandUtils.getPicBean2());
//            NettyManager.getInstance().getClientByTag(tag).sendMsgTest(s);
//        }
//    }

    /**
     * 处理连接失败消息
     *
     * @author Leo
     * created at 2019/4/20 11:26 PM
     */
    private static void erroMsg(String msg) {
        ErroMsg erroMsg = new ErroMsg();
        erroMsg.setMsg(msg);
        BusUtils.postMessage(erroMsg);
    }

    /**
     * 主控服务器推送消息信息
     *
     * @param msg
     * @author Leo
     * created at 2019/4/20 11:25 PM
     */

    private static void masterControlpushMsg(String msg) {
        TestBean testBean = new TestBean();
        testBean.setMsg("接收到主控服务器发送的消息       ："+msg);
        BusUtils.postMessage(testBean);

        AllMsg allMsg = new AllMsg();
        String s = msg.substring(2, 4);
        if (PushMsgCode.WIRE_STRIPPING.equals(s)) {//剥线
            onWiringStripping(msg);
            allMsg.setMsg("剥线指令");
            allMsg.setCode(msg);
            BusUtils.postMessage(allMsg);
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
        if (msg.equals(PushMsgCode.WIRING_TOOL_READY)) {
            utils.putBoolean(RobotInit.WIRING_TOOL_READY, true);
        } else if (msg.equals(PushMsgCode.WIRING_NOT_TOOL_READY)) {
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
        if (msg.equals(PushMsgCode.CUT_READY)) {
            utils.putBoolean(RobotInit.CUT_READY, true);
        } else {
            utils.putBoolean(RobotInit.CUT_READY, false);
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


    /**
    * 连接成功
    *
    *@author Leo
    *created at 2019/4/27 10:08 PM
    */
    public static void onConnectSuccess(String type) {
        if (RobotInit.MASTER_CONTROL_NETTY.equals(type)){//主控服务器连接成功
            String s = mGson.toJson(CommandUtils.getMasterControlBean());
            NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
            client.sendMsgTest(s);
//            ToastUtils.showShortToast("主控服务器连接成功");
        }else if (RobotInit.VISION_NETTY.equals(type)){//视觉服务器连接成功
            String s = mGson.toJson(CommandUtils.getVisionBean());
            NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
            client.sendMsgTest(s);
//            ToastUtils.showShortToast("视觉服务器连接成功");
        }
    }

    /**
    * 连接失败
    *
    *@author Leo
    *created at 2019/4/27 10:08 PM
    */
    public static void onConnectErro(String type) {
        if (RobotInit.MASTER_CONTROL_NETTY.equals(type)){//主控服务器连接失败
            TestBean testBean = new TestBean();
            testBean.setMsg("主控服务器连接失败  ");
            BusUtils.postMessage(testBean);

//            ToastUtils.showShortToast("主控服务器连接失败");
        }else if (RobotInit.VISION_NETTY.equals(type)){//视觉服务器连接失败
//            ToastUtils.showShortToast("视觉服务器连接失败");

            TestBean testBean = new TestBean();
            testBean.setMsg("视觉服务器连接失败  ");
            BusUtils.postMessage(testBean);
        }
    }

    /**
    * 接收服务器发送的消息
    *
    *@author Leo
    *created at 2019/4/27 10:14 PM
    */
    public static void onResultByType(String msg, String type) {
        if (RobotInit.MASTER_CONTROL_NETTY.equals(type)){//主控服务器消息
            masterControlpushMsg(msg);
        }else if (RobotInit.VISION_NETTY.equals(type)){//视觉服务器消息
            onVisionMsg(msg);
        }
    }

    /**
    * 视觉服务器消息接收
    *
    *@author Leo
    *created at 2019/4/27 10:18 PM
    */
    private static void onVisionMsg(String msg) {
        TestBean testBean = new TestBean();
        testBean.setMsg("接收到视觉服务器发送的消息       ："+msg);
        BusUtils.postMessage(testBean);

        VisionMsg visionMsg = new VisionMsg();
        visionMsg.setMsg(msg);
        BusUtils.postMessage(visionMsg);
//        ToastUtils.showShortToast("视觉服务器消息： " + msg);
    }




}
