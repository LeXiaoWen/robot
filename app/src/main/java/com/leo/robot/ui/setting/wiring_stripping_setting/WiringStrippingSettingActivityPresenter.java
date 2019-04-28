package com.leo.robot.ui.setting.wiring_stripping_setting;

import android.widget.TextView;

import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

import cree.mvp.base.presenter.BasePresenter;

/**
 * created by Leo on 2019/4/20 15 : 23
 */


public class WiringStrippingSettingActivityPresenter extends BasePresenter<WiringStrippingSettingActivity,WiringStrippingSettingActivityModel> {
    @Inject
    public WiringStrippingSettingActivityPresenter() {
    }



    public void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
