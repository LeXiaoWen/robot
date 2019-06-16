package com.leo.robot.ui.setting.fragment;

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
import com.just.agentweb.MiddlewareWebClientBase;
import com.leo.robot.R;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;

/**
 * 剥线设置
 * created by Leo on 2019/4/18 22 : 05
 */


public class WiringStrippingFragment extends Fragment implements View.OnClickListener {


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
    private NettyClient mClient;
    private MiddlewareWebClientBase mMiddleWareWebClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fg_wiring_stripping, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();

//        initUnity();
        AgentWebConfig.debug();
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    private void initUnity() {
//        mRl4.addView(mUnityPlayer);
//        mUnityPlayer.requestFocus();
    }

    private void initView(View view) {

        mRlMain = view.findViewById(R.id.rl_main);
        mRl1 = view.findViewById(R.id.rl1);
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

        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
        mIv6.setOnClickListener(this);
        mIv7.setOnClickListener(this);
        mIv8.setOnClickListener(this);


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
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        webViewOnDestroy();
        super.onDestroyView();
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_init:
//                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingInit());
//                break;
//            case R.id.btn_ready:
//                break;
//            case R.id.btn_clamping:
//                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingMainLineClamping());
//                break;
//            case R.id.btn_fixture_close:
//                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingClampClosure());
//                break;
//            case R.id.btn_peeling:
//                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingRotaryPeeling());
//                break;
//            case R.id.btn_cut_off:
//                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingCutOff());
//                break;
//            case R.id.btn_unlock:
//                NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY).sendMsg(CommandUtils.getStrippingUnlock());
//                break;
//        }
//    }


    /**
     * 位姿仿真画面
     *
     * @author Leo
     * created at 2019/4/27 5:27 PM
     * @param
     */
    private void initVideo4() {
        mAgentWeb4 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl4.findViewById(R.id.rl4), new RelativeLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.ARM_FLOW_CAMERA_UREL);

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
                .setAgentWebParent((RelativeLayout) mRl3, new RelativeLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.ARM_MAIN_CAMERA_UREL);

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
                .setAgentWebParent((RelativeLayout) mRl2, new RelativeLayout.LayoutParams(-1, -1))
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
                .setAgentWebParent((RelativeLayout) mRl1, new RelativeLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1).createAgentWeb()
                .ready()
                .go(UrlConstant.DRAIN_LINE_CAMERA_URL);

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
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.LINE_CAMERA_URL);

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
//        mAgentWeb4.getWebLifeCycle().onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingInit());
                }
                break;
            case R.id.iv2:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingMainLineClamping());
                }
                break;
            case R.id.iv3:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingClampClosure());
                }
                break;
            case R.id.iv4:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingRotaryPeeling());
                }
                break;
            case R.id.iv5:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingCutOff());
                }
                break;
            case R.id.iv6:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingUnlock());
                }
                break;
            case R.id.iv7:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingStatusReset());
                }
                break;
            case R.id.iv8:
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getStrippingStop());
                }
                break;
        }
    }


}
