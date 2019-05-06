package com.leo.robot.ui.wiring;

import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.WiringMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.ui.wiring.choose.WiringChooseLocationActivity;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;
import com.unity3d.player.UnityPlayer;

import javax.inject.Inject;

import cree.mvp.util.data.SPUtils;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class WiringActivityPresenter extends RobotPresenter<WiringActivity, WiringActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;

    private boolean isClickble = false;
    private final NettyClient mClient;
    private UnityPlayer mUnityPlayer;

    @Inject
    public WiringActivityPresenter() {
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
                    mClient.sendMsgTest(CommandUtils.getMainArmShutdown());
                    mActivity.refreshLogRv("发送急停命令");
                }
                mActivity.updateScram(true);
                isScram = true;
            } else {//回复急停
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getMainArmResume());
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
        if (isClickble){
            if (mClient!=null){
                mClient.sendMsgTest(CommandUtils.getMainArmRecover());
                mActivity.refreshLogRv("发送一键回收命令");
            }
        }
    }

    /**
     * 开始、停止
     *
     * @author Leo
     * created at 2019/4/18 2:18 PM
     */
    public void startButton() {
        if (isClickble) {
            if (!isStart) { //开始
                mClient.sendMsgTest(CommandUtils.getMainArmStart());
                isStart = true;
                mActivity.updateStart(true);
                mActivity.refreshLogRv("发送开始命令");
            } else {//停止
                mClient.sendMsgTest(CommandUtils.getMainArmStop());
                isStart = false;
                mActivity.updateStart(false);

                mActivity.refreshLogRv("发送停止命令");
            }
        }
    }

    public void getPicButton() {

    }

    public String jugType(WiringMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.WIRING_READY)) {//就绪
            mActivity.updateReady(true);
            isClickble = true;
            mActivity.updateClickStatus(true);
            return "接线就绪";
        } else if (type.equals(RobotInit.WIRING_NOT_READY)) {
            mActivity.updateReady(false);
            isClickble = false;
            mActivity.updateClickStatus(false);
        } else if (type.equals(RobotInit.WIRING_GRAB)) {//引流抓取
            mActivity.updateGrab(true);
            return "接线引流抓取";
        } else if (type.equals(RobotInit.WIRING_NOT_GRAB)) {
            mActivity.updateGrab(false);
        } else if (type.equals(RobotInit.WIRING_ENTER)) {//引线进入
            mActivity.updateEnter(true);
            return "引线进入";
        } else if (type.equals(RobotInit.WIRING_NOT_ENTER)) {
            mActivity.updateEnter(false);
        } else if (type.equals(RobotInit.WIRING_FIXED)) {//引线固定
            mActivity.updateFixed(true);
            return "引线夹紧";
        } else if (type.equals(RobotInit.WIRING_NOT_FIXED)) {
            mActivity.updateFixed(false);
        } else if (type.equals(RobotInit.WIRING_TOOL_READY)) {//接线工具就位
            mActivity.updateToolReady(true);
            return "接线工具就位";
        } else if (type.equals(RobotInit.WIRING_NOT_TOOL_READY)) {
            mActivity.updateToolReady(false);
        } else if (type.equals(RobotInit.WIRING_LINE_READY)) {//并勾线夹就位
            mActivity.updateLineReady(true);
            return "并勾线夹就位";
        } else if (type.equals(RobotInit.WIRING_NOT_LINE_READY)) {
            mActivity.updateLineReady(false);
        } else if (type.equals(RobotInit.WIRING_TWIST)) {//拧断螺母
            mActivity.updateTwist(true);
            return "拧断螺母";
        } else if (type.equals(RobotInit.WIRING_NOT_TWIST)) {
            mActivity.updateTwist(false);
        } else if (type.equals(RobotInit.WIRING_CLIP_UNLOCK)) {//线夹解锁
            mActivity.updateClipUnlock(true);
            return "线夹解锁";
        } else if (type.equals(RobotInit.WIRING_NOT_CLIP_UNLOCK)) {
            mActivity.updateClipUnlock(false);
        } else if (type.equals(RobotInit.WIRING_SLEEVE_UNLOCK)) {//套筒解锁
            mActivity.updateSleeveUnlock(true);
            return "套筒解锁";
        } else if (type.equals(RobotInit.WIRINGNOT_SLEEVE_UNLOCK)) {
            mActivity.updateSleeveUnlock(false);

        } else if (type.equals(RobotInit.WIRING_END)) {//结束
            mActivity.updateEnd(true);
            return "接线结束";
        } else if (type.equals(RobotInit.WIRING_NOT_END)) {
            mActivity.updateEnd(false);
        }

        return null;
    }

    public void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRING_ACTIVITY);
        boolean isReady = utils.getBoolean(RobotInit.WIRING_READY);
        boolean isGrab = utils.getBoolean(RobotInit.WIRING_GRAB);
        boolean isEnter = utils.getBoolean(RobotInit.WIRING_ENTER);
        boolean isFixed = utils.getBoolean(RobotInit.WIRING_FIXED);
        boolean isToolReady = utils.getBoolean(RobotInit.WIRING_TOOL_READY);
        boolean isLineReady = utils.getBoolean(RobotInit.WIRING_LINE_READY);
        boolean isTwist = utils.getBoolean(RobotInit.WIRING_TWIST);
        boolean isClipUnlock = utils.getBoolean(RobotInit.WIRING_CLIP_UNLOCK);
        boolean isSleeveUnlock = utils.getBoolean(RobotInit.WIRING_SLEEVE_UNLOCK);
        boolean isEnd = utils.getBoolean(RobotInit.WIRING_END);
        isClickble = isReady;
        mActivity.updateReady(isReady);
        mActivity.updateGrab(isGrab);
        mActivity.updateEnter(isEnter);
        mActivity.updateFixed(isFixed);
        mActivity.updateToolReady(isToolReady);
        mActivity.updateLineReady(isLineReady);
        mActivity.updateTwist(isTwist);
        mActivity.updateClipUnlock(isClipUnlock);
        mActivity.updateSleeveUnlock(isSleeveUnlock);
        mActivity.updateEnd(isEnd);
    }

    public void identificationClick(RelativeLayout relativeLayout) {
        if (isClickble){
            mActivity.startActivity(new Intent(mActivity, WiringChooseLocationActivity.class));
            mActivity.finishActivity(relativeLayout);
        }
    }
    public void setUnityView(RelativeLayout unityView) {
        // TODO: 2019/5/6  直接使用父类的unityPlayer 不要自己去new一个
        mUnityPlayer = mActivity.getUnityPlayer();
        unityView.addView(mActivity.getUnityPlayer());
        mActivity.getUnityPlayer().requestFocus();
    }

    public void removeUnityView(RelativeLayout unityView) {
        if (unityView.getChildAt(0) != null) {
            unityView.removeView(mUnityPlayer);
        }
        mActivity.getUnityPlayer().requestFocus();
    }

}
