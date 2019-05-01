package com.leo.robot.ui.setting.wiring_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.just.agentweb.AgentWeb;
import com.leo.robot.R;

/**
 * 接线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class WiringFragment extends Fragment implements View.OnClickListener {

    private boolean isPause;
    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb1;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;
    private RelativeLayout mRlMain;
    private RelativeLayout mRl1;
    private RelativeLayout mRl2;
    private RelativeLayout mRl3;
    private RelativeLayout mRl4;
    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;
    private ImageView mIv5;
    private ImageView mIv6;
    private ImageView mIv7;
    private ImageView mIv8;
    private ImageView mIv9;
    private ImageView mIv10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_wiring, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
        mRl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        mRl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        mRl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        mRl4 = (RelativeLayout) view.findViewById(R.id.rl4);

        mIv1 = (ImageView) view.findViewById(R.id.iv1);
        mIv2 = (ImageView) view.findViewById(R.id.iv2);
        mIv3 = (ImageView) view.findViewById(R.id.iv3);
        mIv4 = (ImageView) view.findViewById(R.id.iv4);
        mIv5 = (ImageView) view.findViewById(R.id.iv5);
        mIv6 = (ImageView) view.findViewById(R.id.iv6);
        mIv7 = (ImageView) view.findViewById(R.id.iv7);
        mIv8 = (ImageView) view.findViewById(R.id.iv8);
        mIv9 = (ImageView) view.findViewById(R.id.iv9);
        mIv10 = (ImageView) view.findViewById(R.id.iv10);

        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
        mIv6.setOnClickListener(this);
        mIv7.setOnClickListener(this);
        mIv8.setOnClickListener(this);
        mIv9.setOnClickListener(this);
        mIv10.setOnClickListener(this);

        initVideo();

    }

    private void initVideo() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用

        } else {
            //Fragment显示时调用
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

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_twist_start:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistStart());
//                break;
//            case R.id.btn_twist_flip:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistFlip());
//                break;
//            case R.id.btn_clip_unlock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getClipUnlock());
//                break;
//            case R.id.btn_sleeve_unlock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getSleeveUnlock());
//                break;
//            case R.id.btn_twist_end:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistEnd());
//                break;
//            case R.id.btn_twist_init:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistInit());
//                break;
//            case R.id.btn_clip_lock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getClipLock());
//                break;
//            case R.id.btn_sleeve_lock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getSleeveLock());
//                break;
//        }
//    }
}
