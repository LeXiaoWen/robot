package com.leo.robot.ui.setting.cut_line_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.robot.R;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * 末端位移设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ExtremityMoveFragment extends Fragment {
    private boolean isPause;
    private MultiSampleVideo mVideoPlayer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_extremity_move, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoPlayer = (MultiSampleVideo) view.findViewById(R.id.player);
        mVideoPlayer.setUp(UrlConstant.URL_TEST, true, "测试视频");
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
