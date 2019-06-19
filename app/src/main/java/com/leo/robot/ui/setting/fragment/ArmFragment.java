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
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;

/**
 * 机械臂设置
 * created by Leo on 2019/4/18 22 : 06
 */


public class ArmFragment extends BaseFragment {
    private boolean isPause;
    private int TAG = 0;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTvArm1;
    private TextView mTvArm2;
    private TextView mTvArm3;
    private TextView mTvArm4;
    private TextView mTvArm5;
    private TextView mTvArm6;
    private NettyClient mClient;
    private JNIUtils mJniUtils;


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

    private ImageButton mIv1;
    private ImageButton mIv2;
    private ImageButton mIv3;
    private ImageButton mIv4;
    private ImageButton mIv5;
    private ImageButton mIv6;
    private ImageButton mIv7;
    private ImageButton mIv8;
    private ImageButton mIv9;
    private ImageButton mIv10;
    private ImageButton mIv11;
    private ImageButton mIv12;

    private ArmNettyClient mMainArmNettyClient;
    private ArmNettyClient mFlowArmNettyClient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_arm, container, false);
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
    }

    @Override
    public void updateData() {
        initTem();
        initJoint();
    }

    private void initJoint() {
        //手腕1角度
        String s1 = JNIUtils.ReadURparam(URConstants.J3_A, URConstants.Marm);
        mTvArm1.setText(s1);
        //手腕2角度
        String s2 = JNIUtils.ReadURparam(URConstants.J4_A, URConstants.Marm);
        mTvArm2.setText(s2);
        //手腕3角度
        String s3 = JNIUtils.ReadURparam(URConstants.J5_A, URConstants.Marm);
        mTvArm3.setText(s3);
        //肘部角度
        String s4 = JNIUtils.ReadURparam(URConstants.J2_A, URConstants.Marm);
        mTvArm4.setText(s4);
        //肩部角度
        String s5 = JNIUtils.ReadURparam(URConstants.J1_A, URConstants.Marm);
        mTvArm5.setText(s5);
        //基座角度
        String s6 = JNIUtils.ReadURparam(URConstants.J0_A, URConstants.Marm);
        mTvArm6.setText(s6);
    }

    private void initArmNetty() {
        mMainArmNettyClient = (ArmNettyClient) NettyManager.getInstance().getClientByTag(RobotInit.MAIN_ARM_NETTY);
        mFlowArmNettyClient = (ArmNettyClient) NettyManager.getInstance().getClientByTag(RobotInit.FLOW_ARM_NETTY);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
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

    private void initView(View view) {


        mRlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
        mRl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        mRl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        mRl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        mRl4 = (RelativeLayout) view.findViewById(R.id.rl4);


        mIv1 = (ImageButton) view.findViewById(R.id.iv1);
        mIv2 = (ImageButton) view.findViewById(R.id.iv2);
        mIv3 = (ImageButton) view.findViewById(R.id.iv3);
        mIv4 = (ImageButton) view.findViewById(R.id.iv4);
        mIv5 = (ImageButton) view.findViewById(R.id.iv5);
        mIv6 = (ImageButton) view.findViewById(R.id.iv6);
        mIv7 = (ImageButton) view.findViewById(R.id.iv7);
        mIv8 = (ImageButton) view.findViewById(R.id.iv8);
        mIv9 = (ImageButton) view.findViewById(R.id.iv9);
        mIv10 = (ImageButton) view.findViewById(R.id.iv10);
        mIv11 = (ImageButton) view.findViewById(R.id.iv11);
        mIv12 = (ImageButton) view.findViewById(R.id.iv12);

        mTv1 = (TextView) view.findViewById(R.id.tv1);
        mTv2 = (TextView) view.findViewById(R.id.tv2);
        mTv3 = (TextView) view.findViewById(R.id.tv3);
        mTv4 = (TextView) view.findViewById(R.id.tv4);
        mTv5 = (TextView) view.findViewById(R.id.tv5);
        mTv6 = (TextView) view.findViewById(R.id.tv6);

        mTvArm1 = (TextView) view.findViewById(R.id.tv_arm1);
        mTvArm2 = (TextView) view.findViewById(R.id.tv_arm2);
        mTvArm3 = (TextView) view.findViewById(R.id.tv_arm3);
        mTvArm4 = (TextView) view.findViewById(R.id.tv_arm4);
        mTvArm5 = (TextView) view.findViewById(R.id.tv_arm5);
        mTvArm6 = (TextView) view.findViewById(R.id.tv_arm6);


        mIv1.setOnTouchListener(mOnDownClickListener);
        mIv2.setOnTouchListener(mOnDownClickListener);
        mIv3.setOnTouchListener(mOnDownClickListener);
        mIv4.setOnTouchListener(mOnDownClickListener);
        mIv5.setOnTouchListener(mOnDownClickListener);
        mIv6.setOnTouchListener(mOnDownClickListener);
        mIv7.setOnTouchListener(mOnDownClickListener);
        mIv8.setOnTouchListener(mOnDownClickListener);
        mIv9.setOnTouchListener(mOnDownClickListener);
        mIv10.setOnTouchListener(mOnDownClickListener);
        mIv11.setOnTouchListener(mOnDownClickListener);
        mIv12.setOnTouchListener(mOnDownClickListener);


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



    private void downClickPedestalDec() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J0_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J0_1, URConstants.Farm));
            }
        }
    }

    private void downClickPedestalAdd() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J0_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J0_2, URConstants.Farm));
            }
        }
    }

    private void downClickShoulderDec() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J1_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J1_1, URConstants.Farm));
            }
        }
    }

    private void downClickShoulderAdd() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J1_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J1_2, URConstants.Farm));
            }
        }
    }

    private void downClickElbowAdd() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J2_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J2_2, URConstants.Farm));
            }
        }
    }

    private void downClickElbowDec() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J2_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J2_1, URConstants.Farm));
            }
        }
    }

    private void downClickWrist3Add() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J5_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J5_2, URConstants.Farm));
            }
        }
    }

    private void downClickWrist3Dec() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J5_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J5_1, URConstants.Farm));
            }
        }
    }

    private void downClickWrist2Add() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J4_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J4_2, URConstants.Farm));
            }
        }
    }

    private void downClickWrist2Dec() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J4_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J4_1, URConstants.Farm));
            }
        }
    }

    private void downClickWrist1Add() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J3_2, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J3_2, URConstants.Farm));
            }
        }
    }

    private void downClickWrist1Dec() {
        if (TAG == 1) {//主臂
            if (mMainArmNettyClient != null) {
                mMainArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J3_1, URConstants.Marm));
            }
        } else {//从臂
            if (mFlowArmNettyClient != null) {
                mFlowArmNettyClient.sendMsg30001(JNIUtils.ActionJoint(URConstants.ACTION_J3_1, URConstants.Farm));
            }
        }
    }


    private View.OnTouchListener mOnDownClickListener = (v, event) -> {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) { //按下
            switch (v.getId()) {
                case R.id.iv1:
                    downClickWrist1Dec();
                    break;
                case R.id.iv2:

                    downClickWrist1Add();
                    break;
                case R.id.iv3:

                    downClickWrist2Dec();
                    break;
                case R.id.iv4:

                    downClickWrist2Add();
                    break;
                case R.id.iv5:

                    downClickWrist3Dec();
                    break;
                case R.id.iv6:

                    downClickWrist3Add();
                    break;
                case R.id.iv7:

                    downClickElbowDec();
                    break;
                case R.id.iv8:

                    downClickElbowAdd();
                    break;
                case R.id.iv9:

                    downClickShoulderDec();
                    break;
                case R.id.iv10:

                    downClickShoulderAdd();
                    break;
                case R.id.iv11:

                    downClickPedestalDec();
                    break;
                case R.id.iv12:
                    downClickPedestalAdd();
                    break;
            }
        } else if (action == MotionEvent.ACTION_UP) {//松开
            if (TAG == 1) {//主臂
                if (mMainArmNettyClient != null) {
                    mMainArmNettyClient.sendMsg30001(JNIUtils.ActionStopJ(URConstants.Marm));
                }
            } else {//从臂
                if (mFlowArmNettyClient != null) {
                    mMainArmNettyClient.sendMsg30001(JNIUtils.ActionStopJ(URConstants.Marm));
                }

            }
        }
        return false;
    };

    private void flowArmUp() {
        mClient.sendMsgTest(CommandUtils.getFlowArmActionStop());
    }

    private void mainArmUp() {
        mClient.sendMsgTest(CommandUtils.getMainArmActionStop());
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




    private void initTem() {
        //基座温度
        String mainArm_J0T = JNIUtils.ReadURparam(URConstants.J0_T, URConstants.Marm);
        mTv6.setText(mainArm_J0T);
        //肩部温度
        String mainArm_J1T = JNIUtils.ReadURparam(URConstants.J1_T, URConstants.Marm);
        mTv4.setText(mainArm_J1T);
        //肘部温度
        String mainArm_J2T = JNIUtils.ReadURparam(URConstants.J2_T, URConstants.Marm);
        mTv2.setText(mainArm_J2T);
        //手腕1温度
        String mainArm_J3T = JNIUtils.ReadURparam(URConstants.J3_T, URConstants.Marm);
        mTv1.setText(mainArm_J3T);
        //手腕2温度
        String mainArm_J4T = JNIUtils.ReadURparam(URConstants.J4_T, URConstants.Marm);
        mTv3.setText(mainArm_J4T);
        //手腕3温度
        String mainArm_J5T = JNIUtils.ReadURparam(URConstants.J5_T, URConstants.Marm);
        mTv5.setText(mainArm_J5T);

    }


}
