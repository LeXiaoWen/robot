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
import com.leo.robot.constant.RobotInit;
import cree.mvp.base.activity.BaseActivity;
import cree.mvp.base.presenter.BasePresenter;
import cree.mvp.util.data.SPUtils;

import java.lang.ref.WeakReference;

/**
 * created by Leo on 2019/4/14 18 : 01
 */


public abstract class NettyActivity<T extends BasePresenter> extends BaseActivity<T> {
    public final static int MSG_FROM_SERVER = 0x1;
    public final static int MSG_NET_WORK_ERROR = 0x2;
    protected String TAG;
    protected MHandler handler;
    protected MasterHandler masterHandler;
    protected VisionHandler visionHandler;
    private BatteryReceiver mReceiver;

    public MasterHandler getMasterHandler() {
        return masterHandler;
    }

    public VisionHandler getVisionHandler() {
        return visionHandler;
    }

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
        masterHandler = new MasterHandler(this);
        visionHandler = new VisionHandler(this);
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
            if (msg.what == 0) {
                new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY).clear();
                new SPUtils(RobotInit.WIRING_ACTIVITY).clear();
                new SPUtils(RobotInit.CUT_LINE_ACTIVITY).clear();
            }
            nettyActivity.notifyData(msg.what, (String) msg.obj);

        }
    }

    public static class MasterHandler extends Handler {
        private WeakReference<NettyActivity> activity;

        public MasterHandler(NettyActivity activity) {
            this.activity = new WeakReference<NettyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activity == null || activity.get() == null) return;
            final NettyActivity nettyActivity = activity.get();
            if (msg.what == 0) {
                new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY).clear();
                new SPUtils(RobotInit.WIRING_ACTIVITY).clear();
                new SPUtils(RobotInit.CUT_LINE_ACTIVITY).clear();
            }
            nettyActivity.notifyMasterData(msg.what, (String) msg.obj);
        }
    }

    public static class VisionHandler extends Handler {
        private WeakReference<NettyActivity> activity;

        public VisionHandler(NettyActivity activity) {
            this.activity = new WeakReference<NettyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activity == null || activity.get() == null) return;
            final NettyActivity nettyActivity = activity.get();
            if (msg.what == 0) {
                new SPUtils(RobotInit.WIRE_STRIPPING_ACTIVITY).clear();
                new SPUtils(RobotInit.WIRING_ACTIVITY).clear();
                new SPUtils(RobotInit.CUT_LINE_ACTIVITY).clear();
            }
            nettyActivity.notifyVisionData(msg.what, (String) msg.obj);

        }
    }

    /**
     * 在主线程通知子类 刷新UI
     *
     * @param message
     */
    protected abstract void notifyData(int status, String message);
    protected abstract void notifyMasterData(int status, String message);
    protected abstract void notifyVisionData(int status, String message);


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

    public void showNormalDialog(Activity activity, String type) {
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
                    if (type.equals(RobotInit.MASTER_CONTROL_NETTY)) {

                    } else if (type.equals(RobotInit.VISION_NETTY)) {

                    }
//                    NettyClient.getInstance().reconnect();
                    dialog.dismiss();
                });
        normalDialog.setNegativeButton("否",
                (dialog, which) -> {
//                    NettyClient.getInstance().setReconnectNum(0);
//                    NettyClient.getInstance().disconnect();
//                    final Intent intent = new Intent(getApplication(), NettyService.class);
//                    stopService(intent);
                    dialog.dismiss();
                });
        // 显示
        normalDialog.show();
    }

}
