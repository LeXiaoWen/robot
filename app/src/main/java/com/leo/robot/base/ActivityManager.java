package com.leo.robot.base;

import java.util.Stack;


/**
* activity管理类
*
*@author Leo
*created at 2019/4/14 6:02 PM
*/

public class ActivityManager {

    private static ActivityManager acitivityManager = new ActivityManager();
    public Stack<NettyActivity> activities = new Stack<>();

    public static ActivityManager getInstance() {
        return acitivityManager;
    }

    public Stack<NettyActivity> getActivities() {
        return activities;
    }

    public void addActivity(NettyActivity activity) {
        if (activity == null) {
            return;
        }
        activities.add(activity);
    }

    public void removeActivity(NettyActivity activity) {
        if (activity == null) {
            return;
        }
        activities.remove(activity);
    }
}
