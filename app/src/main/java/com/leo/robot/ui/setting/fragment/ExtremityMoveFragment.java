package com.leo.robot.ui.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.URConstants;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.arm.ArmNettyClient;
import com.leo.robot.utils.NettyManager;
import cree.mvp.util.data.StringUtils;

/**
 * 末端位移设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ExtremityMoveFragment extends BaseFragment {
    private boolean isPause;
    private int TAG = 0;
    private NettyClient mClient;
    private ImageButton mIv1;
    private ImageButton mIv2;
    private ImageButton mIv3;
    private ImageButton mIv4;
    private ImageButton mIv5;
    private ImageButton mIv6;

    private TextView mTv2;
    private TextView mTv4;
    private TextView mTv6;
    private TextView mTv8;
    private TextView mTv10;
    private TextView mTv12;
    private TextView mTvToolName;
    private TextView mTvMode;
    private TextView mTvSafeMode;
    private TextView mTvStatus;

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

    private ArmNettyClient mMainArmNettyClient;
    private ArmNettyClient mFlowArmNettyClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_extremity_move, container, false);
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
        initArmNetty();
        if (TAG == 1) {
            mTvToolName.setText("主臂末端工具中心点（TCP)");
        } else {
            mTvToolName.setText("从臂末端工具中心点（TCP)");
        }
    }

    @Override
    public void updateData() {
        initTCP();
        initMode();
        initSafeMode();
        initArmStatus();
    }

    /**
     * 软件状态
     *
     * @author Leo
     * created at 2019/6/20 9:55 PM
     */
    private void initArmStatus() {
        if (TAG == 1) {
            String status = JNIUtils.ReadURparam(URConstants.Program_State, URConstants.Marm);
            showStatus(status);
        } else {
            String status = JNIUtils.ReadURparam(URConstants.Program_State, URConstants.Farm);
            showStatus(status);
        }
    }


    private void showStatus(String status) {
        if (!StringUtils.isEmpty(status)) {
            int i = (int)Double.parseDouble(status);

            switch (i) {
                case 1:
                    mTvStatus.setText(URConstants.idle);
                    break;
                case 2:
                    mTvStatus.setText(URConstants.running);
                    break;
                case 4:
                    mTvStatus.setText(URConstants.paused);
                    break;
            }
        }
    }

    /**
     * 机器安全模式
     *
     * @author Leo
     * created at 2019/6/20 9:54 PM
     */
    private void initSafeMode() {
        if (TAG == 1) {
            String safeMode = JNIUtils.ReadURparam(URConstants.Safe_Mod, URConstants.Marm);
            showSafeMode(safeMode);
        } else {
            String safeMode = JNIUtils.ReadURparam(URConstants.Safe_Mod, URConstants.Farm);
            showSafeMode(safeMode);
        }
    }

    private void showSafeMode(String safeMode) {
        if (!StringUtils.isEmpty(safeMode)) {
            int i = (int)Double.parseDouble(safeMode);
            switch (i) {
                case 1:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_NORMAL);
                    break;
                case 2:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_REDUCED);
                    break;
                case 3:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_PROTECTIVE_STOP);
                    break;
                case 4:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_RECOVERY);
                    break;
                case 5:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_SAFEGUARD_STOP);
                    break;
                case 6:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_SYSTEM_EMERGENCY_STOP);
                    break;
                case 7:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_ROBOT_EMERGENCY_STOP);
                    break;
                case 8:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_VIOLATION);
                    break;
                case 9:
                    mTvSafeMode.setText(URConstants.SAFETY_MODE_FAULT);
                    break;
            }
        }
    }

    /**
     * 机器模式
     *
     * @author Leo
     * created at 2019/6/20 9:54 PM
     */
    private void initMode() {
        if (TAG == 1) {
            String mode = JNIUtils.ReadURparam(URConstants.Robot_Mod, URConstants.Marm);
            showMode(mode);
        } else {
            String mode = JNIUtils.ReadURparam(URConstants.Robot_Mod, URConstants.Farm);
            showMode(mode);

        }
    }

    private void showMode(String mode) {
        if (!StringUtils.isEmpty(mode)){
            int i = (int)Double.parseDouble(mode);
            switch (i) {
                case 0:
                    mTvMode.setText(URConstants.ROBOT_MODE_DISCONNECTED);
                    break;
                case 1:
                    mTvMode.setText(URConstants.ROBOT_MODE_CONFIRM_SAFETY);
                    break;
                case 2:
                    mTvMode.setText(URConstants.ROBOT_MODE_BOOTING);
                    break;
                case 3:
                    mTvMode.setText(URConstants.ROBOT_MODE_POWER_OFF);
                    break;
                case 4:
                    mTvMode.setText(URConstants.ROBOT_MODE_POWER_ON);
                    break;
                case 5:
                    mTvMode.setText(URConstants.ROBOT_MODE_IDLE);
                    break;
                case 6:
                    mTvMode.setText(URConstants.ROBOT_MODE_BACKDRIVE);
                    break;
                case 7:
                    mTvMode.setText(URConstants.ROBOT_MODE_RUNNING);
                    break;
                case 8:
                    mTvMode.setText(URConstants.ROBOT_MODE_UPDATING_FIRMWARE);
                    break;
            }
        }

    }

    private void initTCP() {
        if (TAG == 1) {
            String x1 = JNIUtils.ReadURparam(URConstants.Act_X, URConstants.Marm);
            mTv2.setText(x1);
        } else {
            String x1 = JNIUtils.ReadURparam(URConstants.Act_X, URConstants.Farm);
            mTv2.setText(x1);
        }
        if (TAG == 1) {
            String y2 = JNIUtils.ReadURparam(URConstants.Act_Y, URConstants.Marm);
            mTv4.setText(y2);
        } else {
            String y2 = JNIUtils.ReadURparam(URConstants.Act_Y, URConstants.Farm);
            mTv4.setText(y2);
        }
        if (TAG == 1) {
            String z3 = JNIUtils.ReadURparam(URConstants.Act_Z, URConstants.Marm);
            mTv6.setText(z3);
        } else {
            String z3 = JNIUtils.ReadURparam(URConstants.Act_Z, URConstants.Farm);
            mTv6.setText(z3);
        }
        if (TAG == 1) {
            String RX1 = JNIUtils.ReadURparam(URConstants.Act_Rx, URConstants.Marm);
            mTv8.setText(RX1);
        } else {
            String RX1 = JNIUtils.ReadURparam(URConstants.Act_Rx, URConstants.Farm);
            mTv8.setText(RX1);
        }
        if (TAG == 1) {
            String RY2 = JNIUtils.ReadURparam(URConstants.Act_Ry, URConstants.Marm);
            mTv10.setText(RY2);
        } else {
            String RY2 = JNIUtils.ReadURparam(URConstants.Act_Ry, URConstants.Farm);
            mTv10.setText(RY2);
        }
        if (TAG == 1) {
            String RZ3 = JNIUtils.ReadURparam(URConstants.Act_Rz, URConstants.Marm);
            mTv12.setText(RZ3);
        } else {
            String RZ3 = JNIUtils.ReadURparam(URConstants.Act_Rz, URConstants.Farm);
            mTv12.setText(RZ3);
        }
    }

    private void initArmNetty() {
        mMainArmNettyClient = (ArmNettyClient) NettyManager.getInstance().getClientByTag(RobotInit.MAIN_ARM_NETTY);
        mFlowArmNettyClient = (ArmNettyClient) NettyManager.getInstance().getClientByTag(RobotInit.FLOW_ARM_NETTY);
    }

    private void initView(View view) {

        mRlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
        mRl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        mRl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        mRl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        mRl4 = (RelativeLayout) view.findViewById(R.id.rl4);


        mTv2 = (TextView) view.findViewById(R.id.tv2);
        mTv4 = (TextView) view.findViewById(R.id.tv4);
        mTv6 = (TextView) view.findViewById(R.id.tv6);
        mTv8 = (TextView) view.findViewById(R.id.tv8);
        mTv10 = (TextView) view.findViewById(R.id.tv10);
        mTv12 = (TextView) view.findViewById(R.id.tv12);

        mTvMode = (TextView) view.findViewById(R.id.tv_mode);
        mTvSafeMode = (TextView) view.findViewById(R.id.tv_safe_mode);
        mTvStatus = (TextView) view.findViewById(R.id.tv_status);

        mIv1 = (ImageButton) view.findViewById(R.id.iv1);
        mIv2 = (ImageButton) view.findViewById(R.id.iv2);
        mIv3 = (ImageButton) view.findViewById(R.id.iv3);
        mIv4 = (ImageButton) view.findViewById(R.id.iv4);
        mIv5 = (ImageButton) view.findViewById(R.id.iv5);
        mIv6 = (ImageButton) view.findViewById(R.id.iv6);
        mIv1.setOnTouchListener(mOnDownClickListener);
        mIv2.setOnTouchListener(mOnDownClickListener);
        mIv3.setOnTouchListener(mOnDownClickListener);
        mIv4.setOnTouchListener(mOnDownClickListener);
        mIv5.setOnTouchListener(mOnDownClickListener);
        mIv6.setOnTouchListener(mOnDownClickListener);
        mTvToolName = (TextView) view.findViewById(R.id.tv_tool_name);
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }


    private View.OnTouchListener mOnDownClickListener = (v, event) -> {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) { //按下
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
        } else if (action == MotionEvent.ACTION_UP) {//松开
            if (TAG == 1) {//主臂
                mainArmUp();
            } else {//从臂
                flowArmUp();
            }
        }
        return false;
    };

    private void flowArmUp() {
        if (mFlowArmNettyClient != null) {
            mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionStopJ(URConstants.Farm));
        }
    }

    private void mainArmUp() {
        if (mMainArmNettyClient != null) {
            mMainArmNettyClient.sendMsg30001(JNIUtils.ActionStopJ(URConstants.Marm));
        }
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
                .setAgentWebParent((RelativeLayout) mRl3, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
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
                .setAgentWebParent((RelativeLayout) mRl2, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.DRAIN_LINE_CAMERA_URL);

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
                .setAgentWebParent((RelativeLayout) mRlMain, -1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
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


    private void down() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_2, URConstants.Farm));
            }
        }
    }

    private void up() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_1, URConstants.Farm));
            }
        }

    }

    private void right() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_4, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_4, URConstants.Farm));
            }
        }

    }

    private void left() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_3, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_3, URConstants.Farm));
            }
        }

    }

    private void backward() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_5, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_5, URConstants.Farm));
            }
        }

    }

    private void forward() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_6, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionMove(URConstants.ACTION_MOVE_6, URConstants.Farm));
            }
        }
    }
}
