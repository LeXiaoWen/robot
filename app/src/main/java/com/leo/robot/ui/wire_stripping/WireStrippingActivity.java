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
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.*;
import com.leo.robot.constant.Constants;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.choose.ChooseActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.ui.wiring.WiringActivity;
import com.leo.robot.utils.DateUtils;
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
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void notifyMasterData(int status, String message) {
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
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
        }
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
//        initUnity();
        //实时请求行线、引流线距离
        mPresenter.initClient();
        mPresenter.initLineLocation();
    }

    private void initSocketStatus() {
        SPUtils socket = new SPUtils("masterSocket");
        boolean status = socket.getBoolean("status");
        SPUtils socket1 = new SPUtils("visionSocket");
        boolean status1 = socket1.getBoolean("status");
        if (status) {
            mTvType.setText("与主控连接成功");
            mSpinKit.setVisibility(View.GONE);
        } else {
            mTvType.setText("与主控断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
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
                .setAgentWebParent((RelativeLayout) mRl2, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
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
    private void initMainVideo() {
        mAgentWebMain = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
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
        mPresenter.initStatus();
        LogUtils.e("剥线界面： onResume");
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
        mAgentWeb2.getWebLifeCycle().onResume();
        mAgentWeb3.getWebLifeCycle().onResume();
        mAgentWeb4.getWebLifeCycle().onResume();
    }

    private void webViewOnDestroy() {
        mAgentWebMain.getWebLifeCycle().onDestroy();
        mAgentWeb2.getWebLifeCycle().onDestroy();
        mAgentWeb3.getWebLifeCycle().onDestroy();
        mAgentWeb4.getWebLifeCycle().onDestroy();
    }

    @Override
    protected void onDestroy() {
//        if (mAgentWebMain != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
//            webViewOnDestroy();
//        }
//        if (mAgentWebMain != null) {
//            mAgentWebMain.getWebLifeCycle().onDestroy();
//        }
//        if (mAgentWeb2 != null) {
//            mAgentWeb2.getWebLifeCycle().onDestroy();
//        }
//        if (mAgentWeb3 != null) {
//            mAgentWeb3.getWebLifeCycle().onDestroy();
//        }
//        if (mAgentWeb4 != null) {
//            mAgentWeb4.getWebLifeCycle().onDestroy();
//        }
        mPresenter.destroyClient();

        super.onDestroy();
//        mUnityPlayer.quit();
        mAgentWebMain = null;
        mAgentWeb2 = null;
        mAgentWeb3 = null;
        mAgentWeb4 = null;
        LogUtils.e("剥线界面：   onDestroy ");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptVisionMsg(VisionMsg msg) {
//        if (isShown) {
//            ToastUtils.showShortToast("接收到选点命令，即将进入选点页面！");
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2000);
//                    startActivity(new Intent(WireStrippingActivity.this, WireStrippingChooseLocationActivity.class));
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
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
//                if (!mPresenter.isFastDoubleClick()) {
//                    mPresenter.startButton();
//                }

                startClick();
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
//        if (b) {
//            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.yijianshouhui_normal));
//            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_normal));
//            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.shibieluxian_normal));
//            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongcaozuo_normal));
        mTvRemind.setText("请开始选取剥线位置");
//        } else {
//            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.yijianhuishou_unclick));
//            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_unclick));
//            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.shibieluxian_unclick));
//            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongcaozuo_unclick));
//        }
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
            ToastUtils.showShortToast("接收到选点命令，即将进入USB1选点页面！");

        } else {
            intent.putExtra("tag", 2);
            if (location == 1) {
                intent.putExtra("location", 1);
            } else {
                intent.putExtra("location", 2);
            }
            ToastUtils.showShortToast("接收到选点命令，即将进入USB2选点页面！");

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
}
