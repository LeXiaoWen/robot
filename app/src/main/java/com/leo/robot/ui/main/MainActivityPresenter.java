package com.leo.robot.ui.main;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/14 18 : 11
 */


public class MainActivityPresenter extends RobotPresenter<MainActivity, MainActivityModel> {
    @Inject
    public MainActivityPresenter() {
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }




}
