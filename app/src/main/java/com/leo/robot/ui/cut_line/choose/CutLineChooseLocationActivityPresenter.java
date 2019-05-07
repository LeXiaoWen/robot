package com.leo.robot.ui.cut_line.choose;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

import cree.mvp.util.data.SPUtils;

/**
 * created by Leo on 2019/5/3 17 : 40
 */


public class CutLineChooseLocationActivityPresenter extends RobotPresenter<CutLineChooseLocationActivity,CutLineChooseLocationActivityModel> {

    private boolean isClickble = false;
    @Inject
    public CutLineChooseLocationActivityPresenter() {
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }

    public void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.CUT_LINE_ACTIVITY);
        boolean isInit = utils.getBoolean(RobotInit.CUT_INIT);
        boolean isReady = utils.getBoolean(RobotInit.CUT_READY);
        boolean isStart = utils.getBoolean(RobotInit.CUT_START);
        boolean isStop = utils.getBoolean(RobotInit.CUT_STOP);
        boolean isReset = utils.getBoolean(RobotInit.CUT_RESET);
        boolean isEnd = utils.getBoolean(RobotInit.CUT_END);
        isClickble = isInit;

        mActivity.updateInit(isInit);
        mActivity.updateReady(isReady);
        mActivity.updateCutStart(isStart);
        mActivity.updateCutStop(isStop);
        mActivity.updateCutReset(isReset);
        mActivity.updateEnd(isEnd);

    }
}
