package com.leo.robot.utils;

import android.content.Context;
import android.webkit.WebView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;

/**
 * created by Leo on 2019/6/25 22 : 55
 */


public class ClearWebUtils {
    public static void clearVideo(AgentWeb agentWeb, Context context) {
        if (agentWeb!=null){
            WebView webView = agentWeb.getWebCreator().getWebView();
            webView.resumeTimers();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.setTag(null);
            webView.clearHistory();
            webView.destroy();
            webView = null;
            AgentWebConfig.clearDiskCache(context);
        }

    }
}
