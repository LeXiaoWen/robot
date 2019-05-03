package com.leo.robot.ui.wire_stripping.choose;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

import cree.mvp.util.data.SPUtils;

/**
 * created by Leo on 2019/4/27 11 : 27
 */


public class WireStrippingChooseLocationActivityPresenter extends RobotPresenter<WireStrippingChooseLocationActivity, WireStrippingChooseLocationActivityModel> {

    private boolean isClickble = false;
    @Inject
    public WireStrippingChooseLocationActivityPresenter() {
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }

    public String jugType(WireStrippingMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.WIRE_STRIPPING_READY)) {//就绪
            mActivity.updateReady(true);
            isClickble = true;
            mActivity.updateClickStatus(true);
            return "剥线就绪";
        } else if (type.equals(RobotInit.WIRE_STRIPPING_NOT_READY)) {//未就绪
            mActivity.updateReady(false);
            isClickble = false;
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
        mActivity.updateEnd(isEnd);
    }
}
