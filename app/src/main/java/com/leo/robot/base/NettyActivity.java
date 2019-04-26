package com.leo.robot.base;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.TextView;

import com.leo.robot.broadcast.BatteryReceiver;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.service.NettyService;

import java.lang.ref.WeakReference;

import cree.mvp.base.activity.BaseActivity;
import cree.mvp.base.presenter.BasePresenter;

/**
 * created by Leo on 2019/4/14 18 : 01
 */


public abstract class NettyActivity<T extends BasePresenter> extends BaseActivity<T> {
    public final static int MSG_FROM_SERVER = 0x1;
    public final static int MSG_NET_WORK_ERROR = 0x2;
    protected String TAG;
    protected MHandler handler;
    private BatteryReceiver mReceiver;


    private boolean mReceiverTag = false; //广播接受者标识位

    /**
     * 暴露handler给Service
     *
     * @return
     */
    public MHandler getHandler() {
        return handler;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        handler = new MHandler(this);
        TAG = this.getClass().getName();
    }

    protected void initBroadcast(TextView view) {
        if (!mReceiverTag) {
            mReceiverTag = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            mReceiver = new BatteryReceiver(view);
            registerReceiver(mReceiver, filter);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    public static class MHandler extends Handler {
        private WeakReference<NettyActivity> activity;

        public MHandler(NettyActivity activity) {
            this.activity = new WeakReference<NettyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activity == null || activity.get() == null) return;
            final NettyActivity nettyActivity = activity.get();
            switch (msg.what) {
                case NettyActivity.MSG_FROM_SERVER:
                    nettyActivity.notifyData((String) msg.obj);
                    break;
            }
        }
    }

    /**
     * 在主线程通知子类 刷新UI
     *
     * @param message
     */
    protected abstract void notifyData(String message);

    public void onUnBindReceiver() {
        if (mReceiverTag) {
            if (mReceiver != null) {
                try {
                    mReceiverTag = false;//设置广播标识位为false
                    unregisterReceiver(mReceiver);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("Receiver not registered")) {
                        // Ignore this exception. This is exactly what is desired
                    } else {
                        // unexpected, re-throw
                        throw e;
                    }
                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;

            case KeyEvent.KEYCODE_MENU:
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showNormalDialog(Activity activity) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(activity);
        normalDialog.setTitle("连接异常");
        normalDialog.setMessage("服务器连接失败，是否重连?");
        normalDialog.setPositiveButton("是",
                (dialog, which) -> {
                    NettyClient.getInstance().reconnect();
                    dialog.dismiss();
                });
        normalDialog.setNegativeButton("否",
                (dialog, which) -> {
                    NettyClient.getInstance().disconnect();
                    final Intent intent = new Intent(getApplication(), NettyService.class);
                    stopService(intent);
                    dialog.dismiss();
                });
        // 显示
        normalDialog.show();
    }

}
