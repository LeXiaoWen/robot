package com.leo.robot.ui.setting.cut_line_setting.fragment;

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
 * 末端位姿设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ExtremityFragment extends Fragment implements View.OnClickListener {
    private boolean isPause;
    private int TAG = 0;

    public void setTAG(int TAG) {
        this.TAG = TAG;
    }

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

    private NettyClient mClient;
    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;
    private ImageView mIv5;
    private ImageView mIv6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_extremity, container, false);
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
        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
        mIv6.setOnClickListener(this);
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);


    }

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


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
//            webViewOnResume();
            webViewOnPause();
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
//        webViewOnPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
//        webViewOnResume();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:
                up();
                break;
            case R.id.iv2:
                down();
                break;
            case R.id.iv3:
                left();
                break;
            case R.id.iv4:
                right();
                break;
            case R.id.iv5:
                forward();
                break;
            case R.id.iv6:
                backward();
                break;
        }
    }

    private void down() {
        if (mClient != null) {
            if (TAG == 1) {//主臂
                mClient.sendMsgTest(CommandUtils.getMainArmPosDown());
            } else {//从臂
                mClient.sendMsgTest(CommandUtils.getFlowArmPosDown());
            }
        }
    }

    private void up() {
        if (mClient != null) {
            if (TAG == 1) {//主臂
                mClient.sendMsgTest(CommandUtils.getMainArmPosUp());
            } else {//从臂
                mClient.sendMsgTest(CommandUtils.getFlowArmPosUp());
            }
        }

    }

    private void right() {
        if (mClient != null) {
            if (TAG == 1) {//主臂
                mClient.sendMsgTest(CommandUtils.getMainArmPosRight());
            } else {//从臂
                mClient.sendMsgTest(CommandUtils.getFlowArmPosRight());
            }
        }

    }

    private void left() {
        if (mClient != null) {
            if (TAG == 1) {//主臂
                mClient.sendMsgTest(CommandUtils.getMainArmPosLeft());
            } else {//从臂
                mClient.sendMsgTest(CommandUtils.getFlowArmPosLeft());
            }
        }

    }

    private void backward() {
        if (mClient != null) {
            if (TAG == 1) {//主臂
                mClient.sendMsgTest(CommandUtils.getMainArmPosRotateRight());
            } else {//从臂
                mClient.sendMsgTest(CommandUtils.getFlowArmPosRotateRight());
            }
        }

    }

    private void forward() {
        if (mClient != null) {
            if (TAG == 1) {//主臂
                mClient.sendMsgTest(CommandUtils.getMainArmPosRotateLeft());
            } else {//从臂
                mClient.sendMsgTest(CommandUtils.getFlowArmPosRotateLeft());
            }
        }

    }
}
