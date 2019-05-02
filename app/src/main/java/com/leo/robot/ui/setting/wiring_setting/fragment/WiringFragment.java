package com.leo.robot.ui.setting.wiring_setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.R;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;

/**
 * 接线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class WiringFragment extends Fragment implements View.OnClickListener {

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
    private ImageView mIv3;
    private ImageView mIv4;
    private ImageView mIv5;
    private ImageView mIv6;
    private ImageView mIv7;
    private ImageView mIv8;
    private ImageView mIv9;
    private ImageView mIv10;
    private NettyClient mClient;
    private boolean isClicked = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_wiring, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
        mRl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        mRl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        mRl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        mRl4 = (RelativeLayout) view.findViewById(R.id.rl4);

        mIv1 = (ImageView) view.findViewById(R.id.iv1);
        mIv2 = (ImageView) view.findViewById(R.id.iv2);
        mIv3 = (ImageView) view.findViewById(R.id.iv3);
        mIv4 = (ImageView) view.findViewById(R.id.iv4);
        mIv5 = (ImageView) view.findViewById(R.id.iv5);
        mIv6 = (ImageView) view.findViewById(R.id.iv6);
        mIv7 = (ImageView) view.findViewById(R.id.iv7);
        mIv8 = (ImageView) view.findViewById(R.id.iv8);
        mIv9 = (ImageView) view.findViewById(R.id.iv9);
        mIv10 = (ImageView) view.findViewById(R.id.iv10);

        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
        mIv6.setOnClickListener(this);
        mIv7.setOnClickListener(this);
        mIv8.setOnClickListener(this);
        mIv9.setOnClickListener(this);
        mIv10.setOnClickListener(this);

        initVideo();
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    private void initVideo() {
        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
//            webViewOnResume();
            webViewOnDestroy();
            AgentWebConfig.clearDiskCache(this.getContext());
        } else {
            //Fragment显示时调用
//            webViewOnPause();
            initMainVideo();
            initVideo1();
            initVideo2();
            initVideo3();
            initVideo4();
        }
    }


    /**
     * 切换急停、恢复急停图标
     *
     * @author Leo
     * created at 2019/5/1 1:19 AM
     */
    public void updateScram(boolean b) {
        if (!b) {
            mIv2.setImageDrawable(getResources().getDrawable(R.drawable.jiexian_jiting_clicked));
        } else {
            mIv2.setImageDrawable(getResources().getDrawable(R.drawable.jiexian_jiechujiting_normal));
        }
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
        webViewOnDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:

                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getTwistOpen());
                }
                break;
            case R.id.iv2:
                if (!isClicked){//急停
                    isClicked = true;
                    updateScram(true);
                    if (mClient != null) {
                        mClient.sendMsgTest(CommandUtils.getMainArmShutdown());
                    }
                }else {//恢复急停
                    isClicked =false;
                    updateScram(false);
                    if (mClient != null) {
                        mClient.sendMsgTest(CommandUtils.getMainArmResume());
                    }
                }

                break;
            case R.id.iv3:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getTwistFeederClamp());
                }
                break;
            case R.id.iv4:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getTwistFeederClampReset());
                }
                break;
            case R.id.iv5:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getClipLock());
                }
                break;
            case R.id.iv6:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getTwistStart());
                }
                break;
            case R.id.iv7:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getTwistFlip());
                }
                break;
            case R.id.iv8:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getTwistInit());
                }
                break;
            case R.id.iv9:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getSleeveStop());
                }
                break;
            case R.id.iv10:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getSleeveLock());
                }
                break;

        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_twist_start:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistStart());
//                break;
//            case R.id.btn_twist_flip:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistFlip());
//                break;
//            case R.id.btn_clip_unlock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getClipUnlock());
//                break;
//            case R.id.btn_sleeve_unlock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getSleeveUnlock());
//                break;
//            case R.id.btn_twist_end:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistEnd());
//                break;
//            case R.id.btn_twist_init:
//                NettyClient.getInstance().sendMsg(CommandUtils.getTwistInit());
//                break;
//            case R.id.btn_clip_lock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getClipLock());
//                break;
//            case R.id.btn_sleeve_lock:
//                NettyClient.getInstance().sendMsg(CommandUtils.getSleeveLock());
//                break;
//        }
//    }

    /**
     * 位姿仿真画面
     *
     * @author Leo
     * created at 2019/4/27 5:27 PM
     */
    private void initVideo4() {
        mAgentWeb4 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl4, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb4.getWebCreator().getWebView());
    }

    /**
     * 机械臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo3() {
        mAgentWeb3 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl3, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb3.getWebCreator().getWebView());
    }

    /**
     * 引流线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo2() {
        mAgentWeb2 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl2, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb2.getWebCreator().getWebView());
    }

    /**
     * 行线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo1() {
        mAgentWeb1 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl1, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb1.getWebCreator().getWebView());
    }


    /**
     * 云台画面
     *
     * @param
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initMainVideo() {
        mAgentWebMain = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWebMain.getWebCreator().getWebView());


        //缩放
//        agentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
//        agentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
//        agentWeb.getAgentWebSettings().getWebSettings().setBuiltInZoomControls(true);

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

    private void webViewOnPause() {
        mAgentWebMain.getWebLifeCycle().onPause();
        mAgentWeb1.getWebLifeCycle().onPause();
        mAgentWeb2.getWebLifeCycle().onPause();
        mAgentWeb3.getWebLifeCycle().onPause();
        mAgentWeb4.getWebLifeCycle().onPause();

    }

    private void webViewOnResume() {
        mAgentWebMain.getWebLifeCycle().onResume();
        mAgentWeb1.getWebLifeCycle().onResume();
        mAgentWeb2.getWebLifeCycle().onResume();
        mAgentWeb3.getWebLifeCycle().onResume();
        mAgentWeb4.getWebLifeCycle().onResume();
    }

    private void webViewOnDestroy() {
        mAgentWebMain.getWebLifeCycle().onDestroy();
        mAgentWeb1.getWebLifeCycle().onDestroy();
        mAgentWeb2.getWebLifeCycle().onDestroy();
        mAgentWeb3.getWebLifeCycle().onDestroy();
        mAgentWeb4.getWebLifeCycle().onDestroy();
    }
}
