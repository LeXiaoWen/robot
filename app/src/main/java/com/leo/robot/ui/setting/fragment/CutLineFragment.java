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
 * 剪线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class CutLineFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cut_line, container, false);
        return view;
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
}
