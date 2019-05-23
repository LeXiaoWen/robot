package com.leo.robot.base;

import android.widget.TextView;

import cree.mvp.base.model.BaseModel;
import cree.mvp.base.presenter.BasePresenter;
import cree.mvp.util.ui.ToastUtils;

/**
 * created by Leo on 2019/4/17 23 : 51
 */


public abstract class RobotPresenter<A extends NettyActivity,M extends BaseModel> extends BasePresenter<A,M> {
    protected abstract void updateTime(TextView view);
    /** 判断是否是快速点击 */
    private static long lastClickTime;


    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            ToastUtils.showShortToast("其他作业进行中，请稍等！");
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
