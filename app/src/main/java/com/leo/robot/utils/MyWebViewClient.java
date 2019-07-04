package com.leo.robot.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * created by Leo on 2019/6/29 21 : 07
 */


public class MyWebViewClient extends com.just.agentweb.WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        //do you  work
        Log.i("Info", "BaseWebActivity onPageStarted");
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        view.reload();
        super.onReceivedError(view, request, error);
    }
}
