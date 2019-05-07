package com.leo.robot;

import com.leo.robot.constant.RobotInit;
import com.tencent.bugly.crashreport.CrashReport;

import cree.mvp.base.activity.BaseApplication;
import cree.mvp.util.data.SPUtils;
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

        CrashReport.initCrashReport(getApplicationContext(), "1a9d059890", true);
        new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY).clear();
        new SPUtils(RobotInit.WIRING_ACTIVITY).clear();
        new SPUtils(RobotInit.CUT_LINE_ACTIVITY).clear();
    }

    @Override
    public boolean isOpenLeakCanary() {
        return false;
    }

}
