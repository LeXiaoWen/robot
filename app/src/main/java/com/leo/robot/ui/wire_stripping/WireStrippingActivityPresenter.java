package com.leo.robot.ui.wire_stripping;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivityPresenter extends RobotPresenter<WireStrippingActivity,WireStrippingActivityModel> {
    @Inject
    public WireStrippingActivityPresenter() {
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
