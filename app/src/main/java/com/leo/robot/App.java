package com.leo.robot;

import com.leo.robot.constant.RobotInit;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.tencent.bugly.Bugly;

import java.util.ArrayList;
import java.util.List;

import cree.mvp.base.activity.BaseApplication;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.Utils;
import cree.mvp.util.ui.ToastUtils;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

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

//        VideoOptionModel videoOptionModel =
//                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "videotoolbox", 1);
//        VideoOptionModel videoOptionModel1 =
//                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "allowed_media_types", "video");
//
//
//        List<VideoOptionModel> list = new ArrayList<>();
//        list.add(videoOptionModel);
//        list.add(videoOptionModel1);
//
//
//        GSYVideoManager.instance().setOptionModelList(list);
        init();
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

    private void init() {
        VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp");
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "allowed_media_types", "video"); //根据媒体类型来配置
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 20000);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "buffer_size", 1316);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1);  // 无限读
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 10240);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1);
        list.add(videoOptionModel);
        //  关闭播放器缓冲，这个必须关闭，否则会出现播放一段时间后，一直卡主，控制台打印 FFP_MSG_BUFFERING_START
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
        list.add(videoOptionModel);
        //软解码：1、打开，0、关闭
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "videotoolbox", 1);
        list.add(videoOptionModel);
        //硬解码：1、打开，0、关闭
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        list.add(videoOptionModel);
        GSYVideoManager.instance().setOptionModelList(list);
    }
}
