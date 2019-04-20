package com.leo.robot.base;

import android.app.Activity;

import java.util.Stack;


/**
* activity管理类
*
*@author Leo
*created at 2019/4/14 6:02 PM
*/

public class ActivityManager {

    private static ActivityManager acitivityManager = new ActivityManager();
    public Stack<Activity> activities = new Stack<>();

    public static ActivityManager getInstance() {
        return acitivityManager;
    }

    public Stack<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activities.remove(activity);
    }
}
