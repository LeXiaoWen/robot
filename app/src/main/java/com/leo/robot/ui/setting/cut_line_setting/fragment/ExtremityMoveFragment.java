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

/**
 * 末端位移设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ExtremityMoveFragment extends Fragment implements View.OnClickListener {
    private boolean isPause;
    private MultiSampleVideo mVideoPlayer;
    private int TAG = 0;
    public void setTAG(int TAG) {
        this.TAG = TAG;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_extremity_move, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoPlayer = (MultiSampleVideo) view.findViewById(R.id.player);
        view.findViewById(R.id.btn_rotate_left).setOnClickListener(this);
        view.findViewById(R.id.btn_rotate_right).setOnClickListener(this);
        view.findViewById(R.id.btn_left).setOnClickListener(this);
        view.findViewById(R.id.btn_right).setOnClickListener(this);
        view.findViewById(R.id.btn_up).setOnClickListener(this);
        view.findViewById(R.id.btn_down).setOnClickListener(this);
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
            case R.id.btn_rotate_left:
                rotateLeft();
                break;
            case R.id.btn_rotate_right:
                rotateRight();
                break;
            case R.id.btn_left:
                left();
                break;
            case R.id.btn_right:
                right();
                break;
            case R.id.btn_up:
                up();
                break;
            case R.id.btn_down:
                down();
                break;
        }
    }

    private void down() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmDisDown());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmDisDown());
        }
    }

    private void up() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmDisUp());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmDisUp());
        }
    }

    private void right() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmDisRight());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmDisRight());
        }
    }

    private void left() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmDisLeft());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmDisLeft());
        }
    }

    private void rotateRight() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmDisRotateRight());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmDisRotateRight());
        }
    }

    private void rotateLeft() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmDisRotateLeft());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmDisRotateLeft());
        }
    }
}
