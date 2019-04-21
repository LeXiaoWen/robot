package com.leo.robot.ui.setting.cut_line_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
 * 机械臂设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ArmFragment extends Fragment {
    private boolean isPause;
    private MultiSampleVideo mVideoPlayer;
    private String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private int TAG = 0;


    public void setTAG(int TAG) {
        this.TAG = TAG;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_arm, container, false);
        initView(view);
        return view;
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

    private void initView(View view) {
        mVideoPlayer = (MultiSampleVideo) view.findViewById(R.id.player);

        view.findViewById(R.id.ibtn_wrist1_dec).setOnTouchListener(mOnDownClickListener);
        view.findViewById(R.id.ibtn_wrist1_add).setOnTouchListener(mOnDownClickListener);

        view.findViewById(R.id.ibtn_wrist2_dec).setOnTouchListener(mOnDownClickListener);
        view.findViewById(R.id.ibtn_wrist2_add).setOnTouchListener(mOnDownClickListener);

        view.findViewById(R.id.ibtn_wrist3_dec).setOnTouchListener(mOnDownClickListener);
        view.findViewById(R.id.ibtn_wrist3_add).setOnTouchListener(mOnDownClickListener);

        view.findViewById(R.id.ibtn_shoulder_dec).setOnTouchListener(mOnDownClickListener);
        view.findViewById(R.id.ibtn_shoulder_add).setOnTouchListener(mOnDownClickListener);

        view.findViewById(R.id.ibtn_pedestal_dec).setOnTouchListener(mOnDownClickListener);
        view.findViewById(R.id.ibtn_pedestal_add).setOnTouchListener(mOnDownClickListener);

        view.findViewById(R.id.ibtn_elbow_dec).setOnTouchListener(mOnDownClickListener);
        view.findViewById(R.id.ibtn_elbow_add).setOnTouchListener(mOnDownClickListener);

        initVideo();
    }

    private void initVideo() {
        mVideoPlayer.setUp(UrlConstant.ARM_CAMERA_UREL, true, "机械臂相机");
        mVideoPlayer.startPlayLogic();
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


    private void downClickPedestalDec() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmPedestalDec());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmPedestalDec());
        }
    }

    private void downClickPedestalAdd() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmPedestalAdd());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmPedestalAdd());
        }
    }

    private void downClickShoulderDec() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmShoulderDec());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmShoulderDec());
        }
    }

    private void downClickShoulderAdd() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmShoulderAdd());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmShoulderAdd());
        }
    }

    private void downClickElbowAdd() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmElbowAdd());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmElbowAdd());
        }
    }

    private void downClickElbowDec() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmElbowDec());
        } else {//从臂
                NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmElbowDec());
        }
    }

    private void downClickWrist3Add() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmWrist3Add());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmWrist3Add());
        }
    }

    private void downClickWrist3Dec() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmWrist3Dec());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmWrist3Dec());
        }
    }

    private void downClickWrist2Add() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmWrist2Add());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmWrist2Add());
        }
    }

    private void downClickWrist2Dec() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmWrist2Dec());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmWrist2Dec());
        }
    }

    private void downClickWrist1Add() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmWrist1Add());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmWrist1Add());
        }
    }

    private void downClickWrist1Dec() {
        if (TAG == 1) {//主臂
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmWrist1Dec());
        } else {//从臂
            NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmWrist1Dec());
        }
    }



    private View.OnTouchListener mOnDownClickListener = (v, event) -> {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) { //按下
            switch (v.getId()) {
                case R.id.ibtn_wrist1_dec:
                    downClickWrist1Dec();
                    break;
                case R.id.ibtn_wrist1_add:
                    downClickWrist1Add();
                    break;
                case R.id.ibtn_wrist2_dec:
                    downClickWrist2Dec();
                    break;
                case R.id.ibtn_wrist2_add:
                    downClickWrist2Add();
                    break;
                case R.id.ibtn_wrist3_dec:
                    downClickWrist3Dec();
                    break;
                case R.id.ibtn_wrist3_add:
                    downClickWrist3Add();
                    break;
                case R.id.ibtn_elbow_dec:
                    downClickElbowDec();
                    break;
                case R.id.ibtn_elbow_add:
                    downClickElbowAdd();
                    break;
                case R.id.ibtn_shoulder_dec:
                    downClickShoulderDec();
                    break;
                case R.id.ibtn_shoulder_add:
                    downClickShoulderAdd();
                    break;
                case R.id.ibtn_pedestal_dec:
                    downClickPedestalDec();
                    break;
                case R.id.ibtn_pedestal_add:
                    downClickPedestalAdd();
                    break;
            }
        } else if (action == MotionEvent.ACTION_UP) {//松开
            if (TAG == 1) {//主臂
                mainArmUp();
            } else {//从臂
                flowArmUp();
            }
        }
        return false;
    };

    private void flowArmUp() {
        NettyClient.getInstance().sendMsg(CommandUtils.getFlowArmActionStop());
    }

    private void mainArmUp() {
        NettyClient.getInstance().sendMsg(CommandUtils.getMainArmActionStop());
    }
}
