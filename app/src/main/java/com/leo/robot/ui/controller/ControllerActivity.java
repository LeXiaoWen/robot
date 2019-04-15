package com.leo.robot.ui.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;

/**
 * created by Leo on 2019/4/15 18 : 06
 */


public class ControllerActivity extends NettyActivity<ControllerActivityPresenter> {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerControllerActivityComponent.create().inject(this);
    }

    @Override
    protected void notifyData(String message) {

    }
}
