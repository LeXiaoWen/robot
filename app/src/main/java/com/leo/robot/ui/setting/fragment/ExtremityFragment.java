package com.leo.robot.ui.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.robot.R;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * 末端位姿设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ExtremityFragment extends Fragment {
    private boolean isPause;
    private MultiSampleVideo mVideoPlayer;
    private String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_extremity, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoPlayer = (MultiSampleVideo) view.findViewById(R.id.player);
//        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        String source1 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
//        String source2 = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
        mVideoPlayer.setUp(source2, true, "测试视频");
        mVideoPlayer.startPlayLogic();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
            GSYVideoManager.onPause();
            CustomManager.clearAllVideo();

        } else {
            //Fragment显示时调用
            GSYVideoManager.onResume();
        }
    }







    @Override
    public void onPause() {
        super.onPause();
        CustomManager.onPauseAll();
        isPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomManager.onResumeAll();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CustomManager.clearAllVideo();
    }
}
