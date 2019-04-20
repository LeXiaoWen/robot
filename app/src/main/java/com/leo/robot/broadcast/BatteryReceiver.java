package com.leo.robot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

/**
 * created by Leo on 2019/4/20 14 : 36
 */


public class BatteryReceiver extends BroadcastReceiver {
    private TextView pow;

    public BatteryReceiver(TextView pow) {
        this.pow = pow;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int current = intent.getExtras().getInt("level");// 获得当前电量
        int total = intent.getExtras().getInt("scale");// 获得总电量
        int percent = current * 100 / total;
        pow.setText(percent + "%");
    }
}
