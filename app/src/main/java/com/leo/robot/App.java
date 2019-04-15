package com.leo.robot;

import cree.mvp.base.activity.BaseApplication;
import cree.mvp.util.data.Utils;
import cree.mvp.util.ui.ToastUtils;

/**
 * created by Leo on 2019/4/12 14 : 08
 */


public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //吐司初始化
        ToastUtils.init(true);
    }

    @Override
    public boolean isOpenLeakCanary() {
        return false;
    }
}
