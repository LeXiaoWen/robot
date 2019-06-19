package com.leo.robot.ui.setting.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * created by Leo on 2019/6/20 01 : 00
 */


public abstract class BaseFragment extends Fragment {

    protected Timer mTimer;
    protected TimerTask mTimerTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        mTimer.schedule(mTimerTask, 1000, 200);//延时1s，每隔500毫秒执行一次run方法
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
               updateData();
            }
            super.handleMessage(msg);
        }
    };

    public abstract void updateData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }

}
