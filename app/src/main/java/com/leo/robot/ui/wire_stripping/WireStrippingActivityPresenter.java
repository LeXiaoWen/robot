package com.leo.robot.ui.wire_stripping;

import android.content.Intent;
import android.widget.TextView;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.ui.setting.wiring_stripping_setting.WiringStrippingSettingActivity;
import com.leo.robot.ui.wire_stripping.choose.WireStrippingChooseLocationActivity;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;
import com.unity3d.player.UnityPlayer;
import cree.mvp.util.data.SPUtils;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivityPresenter extends RobotPresenter<WireStrippingActivity, WireStrippingActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;

    private boolean isClickble = false;
    private final NettyClient mClient;
    public boolean isSettingClickble = false;
    private UnityPlayer mUnityPlayer;


    @Inject
    public WireStrippingActivityPresenter() {
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }

    /**
     * 急停、恢复急停
     *
     * @author Leo
     * created at 2019/4/18 2:11 PM
     */
    public void scramButton() {
        if (isClickble) {
            if (!isScram) { //急停
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getFlowArmShutdown());
                    mActivity.refreshLogRv("发送急停命令");
                }
                mActivity.updateScram(true);
                isScram = true;
            } else {//回复急停
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getFlowArmResume());
                    mActivity.refreshLogRv("发送恢复急停命令");

                }
                mActivity.updateScram(false);
                isScram = false;
            }
        }
    }

    /**
     * 一键回收
     *
     * @author Leo
     * created at 2019/4/18 2:17 PM
     */
    public void revocerButton() {
        mClient.sendMsgTest(CommandUtils.getFlowArmRecover());
        mActivity.refreshLogRv("发送一键回收命令");
    }

    /**
     * 开始、停止
     *
     * @author Leo
     * created at 2019/4/18 2:18 PM
     */
    public void startButton() {

        if (!isStart) { //开始
            mClient.sendMsgTest(CommandUtils.getFlowArmStart());
            isStart = true;
            mActivity.updateStart(true);
            mActivity.refreshLogRv("发送开始命令");
        } else {//停止
            mClient.sendMsgTest(CommandUtils.getFlowArmStop());
            isStart = false;
            mActivity.updateStart(false);

            mActivity.refreshLogRv("发送停止命令");
        }
    }


    /**
     * 手动设置
     *
     * @param
     * @author Leo
     * created at 2019/4/27 2:22 AM
     */
    public void settingButton() {
        if (!isFastDoubleClick()) {
            mActivity.startActivity(new Intent(mActivity, WiringStrippingSettingActivity.class));
            mActivity.finish();
        }
    }

    public String jugType(WireStrippingMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.WIRE_STRIPPING_READY)) {//就绪
            mActivity.updateReady(true);
            isClickble = true;
            isSettingClickble = true;
            mActivity.updateClickStatus(true);
            return "剥线就绪";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_READY)) {//未就绪
            mActivity.updateReady(false);
            isClickble = false;
            isSettingClickble = false;
            mActivity.updateClickStatus(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_INIT)) { //初始化动作
            mActivity.updateInit(true);
            return "剥线初始化动作";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_INIT)) { //未初始化动作
            mActivity.updateInit(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_TOOL_READY)) {//剥线工具就位
            mActivity.updateInPlace(true);
            return "剥线工具就位";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_TOOL_NOT_READY)) {
            mActivity.updateInPlace(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_CLAMPING)) {//主线夹紧
            mActivity.updateClamping(true);
            return "主线夹紧";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_CLAMPING)) {
            mActivity.updateClamping(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_CLOSURE)) {//夹具闭合
            mActivity.updateClosure(true);
            return "夹具闭合";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_CLOSURE)) {
            mActivity.updateClosure(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_PEELING)) {//旋转剥皮
            mActivity.updatePeeling(true);
            return "旋转剥皮";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_PEELING)) {
            mActivity.updatePeeling(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_CUT_OFF)) {//切断绝缘皮
            mActivity.updateCutOff(true);
            return "切断绝缘皮";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_CUT_OFF)) {
            mActivity.updateCutOff(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_UNLOCK)) {//解锁
            mActivity.updateUnlock(true);
            return "解锁";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_UNLOCK)) {
            mActivity.updateUnlock(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_END)) {//结束
            mActivity.updateEnd(true);
            return "剥线结束";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_END)) {
            mActivity.updateEnd(false);
        }
        return null;
    }

    public void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY);
        boolean isReady = utils.getBoolean(RobotInit.WIRE_STRIPPING_READY);
        boolean isInit = utils.getBoolean(RobotInit.WIRE_STRIPPING_INIT);
        boolean isToolReady = utils.getBoolean(RobotInit.WIRE_STRIPPING_TOOL_READY);
        boolean isClamping = utils.getBoolean(RobotInit.WIRE_STRIPPING_CLAMPING);
        boolean isClosure = utils.getBoolean(RobotInit.WIRE_STRIPPING_CLOSURE);
        boolean isPeeling = utils.getBoolean(RobotInit.WIRE_STRIPPING_PEELING);
        boolean isCutOff = utils.getBoolean(RobotInit.WIRE_STRIPPING_CUT_OFF);
        boolean isUnlock = utils.getBoolean(RobotInit.WIRE_STRIPPING_UNLOCK);
        boolean isEnd = utils.getBoolean(RobotInit.WIRE_STRIPPING_END);
        isClickble = isReady;
        mActivity.updateReady(isReady);
        mActivity.updateInit(isInit);
        mActivity.updateInPlace(isToolReady);
        mActivity.updateClamping(isClamping);
        mActivity.updateClosure(isClosure);
        mActivity.updatePeeling(isPeeling);
        mActivity.updateCutOff(isCutOff);
        mActivity.updateUnlock(isUnlock);
//        mActivity.updateEnd(isEnd);
        mActivity.updateEndNormal(isEnd);
    }


    public void identificationButton() {
        mActivity.startActivity(new Intent(mActivity, WireStrippingChooseLocationActivity.class));
        mActivity.finish();
    }


    public void sendMsgToUnity() {
        //设置机械运动速度，类型float
        UnityPlayer.UnitySendMessage("MessageController", "SetMoveSpeed", "5.01");

        //设置主臂旋转，类型string  （将json以string的形式传参）
//        UnityPlayer.UnitySendMessage("MessageController","SetMaRobotValue","");
        //设置从臂旋转，类型string  （将json以string的形式传参）
//        UnityPlayer.UnitySendMessage("MessageController","SetFaRobotValue","");
    }
//    public void setUnityView(RelativeLayout unityView) {
//        // TODO: 2019/5/6  直接使用父类的unityPlayer 不要自己去new一个
//        mUnityPlayer = mActivity.getUnityPlayer();
//        unityView.addView(mActivity.getUnityPlayer());
//        mActivity.getUnityPlayer().requestFocus();
//    }
//
//    public void removeUnityView(RelativeLayout unityView) {
//        if (unityView.getChildAt(0) != null) {
//            unityView.removeView(mUnityPlayer);
//        }
//        mActivity.getUnityPlayer().requestFocus();
//    }

}
