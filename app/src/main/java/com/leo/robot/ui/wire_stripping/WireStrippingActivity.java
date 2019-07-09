package com.leo.robot.ui.wire_stripping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.leo.robot.constant.Constants;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.URConstants;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.arm.FlowArmBean;
import com.leo.robot.ui.choose.ChooseActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.ui.wiring.WiringActivity;
import com.leo.robot.utils.ClearWebUtils;
import com.leo.robot.utils.DateUtils;
import com.leo.robot.utils.PowerUtils;
import com.leo.robot.view.CustomPopWindow;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 剥线作业
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivity extends NettyActivity<WireStrippingActivityPresenter> {

    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    @BindView(R.id.iv_back)
    ImageView mIvBack;


    public static final String TAG = "WireStrippingActivity";
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.tv_ready)
    TextView mTvReady;
    @BindView(R.id.iv_ready)
    ImageView mIvReady;
    @BindView(R.id.tv_init)
    TextView mTvInit;
    @BindView(R.id.iv_init)
    ImageView mIvInit;
    @BindView(R.id.tv_in_place)
    TextView mTvInPlace;
    @BindView(R.id.iv_in_place)
    ImageView mIvInPlace;
    @BindView(R.id.tv_clamping)
    TextView mTvClamping;
    @BindView(R.id.iv_clamping)
    ImageView mIvClamping;
    @BindView(R.id.tv_closure)
    TextView mTvClosure;
    @BindView(R.id.iv_closure)
    ImageView mIvClosure;
    @BindView(R.id.tv_peeling)
    TextView mTvPeeling;
    @BindView(R.id.iv_peeling)
    ImageView mIvPeeling;
    @BindView(R.id.tv_cut_off)
    TextView mTvCutOff;
    @BindView(R.id.iv_cut_off)
    ImageView mIvCutOff;
    @BindView(R.id.tv_unlock)
    TextView mTvUnlock;
    @BindView(R.id.iv_unlock)
    ImageView mIvUnlock;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.iv_end)
    ImageView mIvEnd;
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
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rl_log)
    RecyclerView mRlLog;
    @BindView(R.id.tv_robot_status)
    TextView mTvRobotStatus;
    @BindView(R.id.tv_log)
    TextView mTvLog;
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


    private boolean isPause;
    private boolean isShown = false;
    private List<String> mStatusData;
    //机器人状态
    private ActionAdapter mStatusAdapter;
    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;
    private List<String> mLogData;
    //操作日志
    private ActionAdapter mLogAdapter;
    private CustomPopWindow mCustomPopWindow;
    private boolean isStart = false;


    @Override
    protected void notifyData(int status, String message) {
        mTvType.setText(message);

        if (status == 0) {//未连接
            updateReady(false);
            updateInit(false);
            updateInPlace(false);
            updateClamping(false);
            updateClosure(false);
            updatePeeling(false);
            updateCutOff(false);
            updateUnlock(false);
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
//            updateInit(false);
//            updateInPlace(false);
//            updateClamping(false);
//            updateClosure(false);
//            updatePeeling(false);
//            updateCutOff(false);
//            updateUnlock(false);
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
        DaggerWireStrippingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wire_stripping);
        ButterKnife.bind(this);
        initTile();
        initAdapter();
        initMainVideo();
        initVideo2();
        initVideo3();
        initVideo4();
        initBroadcast(mTvGroundPower);
        mPresenter.initStatus();

        initSocketStatus();
        mBtnJump.setVisibility(View.VISIBLE);
        //实时请求行线、引流线距离
        mPresenter.initClient();
        mPresenter.initLineLocation();
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

//    private void initUnity() {
//        mUnityPlayer = getUnityPlayer();
//        mRl1.addView(mUnityPlayer);
//        mUnityPlayer.requestFocus();
//    }

    /**
     * 从臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:27 PM
     */
    private void initVideo4() {
        mAgentWeb4 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl4, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.ARM_FLOW_CAMERA_UREL);

        initWebSetting(mAgentWeb4.getWebCreator().getWebView());
        mAgentWeb4.getWebCreator().getWebView().reload();
    }

    /**
     * 主臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo3() {
        mAgentWeb3 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl3, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.ARM_MAIN_CAMERA_UREL);

        initWebSetting(mAgentWeb3.getWebCreator().getWebView());
        mAgentWeb3.getWebCreator().getWebView().reload();
    }


    /**
     * 引流线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo2() {
        mAgentWeb2 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl2, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .createAgentWeb()
                .ready()
                .go(UrlConstant.DRAIN_LINE_CAMERA_URL);

        initWebSetting(mAgentWeb2.getWebCreator().getWebView());
        mAgentWeb2.getWebCreator().getWebView().reload();
    }


    /**
     * 行线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initMainVideo() {
        mAgentWebMain = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, Integer.valueOf(1))
                .createAgentWeb()
                .ready()
                .go(UrlConstant.LINE_CAMERA_URL);
        AgentWebConfig.debug();
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

    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }


    @Override
    protected void onPause() {


        super.onPause();
        isShown = false;

        LogUtils.e("剥线界面：  onPause");
    }


    @Override
    protected void onResume() {

        super.onResume();
        isShown = true;
//        mPresenter.initStatus();
        if (mAgentWebMain != null) {
            mAgentWebMain.getWebCreator().getWebView().reload();
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
        LogUtils.e("剥线界面： onResume");
    }


    @Override
    protected void onDestroy() {

        mPresenter.destroyClient();
        mPresenter.onDestroy();
        super.onDestroy();
        clearWeb();
        AgentWebConfig.clearDiskCache(this);
        onUnBindReceiver();

        LogUtils.e("剥线界面：   onDestroy ");
    }

    private void clearWeb() {
        ClearWebUtils.clearVideo(mAgentWebMain, this);
        ClearWebUtils.clearVideo(mAgentWeb2, this);
        ClearWebUtils.clearVideo(mAgentWeb3, this);
        ClearWebUtils.clearVideo(mAgentWeb4, this);
    }


    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
        LogUtils.e("剥线界面： onStop");

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
        String msg = bean.getMsg();
        if (isShown) {
            if (type.equals(RobotInit.MASTER_CONTROL_NETTY)) {//主控服务器
                if ("0".equals(code)) {//连接失败或断开连接
                    mTvType.setText("与主控服务器断开连接，正在重连");
                    mSpinKit.setVisibility(View.VISIBLE);
                } else if ("1".equals(code)) {//连接成功
                    mTvType.setText("与主控服务器连接成功");
                    mSpinKit.setVisibility(View.GONE);
                }
            } else if (type.equals(RobotInit.VISION_NETTY)) {//视觉服务器
                if ("0".equals(code)) {//连接失败或断开连接
//                    ToastUtils.showShortToast(msg);

                } else if ("1".equals(code)) {//连接成功
//                    ToastUtils.showShortToast(msg);

                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptWireStrippingMsg(WireStrippingMsg msg) {
        if (isShown) {
            String s = mPresenter.jugType(msg);
            if (!StringUtils.isEmpty(s)) {
                refreshStatusRv(s);
            }
//            ToastUtils.showShortToast("接收到剥线推送消息 ： " + msg.getMsg() + "      " + msg.getCode());
        }
    }


    //------------------------ 更新UI --------------------------
    public void updateReady(boolean b) {
        mTvReady.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvReady.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvReady.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvReady.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
            updateClickStatus(b);
        }
    }

    public void updateInit(boolean b) {
        mTvInit.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvInit.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvInit.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvInit.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));

        }
    }

    public void updateInPlace(boolean b) {
        mTvInPlace.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvInPlace.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvInPlace.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvInPlace.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClamping(boolean b) {
        mTvClamping.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvClamping.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvClamping.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvClamping.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClosure(boolean b) {
        mTvClosure.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvClosure.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvClosure.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvClosure.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updatePeeling(boolean b) {
        mTvPeeling.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvPeeling.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvPeeling.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvPeeling.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateCutOff(boolean b) {
        mTvCutOff.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvCutOff.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvCutOff.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvCutOff.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateUnlock(boolean b) {
        mTvUnlock.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvUnlock.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvUnlock.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvUnlock.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateEnd(boolean b) {
        mTvEnd.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvEnd.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvEnd.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvEnd.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
            entryWiringActivity();
        }
    }

    public void updateEndNormal(boolean isEnd) {
        mTvEnd.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvEnd.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isEnd) {
            mTvEnd.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvEnd.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }


    /**
     * 接收到剥线结束命令后，自动跳转到接线页面
     *
     * @author Leo
     * created at 2019/5/26 12:28 PM
     */
    private void entryWiringActivity() {
        Constants.setFinishWrieStripping(true);
        ToastUtils.showShortToast("剥线作业结束，即将进入接线作业！");
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                startActivity(new Intent(WireStrippingActivity.this, WiringActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @OnClick({R.id.iv_scram, R.id.iv_take_back, R.id.iv_start, R.id.iv_identification, R.id.iv_setting, R.id.iv_back, R.id.btn_jump})
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
                if (!isStart) {
                    startClick();
                    isStart = true;
                } else {
                    mPresenter.sendStop();
                    changeStartUi(false);
                    isStart = false;
                }
                break;
            case R.id.iv_identification:
                if (!mPresenter.isFastDoubleClick()) {
                    mPresenter.identificationButton();
                }

                break;
            case R.id.iv_setting:
                mPresenter.settingButton();
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    refreshLogRv("按下返回键");
                    finish();
                }
//                SendUtils.sendMainArmRotate();
                break;
            case R.id.btn_jump:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WireStrippingActivity.this, WiringActivity.class));
                    finish();
                }
                break;
        }
    }

    private void startClick() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
        //处理popWindow 显示内容
        mPresenter.handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create()
                .showAsDropDown(mIvStart, 260, 20);
    }


    public void refreshStatusRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mStatusData.add(currentDate + " " + msg);
        mStatusAdapter.notifyDataSetChanged();
        mRlStatus.scrollToPosition(mStatusAdapter.getItemCount() - 1);
    }

    public void refreshLogRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mLogData.add(currentDate + " " + msg);
        mLogAdapter.notifyDataSetChanged();
        mRlLog.scrollToPosition(mLogAdapter.getItemCount() - 1);

    }


    /**
     * 更新一键收回、开始、识别路线、手动设置是否可点击
     *
     * @author Leo
     * created at 2019/4/27 2:10 AM
     */
    public void updateClickStatus(boolean b) {
        mTvRemind.setText("请开始选取剥线位置");
    }


    /**
     * 切换急停、恢复急停图标
     *
     * @author Leo
     * created at 2019/5/1 1:19 AM
     */
    public void updateScram(boolean b) {
        if (!b) {
            mIvScram.setImageDrawable(getResources().getDrawable(R.drawable.jiting_normal));
        } else {
            mIvScram.setImageDrawable(getResources().getDrawable(R.drawable.jiechujiting_normal));
        }
    }

    /**
     * 切换开始、停止图标
     *
     * @author Leo
     * created at 2019/5/1 1:20 AM
     */
    public void updateStart(boolean b) {
        if (!b) {
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_normal));
        } else {
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.atingzhi_normal));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptOperatingModeBean(OperatingModeBean bean) {
        if (isShown) {
            String status = bean.getParams().getStatus();
            if ("0".equals(status)) {
                ToastUtils.showShortToast("正处于自动作业模式。");
                mPresenter.isSettingClickble = false;
                mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongcaozuo_unclick));
            } else {
                mPresenter.isSettingClickble = true;
                mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongcaozuo_normal));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraLocationMsg(ChooseCameraLocationMsg msg) {
        String code = msg.getCode();
        mPresenter.jugCameraLocationType(code);
    }


    /**
     * 消失pop
     *
     * @author Leo
     * created at 2019/5/29 9:10 PM
     */
    public void dismissPop() {
        if (mCustomPopWindow != null) {
            mCustomPopWindow.dissmiss();
        }
    }

    public void jumpChooseActivity(int camera, int location) {
        Intent intent = new Intent(WireStrippingActivity.this, ChooseActivity.class);
        intent.putExtra("activity", 1);
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
    public void flowArmMsg(FlowArmBean bean) {
        String code = bean.getCode();
        String msg = bean.getMsg();
        if (msg.length() == 2216) {
            if (code.equals("0")) {//30003数据
                handlerFlow30003Msg(msg);
            } else if (code.equals("1")) {//29999数据
                handlerFlow29999Msg(msg);
            }
        }
    }

    /**
     * 从臂29999端口数据
     *
     * @author Leo
     * created at 2019/6/20 8:30 PM
     */
    private void handlerFlow29999Msg(String msg) {
        JNIUtils.GetDataPort29999(msg, URConstants.Farm);
    }

    /**
     * 从臂30003端口数据
     *
     * @author Leo
     * created at 2019/6/20 8:30 PM
     */
    private void handlerFlow30003Msg(String msg) {
        JNIUtils.GetDataPort30003(msg, URConstants.Farm);
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

    public void changeStartUi(boolean isChange) {
        mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_normal));
        if (isChange) {
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.stop_normal));
        }
    }

}
