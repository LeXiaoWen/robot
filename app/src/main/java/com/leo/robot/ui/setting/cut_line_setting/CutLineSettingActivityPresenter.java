package com.leo.robot.ui.setting.cut_line_setting;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/18 21 : 33
 */


public class CutLineSettingActivityPresenter extends RobotPresenter<CutLineSettingActivity, CutLineSettingActivityModel> {
    private Fragment mCurrentFragment = new Fragment();
    @Inject
    public CutLineSettingActivityPresenter() {
    }


    public void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
