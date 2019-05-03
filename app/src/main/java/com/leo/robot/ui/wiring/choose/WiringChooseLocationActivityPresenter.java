package com.leo.robot.ui.wiring.choose;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

import cree.mvp.util.data.SPUtils;

/**
 * created by Leo on 2019/5/3 17 : 40
 */


public class WiringChooseLocationActivityPresenter extends RobotPresenter<WiringChooseLocationActivity,WiringChooseLocationActivityModel> {

    private boolean isClickble = false;
    private final NettyClient mClient;
    @Inject
    public WiringChooseLocationActivityPresenter() {
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);

    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
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
}
