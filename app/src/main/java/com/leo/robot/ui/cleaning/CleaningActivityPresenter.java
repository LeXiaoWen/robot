package com.leo.robot.ui.cleaning;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/18 10 : 45
 */


public class CleaningActivityPresenter extends RobotPresenter<CleaningActivity,CleaningActivityModel> {
    @Inject
    public CleaningActivityPresenter() {
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
