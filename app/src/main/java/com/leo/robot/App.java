package com.leo.robot;

import com.leo.robot.constant.RobotInit;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.tencent.bugly.Bugly;

import cree.mvp.base.activity.BaseApplication;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.Utils;
import cree.mvp.util.ui.ToastUtils;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

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
        //切换渲染模式
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        //16:9
//        GSYVideoType.SCREEN_TYPE_16_9 = 1;
        //静音
        GSYVideoManager.instance().setNeedMute(true);
        //EXOPlayer内核，支持格式更多
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        //切换渲染模式
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        Bugly.init(getApplicationContext(), "eb991c2317", false);
        new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY).clear();
        new SPUtils(RobotInit.WIRING_ACTIVITY).clear();
        new SPUtils(RobotInit.CUT_LINE_ACTIVITY).clear();

    }

    @Override
    public boolean isOpenLeakCanary() {
        return false;
    }
}
