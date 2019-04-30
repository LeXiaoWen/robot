package com.leo.robot.ui.setting.wiring_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.leo.robot.R;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;

import cree.mvp.util.ui.ToastUtils;

/**
 * 接线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class WiringFragment extends Fragment implements View.OnClickListener {

    private boolean isPause;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_wiring, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button btnTwistStart = (Button) view.findViewById(R.id.btn_twist_start);
        Button btnTwistFlip = (Button) view.findViewById(R.id.btn_twist_flip);
        Button btnClipUnlock = (Button) view.findViewById(R.id.btn_clip_unlock);
        Button btnSleeveUnlock = (Button) view.findViewById(R.id.btn_sleeve_unlock);
        Button btnTwistEnd = (Button) view.findViewById(R.id.btn_twist_end);
        Button btnTwistInit = (Button) view.findViewById(R.id.btn_twist_init);
        Button btnClipLock = (Button) view.findViewById(R.id.btn_clip_lock);
        Button btnSleeveLock = (Button) view.findViewById(R.id.btn_sleeve_lock);


        btnTwistStart.setOnClickListener(this);
        btnTwistFlip.setOnClickListener(this);
        btnClipUnlock.setOnClickListener(this);
        btnSleeveUnlock.setOnClickListener(this);
        btnTwistEnd.setOnClickListener(this);
        btnTwistInit.setOnClickListener(this);
        btnClipLock.setOnClickListener(this);
        btnSleeveLock.setOnClickListener(this);

        initVideo();

    }

    private void initVideo() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
            ToastUtils.showShortToast("隐藏 剪线设置");

        } else {
            //Fragment显示时调用
            ToastUtils.showShortToast("显示 剪线设置");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_twist_start:
                NettyClient.getInstance().sendMsg(CommandUtils.getTwistStart());
                break;
            case R.id.btn_twist_flip:
                NettyClient.getInstance().sendMsg(CommandUtils.getTwistFlip());
                break;
            case R.id.btn_clip_unlock:
                NettyClient.getInstance().sendMsg(CommandUtils.getClipUnlock());
                break;
            case R.id.btn_sleeve_unlock:
                NettyClient.getInstance().sendMsg(CommandUtils.getSleeveUnlock());
                break;
            case R.id.btn_twist_end:
                NettyClient.getInstance().sendMsg(CommandUtils.getTwistEnd());
                break;
            case R.id.btn_twist_init:
                NettyClient.getInstance().sendMsg(CommandUtils.getTwistInit());
                break;
            case R.id.btn_clip_lock:
                NettyClient.getInstance().sendMsg(CommandUtils.getClipLock());
                break;
            case R.id.btn_sleeve_lock:
                NettyClient.getInstance().sendMsg(CommandUtils.getSleeveLock());
                break;
        }
    }
}
