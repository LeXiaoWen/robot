package com.leo.robot.utils;

import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.LogUtils;
import com.just.agentweb.WebViewClient;

/**
 * created by Leo on 2019/7/9 21 : 43
 */


public class WebErrorUtils {
    private boolean isError = false;

    public WebErrorUtils() {
    }

    public void errorWeb(AgentWeb agentWeb, View errorView) {
        agentWeb.getWebCreator().getWebView().setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.setVisibility(View.GONE);
                if (errorView != null) {
                    errorView.setVisibility(View.VISIBLE);
                }
                isError = true;
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!isError) {
                    if (errorView != null) {
                        errorView.setVisibility(View.GONE);
                    }
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
        agentWeb.getWebCreator().getWebView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("WebErrorUtils", "点击了刷新！");
            }
        });
    }
}
