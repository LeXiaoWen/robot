package com.leo.robot.ui.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.robot.R;

import cree.mvp.util.ui.ToastUtils;

/**
 * 末端位姿设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ExtremityFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_extremity, container, false);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
            ToastUtils.showShortToast("隐藏 末端位姿设置");
        } else {
            //Fragment显示时调用
            ToastUtils.showShortToast("显示 末端位姿设置");
        }
    }
}
