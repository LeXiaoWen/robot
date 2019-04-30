package com.leo.robot.ui.setting.cut_line_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.robot.R;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;

/**
 * 剪线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class CutLineFragment extends Fragment implements View.OnClickListener {

    private boolean isPause;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cut_line, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.btn_cut_start).setOnClickListener(this);
        view.findViewById(R.id.btn_reset).setOnClickListener(this);
        view.findViewById(R.id.btn_cut_stop).setOnClickListener(this);
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
