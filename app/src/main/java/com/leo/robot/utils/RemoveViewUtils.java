package com.leo.robot.utils;

import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

/**
 * created by Leo on 2019/7/10 20 : 30
 */


public class RemoveViewUtils {
    public static void removeView(RelativeLayout relativeLayout) {
        if (relativeLayout!=null){
            View view = null;
            for (int index = relativeLayout.getChildCount(); index > 0; index--) {
                view = relativeLayout.getChildAt(index);
                if (view != null && view instanceof WebView) {
                } else {
                    relativeLayout.removeView(view);
                }
            }
        }

    }
}
