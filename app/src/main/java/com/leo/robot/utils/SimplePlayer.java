package com.leo.robot.utils;

import android.content.Context;
import android.util.AttributeSet;

import com.leo.robot.R;

import moe.codeest.enviews.ENDownloadView;
import moe.codeest.enviews.ENPlayView;

/**
 * created by Leo on 2019/4/19 18 : 58
 */


public class SimplePlayer extends EmptyControlVideo {

    private ENDownloadView mLoad;
    private ENPlayView mStart;

    public SimplePlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SimplePlayer(Context context) {
        super(context);
    }

    public SimplePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_simple;
    }

    public ENDownloadView getLoading() {
        return mLoad;
    }

    public ENPlayView getStart() {
        return mStart;
    }
}
