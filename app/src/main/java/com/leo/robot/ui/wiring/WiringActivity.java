package com.leo.robot.ui.wiring;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ybq.android.spinkit.SpinKitView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.*;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.URConstants;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.arm.MainArmBean;
import com.leo.robot.ui.choose.ChooseActivity;
import com.leo.robot.ui.setting.wiring_setting.WiringSettingActivity;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.utils.ClearWebUtils;
import com.leo.robot.utils.DateUtils;
import com.leo.robot.utils.PowerUtils;
import com.leo.robot.utils.WebErrorUtils;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 接线作业
 * created by Leo on 2019/4/18 10 : 46
 */


public class WiringActivity extends NettyActivity<WiringActivityPresenter> {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.rl_status)
    RecyclerView mRlStatus;
    @BindView(R.id.rl_1)
    RelativeLayout mRl1;
    @BindView(R.id.rl_2)
    RelativeLayout mRl2;
    @BindView(R.id.rl_3)
    RelativeLayout mRl3;
    @BindView(R.id.rl_4)
    RelativeLayout mRl4;
    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    @BindView(R.id.iv_scram)
    ImageView mIvScram;
    @BindView(R.id.iv_take_back)
    ImageView mIvTakeBack;
    @BindView(R.id.iv_start)
    ImageView mIvStart;
    @BindView(R.id.iv_identification)
    ImageView mIvIdentification;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_1)
    TextView mTv1;

    @BindView(R.id.tv_2)
    TextView mTv2;

    @BindView(R.id.tv_3)
    TextView mTv3;

    @BindView(R.id.tv_4)
    TextView mTv4;

    @BindView(R.id.tv_5)
    TextView mTv5;

    @BindView(R.id.tv_6)
    TextView mTv6;

    @BindView(R.id.tv_7)
    TextView mTv7;

    @BindView(R.id.tv_8)
    TextView mTv8;

    @BindView(R.id.tv_9)
    TextView mTv9;

    @BindView(R.id.tv_robot_status)
    TextView mTvRobotStatus;
    @BindView(R.id.tv_log)
    TextView mTvLog;
    @BindView(R.id.rl_log)
    RecyclerView mRlLog;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.iv_3)
    ImageView mIv3;
    @BindView(R.id.iv_4)
    ImageView mIv4;
    @BindView(R.id.iv_5)
    ImageView mIv5;
    @BindView(R.id.iv_6)
    ImageView mIv6;
    @BindView(R.id.iv_7)
    ImageView mIv7;
    @BindView(R.id.iv_8)
    ImageView mIv8;
    @BindView(R.id.iv_9)
    ImageView mIv9;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.btn_jump)
    Button mBtnJump;
    @BindView(R.id.btn_jump2)
    Button mBtnJump2;
    @BindView(R.id.tv_type1)
    TextView mTvType1;
    @BindView(R.id.spin_kit1)
    SpinKitView mSpinKit1;
    @BindView(R.id.ll_status1)
    LinearLayout mLlStatus1;
    @BindView(R.id.tv_mode)
    TextView mTvMode;
    @BindView(R.id.tv_safe_mode)
    TextView mTvSafeMode;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.ll_robot_status)
    LinearLayout mLlRobotStatus;
    @BindView(R.id.tv_wiring)
    TextView mTvWiring;
    @BindView(R.id.tv_wire_stripping)
    TextView mTvWireStripping;
    @BindView(R.id.tv_claw)
    TextView mTvClaw;
    @BindView(R.id.tv_cut_line)
    TextView mTvCutLine;
    @BindView(R.id.ll_error_main)
    LinearLayout mLlErrorMain;
    @BindView(R.id.ll_error1)
    LinearLayout mLlError1;
    @BindView(R.id.ll_error2)
    LinearLayout mLlError2;
    @BindView(R.id.ll_error3)
    LinearLayout mLlError3;
    @BindView(R.id.ll_error4)
    LinearLayout mLlError4;
    private boolean isPause;

    private List<String> mLogData;
    //操作日志
    private ActionAdapter mLogAdapter;
    private List<String> mStatusData;
    //机器人状态
    private ActionAdapter mStatusAdapter;

    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb1;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;


    private boolean isShown = false;

    private String videoMainTag = UrlConstant.URL[0];
    private String video1Tag = UrlConstant.URL[1];
    private String video2Tag = UrlConstant.URL[2];
    private String video3Tag = UrlConstant.URL[3];
    private String video4Tag = UrlConstant.URL[4];
    private String videoTag;

    @Override
    protected void notifyData(int status, String message) {
        mTvType.setText(message);

        if (status == 0) {//未连接
            updateReady(false);
            updateGrab(false);
            updateEnter(false);
            updateFixed(false);
            updateToolReady(false);
            updateLineReady(false);
            updateTwist(false);
            updateClipUnlock(false);
            updateSleeveUnlock(false);
            updateEnd(false);
            mSpinKit.setVisibility(View.VISIBLE);
            updateReady(false);

        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
            updateReady(true);

        }
    }

    @Override
    protected void notifyMasterData(int status, String message) {
//        mTvType.setText(message);
//
//        if (status == 0) {//未连接
//            updateReady(false);
//            updateGrab(false);
//            updateEnter(false);
//            updateFixed(false);
//            updateToolReady(false);
//            updateLineReady(false);
//            updateTwist(false);
//            updateClipUnlock(false);
//            updateSleeveUnlock(false);
//            updateEnd(false);
//            mSpinKit.setVisibility(View.VISIBLE);
//        } else {//已连接
//            mSpinKit.setVisibility(View.GONE);
//            updateReady(true);
//        }
    }

    @Override
    protected void notifyVisionData(int status, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvType1.setText(message);
                if (status == 0) {
                    mSpinKit1.setVisibility(View.VISIBLE);
                } else {
                    mSpinKit1.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWiringActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring);
        ButterKnife.bind(this);
        mPresenter.updateTime(mTvDate);
        initBroadcast(mTvGroundPower);
        mPresenter.initStatus();

        initAdapter();
        initMainVideo(UrlConstant.URL[0]);
        initVideo1(UrlConstant.URL[1]);
        initVideo2(UrlConstant.URL[2]);
        initVideo3(UrlConstant.URL[3]);
        initVideo4(UrlConstant.URL[4]);

        mPresenter.initClient();
//        mPresenter.setUnityView(mRl1);
        initSocketStatus();
        //实时请求行线、引流线距离
        mPresenter.initLineLocation();
        mBtnJump2.setVisibility(View.VISIBLE);
        mPresenter.updatePower();
    }

    private void initSocketStatus() {
        SPUtils socket = new SPUtils("masterSocket");
        boolean status = socket.getBoolean("status");
        SPUtils socket1 = new SPUtils("visionSocket");
        boolean status1 = socket1.getBoolean("status");
        if (status) {
            mTvType.setText("与主控连接成功");
            mSpinKit.setVisibility(View.GONE);
            updateReady(true);
        } else {
            mTvType.setText("与主控断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
            updateReady(false);

        }

        if (status1) {
            mTvType1.setText("与视觉连接成功");
            mSpinKit1.setVisibility(View.GONE);
        } else {
            mTvType1.setText("与视觉断开连接，正在重连");
            mSpinKit1.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        //机器人状态
        mStatusData = new ArrayList<>();
        mStatusAdapter = new ActionAdapter(this, mStatusData);
        mRlStatus.setLayoutManager(new LinearLayoutManager(this));
        mRlStatus.setAdapter(mStatusAdapter);

        //操作日志
        mLogData = new ArrayList<>();
        mLogAdapter = new ActionAdapter(this, mLogData);
        mRlLog.setLayoutManager(new LinearLayoutManager(this));
        mRlLog.setAdapter(mLogAdapter);
    }

    /**
     * 从臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:27 PM
     * @param url
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
     * @param url
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
     * @param url
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
     * @param url
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


    /**
     * 行线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     * @param url
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


    private void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRING_ACTIVITY);
        boolean isToolReady = utils.getBoolean(RobotInit.WIRING_TOOL_READY);
        LogUtils.e("WiringActivity 剥线工具到位  " + isToolReady);
    }


    @Override
    protected void onPause() {
        super.onPause();
        isShown = false;
    }

    @Override
    protected void onResume() {

        super.onResume();
//        mPresenter.initStatus();
        if (mAgentWebMain != null) {
            mAgentWebMain.getWebCreator().getWebView().reload();
        }
        if (mAgentWeb1 != null) {
            mAgentWeb1.getWebCreator().getWebView().reload();
        }
        if (mAgentWeb2 != null) {
            mAgentWeb2.getWebCreator().getWebView().reload();
        }
        if (mAgentWeb3 != null) {
            mAgentWeb3.getWebCreator().getWebView().reload();
        }
        if (mAgentWeb4 != null) {
            mAgentWeb4.getWebCreator().getWebView().reload();
        }
        isShown = true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroyClient();
        mPresenter.onDestroy();
        super.onDestroy();
        clearWeb();
        AgentWebConfig.clearDiskCache(this);
        onUnBindReceiver();

    }

    private void clearWeb() {
        ClearWebUtils.clearVideo(mAgentWebMain, this);
        ClearWebUtils.clearVideo(mAgentWeb1, this);
        ClearWebUtils.clearVideo(mAgentWeb2, this);
        ClearWebUtils.clearVideo(mAgentWeb3, this);
        ClearWebUtils.clearVideo(mAgentWeb4, this);
    }


    @Override
    protected void onStop() {
        onUnBindReceiver();

        super.onStop();
    }

    //------------------------ EventBus --------------------------

    /**
     * socket连接状态信息
     *
     * @author Leo
     * created at 2019/5/24 1:44 AM
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void socketStatus(SocketStatusBean bean) {
        String type = bean.getType();
        String code = bean.getCode();
        if (isShown) {
            if (type.equals(RobotInit.MASTER_CONTROL_NETTY)) {//主控服务器
                if ("0".equals(code)) {//连接失败或断开连接
                } else if ("1".equals(code)) {//连接成功

                }
            } else if (type.equals(RobotInit.VISION_NETTY)) {//视觉服务器
                if ("0".equals(code)) {//连接失败或断开连接

                } else if ("1".equals(code)) {//连接成功

                }
            }
        }
    }

    /**
     * 接收接线命令
     *
     * @author Leo
     * created at 2019/5/1 1:25 AM
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptWiringMsg(WiringMsg msg) {
        if (isShown) {
            String s = mPresenter.jugType(msg);
            if (!StringUtils.isEmpty(s)) {
                refreshStatusRv(s);
            }
        }
    }

    private void refreshStatusRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mStatusData.add(currentDate + " " + msg);
        mStatusAdapter.notifyDataSetChanged();
        mRlStatus.scrollToPosition(mStatusAdapter.getItemCount() - 1);
    }

    @OnClick({R.id.iv_scram, R.id.iv_take_back, R.id.iv_start, R.id.iv_identification, R.id.iv_setting, R.id.iv_back, R.id.btn_jump2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scram:
                if (!mPresenter.isFastDoubleClick()) {
                    mPresenter.scramButton();
                }
                break;
            case R.id.iv_take_back:
                if (!mPresenter.isFastDoubleClick()) {
                    mPresenter.revocerButton();
                }
                break;
            case R.id.iv_start:
                if (!mPresenter.isFastDoubleClick()) {
                    mPresenter.startButton();
                }
                break;
            case R.id.iv_identification:
                if (!mPresenter.isFastDoubleClick()) {
                    mPresenter.identificationClick();
                }
                break;
            case R.id.iv_setting:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WiringActivity.this, WiringSettingActivity.class));
                    finish();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_jump2:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WiringActivity.this, WireStrippingActivity.class));
                    finish();
                }
                break;
        }
    }

    private void webViewOnPause() {
        mAgentWebMain.getWebLifeCycle().onPause();
//        mAgentWeb1.getWebLifeCycle().onPause();
        mAgentWeb2.getWebLifeCycle().onPause();
        mAgentWeb3.getWebLifeCycle().onPause();
        mAgentWeb4.getWebLifeCycle().onPause();

    }

    private void webViewOnResume() {
        mAgentWebMain.getWebLifeCycle().onResume();
//        mAgentWeb1.getWebLifeCycle().onResume();
        mAgentWeb2.getWebLifeCycle().onResume();
        mAgentWeb3.getWebLifeCycle().onResume();
        mAgentWeb4.getWebLifeCycle().onResume();
    }

    private void webViewOnDestroy() {
        mAgentWebMain.getWebLifeCycle().onDestroy();
//        mAgentWeb1.getWebLifeCycle().onDestroy();
        mAgentWeb2.getWebLifeCycle().onDestroy();
        mAgentWeb3.getWebLifeCycle().onDestroy();
        mAgentWeb4.getWebLifeCycle().onDestroy();
    }

    //------------------------ 更新UI --------------------------
    public void updateReady(boolean b) {
        mTv1.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv1.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv1.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv1.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
            updateClickStatus(b);
        }
    }

    public void updateGrab(boolean isGrab) {
        mTv2.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv2.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isGrab) {
            mTv2.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv2.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateEnter(boolean isEnter) {
        mTv3.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv3.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isEnter) {
            mTv3.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv3.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateFixed(boolean isFixed) {
        //没有，预留
        mTv4.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv4.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isFixed) {
            mTv4.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv4.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateToolReady(boolean isToolReady) {
        //没有，预留
    }

    public void updateLineReady(boolean isLineReady) {
        mTv5.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv5.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isLineReady) {
            mTv5.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv5.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateTwist(boolean isTwist) {
        mTv6.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv6.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isTwist) {
            mTv6.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv6.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClipUnlock(boolean isClipUnlock) {

        mTv7.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv7.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isClipUnlock) {
            mTv7.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv7.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateSleeveUnlock(boolean isSleeveUnlock) {
        mTv8.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv8.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isSleeveUnlock) {
            mTv8.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv8.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }

    }

    public void updateEnd(boolean isEnd) {
        mTv9.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv9.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isEnd) {
            mTv9.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv9.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClickStatus(boolean b) {
        if (b) {
            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.yijianshouhui_normal));
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_normal));
            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.shibieluxian_normal));
            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongshezhi_normal));
            mTvRemind.setText("请行线接线位置");
        } else {
            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.yijianhuishou_unclick));
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_unclick));
            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.shibieluxian_unclick));
            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongshezhi_unclick));
        }
    }

    public void refreshLogRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mLogData.add(currentDate + " " + msg);
        mLogAdapter.notifyDataSetChanged();
        mRlLog.scrollToPosition(mLogAdapter.getItemCount() - 1);

    }

    public void updateScram(boolean b) {
        if (!b) {
            mIvScram.setImageDrawable(getResources().getDrawable(R.drawable.jiting_normal));
        } else {
            mIvScram.setImageDrawable(getResources().getDrawable(R.drawable.jiechujiting_normal));
        }
    }

    public void updateStart(boolean b) {
        if (!b) {
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_normal));
        } else {
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.atingzhi_normal));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptVisionMsg(VisionMsg msg) {
//        if (isShown) {
//            ToastUtils.showShortToast("接收到选点命令，即将进入选点页面！");
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2000);
//                    startActivity(new Intent(WiringActivity.this, WiringChooseLocationActivity.class));
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraLocationMsg(ChooseCameraLocationMsg msg) {
        if (isShown) {
            String code = msg.getCode();
            mPresenter.jugCameraLocationType(code);
        }

    }

    public void jumpChooseActivity(int camera, int location) {
        Intent intent = new Intent(WiringActivity.this, ChooseActivity.class);
        intent.putExtra("activity", 2);
        if (camera == 1) {
            intent.putExtra("tag", 1);
            if (location == 1) {
                intent.putExtra("location", 1);
            } else {
                intent.putExtra("location", 2);
            }
            ToastUtils.showShortToast("接收到选点命令，即将进入USB1—行线画面！");

        } else {
            intent.putExtra("tag", 2);
            if (location == 1) {
                intent.putExtra("location", 1);
            } else {
                intent.putExtra("location", 2);
            }
            ToastUtils.showShortToast("接收到选点命令，即将进入USB2-引流线画面！");

        }
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateOwnPower(MasterPowerDataMsg msg) {
        String code = msg.getCode();
        String ownPower = PowerUtils.getPowerByType(code, URConstants.Master_Power_Ma);
        //剥线工具电量
        String Wire_Stripper_Ma = PowerUtils.getPowerByType(code, URConstants.Wire_Stripper_Ma);
        //接线工具电量
        String Connect_Wire_Ma = PowerUtils.getPowerByType(code, URConstants.Connect_Wire_Ma);
        //剪线工具电量
        String Cut_Wire_Ma = PowerUtils.getPowerByType(code, URConstants.Cut_Wire_Ma);
        //手爪工具电量
        String Hand_Grab_Ma = PowerUtils.getPowerByType(code, URConstants.Hand_Grab_Ma);
        updatePw(ownPower, Wire_Stripper_Ma, Connect_Wire_Ma, Cut_Wire_Ma, Hand_Grab_Ma);

    }

    public void updatePw(String ownPower, String wire_Stripper_Ma, String connect_Wire_Ma, String cut_Wire_Ma, String hand_Grab_Ma) {
        if (!StringUtils.isEmpty(ownPower)) {
            mTvOwnPower.setText(ownPower);
        }

        if (!StringUtils.isEmpty(wire_Stripper_Ma)) {
            mTvWireStripping.setText(wire_Stripper_Ma);
        }
        if (!StringUtils.isEmpty(connect_Wire_Ma)) {
            mTvWiring.setText(connect_Wire_Ma);
        }
        if (!StringUtils.isEmpty(cut_Wire_Ma)) {
            mTvCutLine.setText(cut_Wire_Ma);
        }
        if (!StringUtils.isEmpty(hand_Grab_Ma)) {
            mTvClaw.setText(hand_Grab_Ma);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainArmMsg(MainArmBean bean) {
        String code = bean.getCode();
        String msg = bean.getMsg();
        if (msg.length() == 2216) {
            if (code.equals("0")) {//30003数据
                handlerMain30003Msg(msg);
            } else if (code.equals("1")) {//29999数据
                handlerMain29999Msg(msg);
            }
        }
    }

    /**
     * 主臂29999端口数据
     *
     * @author Leo
     * created at 2019/6/19 11:18 PM
     */
    private void handlerMain29999Msg(String msg) {
        JNIUtils.GetDataPort29999(msg, URConstants.Marm);
    }

    /**
     * 主臂30003端口数据
     *
     * @author Leo
     * created at 2019/6/19 11:18 PM
     */
    private void handlerMain30003Msg(String msg) {
        JNIUtils.GetDataPort30003(msg, URConstants.Marm);
    }


    public void showStatus(String status) {
        if (!StringUtils.isEmpty(status)) {
            int i = (int) Double.parseDouble(status);
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

    public void showSafeMode(String safeMode) {
        if (!StringUtils.isEmpty(safeMode)) {
            int i = (int) Double.parseDouble(safeMode);
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

    public void showMode(String mode) {
        if (!StringUtils.isEmpty(mode)) {
            int i = (int) Double.parseDouble(mode);
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

    private View.OnTouchListener changeOnTouchListener1 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ClearWebUtils.clearVideo(mAgentWebMain, WiringActivity.this);
                ClearWebUtils.clearVideo(mAgentWeb1, WiringActivity.this);
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
                ClearWebUtils.clearVideo(mAgentWebMain, WiringActivity.this);
                ClearWebUtils.clearVideo(mAgentWeb2, WiringActivity.this);
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
                ClearWebUtils.clearVideo(mAgentWebMain, WiringActivity.this);
                ClearWebUtils.clearVideo(mAgentWeb3, WiringActivity.this);
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
                ClearWebUtils.clearVideo(mAgentWebMain, WiringActivity.this);
                ClearWebUtils.clearVideo(mAgentWeb1, WiringActivity.this);
                initMainVideo(video4Tag);
                initVideo4(videoMainTag);
                videoTag = videoMainTag;
                videoMainTag = video4Tag;
                video4Tag = videoTag;
            }

            return false;
        }
    };

}
