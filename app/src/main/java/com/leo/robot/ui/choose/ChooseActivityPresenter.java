package com.leo.robot.ui.choose;

import android.widget.TextView;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/6/13 23 : 26
 */


public class ChooseActivityPresenter extends RobotPresenter<ChooseActivity, ChooseActivityModel> {

    @Inject
    public ChooseActivityPresenter() {
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
