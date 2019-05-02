package com.leo.robot.ui.setting.wiring_setting;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/20 15 : 24
 */


public class WiringSettingActivityPresenter extends RobotPresenter<WiringSettingActivity,WiringSettingActivityModel> {
    @Inject
    public WiringSettingActivityPresenter() {
    }

    public void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
