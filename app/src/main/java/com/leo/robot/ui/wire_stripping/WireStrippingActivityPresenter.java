package com.leo.robot.ui.wire_stripping;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

import cree.mvp.util.data.SPUtils;

import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_CLAMPING;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_CLOSURE;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_CUT_OFF;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_END;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_INIT;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_PEELING;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_READY;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_TOOL_READY;
import static com.leo.robot.constant.RobotInit.WIRE_STRIPPING_UNLOCK;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivityPresenter extends RobotPresenter<WireStrippingActivity, WireStrippingActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;


    @Inject
    public WireStrippingActivityPresenter() {
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
        if (!isScram) { //急停
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmShutdown());
            mActivity.updateScramText("恢复急停");
            isScram = true;
        } else {//回复急停
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmResume());
            mActivity.updateScramText("急停");
            isScram = false;
        }
    }

    /**
     * 一键回收
     *
     * @author Leo
     * created at 2019/4/18 2:17 PM
     */
    public void revocerButton() {
        NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmRecover());
    }

    /**
     * 开始、停止
     *
     * @author Leo
     * created at 2019/4/18 2:18 PM
     */
    public void startButton() {
        if (!isStart) { //开始
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmStart());
            isStart = true;
            mActivity.updateStartText("停止");
        } else {//停止
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmStop());
            isStart = false;
            mActivity.updateStartText("开始");
        }
    }

    public void getPicButton() {

    }

    public void jugType(WireStrippingMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.WIRE_STRIPPING_READY)) {//就绪
            mActivity.updateReady(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_READY)) {//未就绪
            mActivity.updateReady(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_INIT)) { //初始化动作
            mActivity.updateInit(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_INIT)) { //未初始化动作
            mActivity.updateInit(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_TOOL_READY)) {//剥线工具就位
            mActivity.updateInPlace(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_TOOL_NOT_READY)) {
            mActivity.updateInPlace(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_CLAMPING)) {//主线夹紧
            mActivity.updateClamping(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_CLAMPING)) {
            mActivity.updateClamping(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_CLOSURE)) {//夹具闭合
            mActivity.updateClosure(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_CLOSURE)) {
            mActivity.updateClosure(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_PEELING)) {//旋转剥皮
            mActivity.updatePeeling(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_PEELING)) {
            mActivity.updatePeeling(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_CUT_OFF)) {//切断绝缘皮
            mActivity.updateCutOff(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_CUT_OFF)) {
            mActivity.updateCutOff(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_UNLOCK)) {//解锁
            mActivity.updateUnlock(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_UNLOCK)) {
            mActivity.updateUnlock(false);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_END)) {//结束
            mActivity.updateEnd(true);
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_END)) {
            mActivity.updateEnd(false);
        }
    }

    public void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY);
        boolean isReady = utils.getBoolean(WIRE_STRIPPING_READY);
        boolean isInit = utils.getBoolean(WIRE_STRIPPING_INIT);
        boolean isToolReady = utils.getBoolean(WIRE_STRIPPING_TOOL_READY);
        boolean isClamping = utils.getBoolean(WIRE_STRIPPING_CLAMPING);
        boolean isClosure = utils.getBoolean(WIRE_STRIPPING_CLOSURE);
        boolean isPeeling = utils.getBoolean(WIRE_STRIPPING_PEELING);
        boolean isCutOff = utils.getBoolean(WIRE_STRIPPING_CUT_OFF);
        boolean isUnlock = utils.getBoolean(WIRE_STRIPPING_UNLOCK);
        boolean isEnd = utils.getBoolean(WIRE_STRIPPING_END);

        mActivity.updateReady(isReady);
        mActivity.updateInit(isInit);
        mActivity.updateInPlace(isToolReady);
        mActivity.updateClamping(isClamping);
        mActivity.updateClosure(isClosure);
        mActivity.updatePeeling(isPeeling);
        mActivity.updateCutOff(isCutOff);
        mActivity.updateUnlock(isUnlock);
        mActivity.updateEnd(isEnd);
    }
}
