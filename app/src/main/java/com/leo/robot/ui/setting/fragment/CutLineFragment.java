package com.leo.robot.ui.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.R;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.ClearWebUtils;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.WebErrorUtils;

/**
 * 剪线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class CutLineFragment extends Fragment implements View.OnClickListener {

    private boolean isPause;
    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb1;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;
    private RelativeLayout mRlMain;
    private RelativeLayout mRl1;
    private RelativeLayout mRl2;
    private RelativeLayout mRl3;
    private RelativeLayout mRl4;
    private ImageView mIv1;
    private ImageView mIv2;
    private NettyClient mClient;

    private LinearLayout mLlErrorMain;
    private LinearLayout mLlError1;
    private LinearLayout mLlError2;
    private LinearLayout mLlError3;
    private LinearLayout mLlError4;

    private String videoMainTag = UrlConstant.URL[0];
    private String video1Tag = UrlConstant.URL[1];
    private String video2Tag = UrlConstant.URL[2];
    private String video3Tag = UrlConstant.URL[3];
    private String video4Tag = UrlConstant.URL[4];
    private String videoTag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cut_line, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
        mRl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        mRl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        mRl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        mRl4 = (RelativeLayout) view.findViewById(R.id.rl4);

        mLlErrorMain = (LinearLayout) view.findViewById(R.id.ll_error_main);
        mLlError1 = (LinearLayout) view.findViewById(R.id.ll_error1);
        mLlError2 = (LinearLayout) view.findViewById(R.id.ll_error2);
        mLlError3 = (LinearLayout) view.findViewById(R.id.ll_error3);
        mLlError4 = (LinearLayout) view.findViewById(R.id.ll_error4);

        mIv1 = (ImageView) view.findViewById(R.id.iv1);
        mIv2 = (ImageView) view.findViewById(R.id.iv2);


        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);

        initVideo();
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    private void initVideo() {
        initMainVideo(UrlConstant.URL[0]);
        initVideo1(UrlConstant.URL[1]);
        initVideo2(UrlConstant.URL[2]);
        initVideo3(UrlConstant.URL[3]);
        initVideo4(UrlConstant.URL[4]);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
            clearWeb();
            AgentWebConfig.clearDiskCache(this.getContext());
        } else {
            //Fragment显示时调用
           initVideo();
        }
    }

    private void clearWeb() {
        ClearWebUtils.clearVideo(mAgentWebMain, getContext());
        ClearWebUtils.clearVideo(mAgentWeb2, getContext());
        ClearWebUtils.clearVideo(mAgentWeb3, getContext());
        ClearWebUtils.clearVideo(mAgentWeb4, getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        clearWeb();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:
                if (mClient!=null){
                    mClient.sendMsgTest(CommandUtils.getCutToolReset());
                }
                break;
            case R.id.iv2:
                if (mClient!=null){
                    mClient.sendMsgTest(CommandUtils.getCutToolStart());
                }
                break;
        }
    }

    /**
     * 从臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:27 PM
     */
    private void initVideo4(String url) {
        mAgentWeb4 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl4, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        WebErrorUtils utils = new WebErrorUtils();
        utils.errorWeb(mAgentWeb4, mLlError4);
        initWebSetting(mAgentWeb4.getWebCreator().getWebView());
        mAgentWeb4.getWebCreator().getWebView().reload();
        mAgentWeb4.getWebCreator().getWebView().setOnTouchListener(changeOnTouchListener4);

    }

    /**
     * 主臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo3(String url) {
        mAgentWeb3 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl3, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb3, mLlError3);
        initWebSetting(mAgentWeb3.getWebCreator().getWebView());
        mAgentWeb3.getWebCreator().getWebView().reload();
        mAgentWeb3.getWebCreator().getWebView().setOnTouchListener(changeOnTouchListener3);

    }


    /**
     * 引流线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo2(String url) {
        mAgentWeb2 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl2, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb2, mLlError2);
        initWebSetting(mAgentWeb2.getWebCreator().getWebView());
        mAgentWeb2.getWebCreator().getWebView().reload();

        mAgentWeb2.getWebCreator().getWebView().setOnTouchListener(changeOnTouchListener2);

    }

    /**
     * 云台画面
     *
     * @author Leo
     * created at 2019/7/9 10:05 PM
     */
    private void initVideo1(String url) {
        mAgentWeb1 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb1, mLlError1);
        initWebSetting(mAgentWeb1.getWebCreator().getWebView());
        mAgentWeb1.getWebCreator().getWebView().reload();
        mAgentWeb1.getWebCreator().getWebView().setOnTouchListener(changeOnTouchListener1);
    }


    private View.OnTouchListener changeOnTouchListener1 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ClearWebUtils.clearVideo(mAgentWebMain, getContext());
                ClearWebUtils.clearVideo(mAgentWeb1, getContext());
                initMainVideo(video1Tag);
                initVideo1(videoMainTag);
                videoTag = videoMainTag;
                videoMainTag = video1Tag;
                video1Tag = videoTag;
            }
            return false;
        }
    };
    private View.OnTouchListener changeOnTouchListener2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP){
                ClearWebUtils.clearVideo(mAgentWebMain, getContext());
                ClearWebUtils.clearVideo(mAgentWeb2, getContext());
                initMainVideo(video2Tag);
                initVideo2(videoMainTag);
                videoTag = videoMainTag;
                videoMainTag = video2Tag;
                video2Tag = videoTag;
            }

            return false;
        }
    };
    private View.OnTouchListener changeOnTouchListener3 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ClearWebUtils.clearVideo(mAgentWebMain, getContext());
                ClearWebUtils.clearVideo(mAgentWeb3, getContext());
                initMainVideo(video3Tag);
                initVideo3(videoMainTag);
                videoTag = videoMainTag;
                videoMainTag = video3Tag;
                video3Tag = videoTag;
            }
            return false;
        }
    };
    private View.OnTouchListener changeOnTouchListener4 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP){
                ClearWebUtils.clearVideo(mAgentWebMain, getContext());
                ClearWebUtils.clearVideo(mAgentWeb1, getContext());
                initMainVideo(video4Tag);
                initVideo4(videoMainTag);
                videoTag = videoMainTag;
                videoMainTag = video4Tag;
                video4Tag = videoTag;
            }

            return false;
        }
    };



    /**
     * 行线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initMainVideo(String url) {
        mAgentWebMain = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWebMain, mLlErrorMain);
        initWebSetting(mAgentWebMain.getWebCreator().getWebView());
        mAgentWebMain.getWebCreator().getWebView().reload();
    }

    /**
     * 设置webView自适应屏幕，取消滚动条
     *
     * @author Leo
     * created at 2019/4/27 5:19 PM
     */
    private void initWebSetting(WebView view) {
        //取消滚动条
        view.setHorizontalScrollBarEnabled(false);
        view.setVerticalScrollBarEnabled(false);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setLoadWithOverviewMode(true);
        //缩放
//        agentWeb.getAgentWebSettings().getWebSettings().setBuiltInZoomControls(true);
    }


}
