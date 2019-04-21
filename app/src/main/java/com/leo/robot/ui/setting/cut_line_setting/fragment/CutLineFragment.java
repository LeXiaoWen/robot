package com.leo.robot.ui.setting.cut_line_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.robot.R;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import cree.mvp.util.ui.ToastUtils;

/**
 * 剪线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class CutLineFragment extends Fragment implements View.OnClickListener {

    private MultiSampleVideo mVideoPlayer;
    private boolean isPause;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cut_line, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoPlayer = (MultiSampleVideo) view.findViewById(R.id.player);
        view.findViewById(R.id.btn_cut_start).setOnClickListener(this);
        view.findViewById(R.id.btn_reset).setOnClickListener(this);
        view.findViewById(R.id.btn_cut_stop).setOnClickListener(this);
        initVideo();

    }

    private void initVideo() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cut_start:
                NettyClient.getInstance().sendMsg(CommandUtils.getCutToolStart());
                break;
            case R.id.btn_reset:
                NettyClient.getInstance().sendMsg(CommandUtils.getCutToolReset());
                break;
            case R.id.btn_cut_stop:
                NettyClient.getInstance().sendMsg(CommandUtils.getCutToolStop());
                break;
        }
    }
}
