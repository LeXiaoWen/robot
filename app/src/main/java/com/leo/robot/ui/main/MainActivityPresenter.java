package com.leo.robot.ui.main;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.utils.TimeThread;
import cree.mvp.util.data.SPUtils;

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


    public void updatePower() {
        SPUtils spUtils = new SPUtils("power");
        String ownPower = spUtils.getString("ownPower", "--");
        String wire_stripper_ma = spUtils.getString("Wire_Stripper_Ma", "--");
        String connect_wire_ma = spUtils.getString("Connect_Wire_Ma", "--");
        String cut_wire_ma = spUtils.getString("Cut_Wire_Ma", "--");
        String hand_grab_ma = spUtils.getString("Hand_Grab_Ma", "--");
        mActivity.updatePw(ownPower,wire_stripper_ma,connect_wire_ma,cut_wire_ma,hand_grab_ma);
    }

}
