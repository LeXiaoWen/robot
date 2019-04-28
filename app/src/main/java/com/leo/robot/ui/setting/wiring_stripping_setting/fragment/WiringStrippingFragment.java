package com.leo.robot.ui.setting.wiring_stripping_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.leo.robot.R;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;
import com.leo.robot.utils.NettyManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import cree.mvp.util.ui.ToastUtils;

/**
 * 剥线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class WiringStrippingFragment extends Fragment implements View.OnClickListener {

    private MultiSampleVideo mVideoPlayer;
    private boolean isPause;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_wiring_stripping, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoPlayer = (MultiSampleVideo) view.findViewById(R.id.player);
        Button btnInit = (Button) view.findViewById(R.id.btn_init);
        Button btnReady = (Button) view.findViewById(R.id.btn_ready);
        Button btnClamping = (Button) view.findViewById(R.id.btn_clamping);
        Button btnFixtureClose = (Button) view.findViewById(R.id.btn_fixture_close);
        Button btnPeeling = (Button) view.findViewById(R.id.btn_peeling);
        Button btnCutOff = (Button) view.findViewById(R.id.btn_cut_off);
        Button btnUnlock = (Button) view.findViewById(R.id.btn_unlock);

        btnInit.setOnClickListener(this);
        btnReady.setOnClickListener(this);
        btnClamping.setOnClickListener(this);
        btnFixtureClose.setOnClickListener(this);
        btnPeeling.setOnClickListener(this);
        btnCutOff.setOnClickListener(this);
        btnUnlock.setOnClickListener(this);

        initVideo();

    }

    private void initVideo() {
        mVideoPlayer.setUp("", true, "测试视频");
        mVideoPlayer.startPlayLogic();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
            ToastUtils.showShortToast("隐藏 剪线设置");
            GSYVideoManager.onPause();
            CustomManager.clearAllVideo();

        } else {
            //Fragment显示时调用
            GSYVideoManager.onResume();
            ToastUtils.showShortToast("显示 剪线设置");
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
            case R.id.btn_init:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingInit());
                break;
            case R.id.btn_ready:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingToolReady());
                break;
            case R.id.btn_clamping:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingMainLineClamping());
                break;
            case R.id.btn_fixture_close:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingClampClosure());
                break;
            case R.id.btn_peeling:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingRotaryPeeling());
                break;
            case R.id.btn_cut_off:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingCutOff());
                break;
            case R.id.btn_unlock:
                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingUnlock());
                break;
        }
    }
}
