package com.leo.robot.base;

import android.widget.TextView;

import cree.mvp.base.model.BaseModel;
import cree.mvp.base.presenter.BasePresenter;

/**
 * created by Leo on 2019/4/17 23 : 51
 */


public abstract class RobotPresenter<A extends NettyActivity,M extends BaseModel> extends BasePresenter<A,M> {
    protected abstract void updateTime(TextView view);
}
