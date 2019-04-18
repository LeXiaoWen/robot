package com.leo.robot.ui.setting;

import android.support.v4.app.Fragment;

import javax.inject.Inject;

import cree.mvp.base.presenter.BasePresenter;

/**
 * created by Leo on 2019/4/18 21 : 33
 */


public class SettingActivityPresenter extends BasePresenter<SettingActivity,SettingActivityModel> {
    private Fragment mCurrentFragment = new Fragment();
    @Inject
    public SettingActivityPresenter() {
    }



}
