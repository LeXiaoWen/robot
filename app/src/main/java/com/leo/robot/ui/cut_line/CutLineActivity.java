package com.leo.robot.ui.cut_line;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.leo.robot.bean.ChooseCameraLocationMsg;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.bean.MasterPowerDataMsg;
import com.leo.robot.bean.SocketStatusBean;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.URConstants;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.arm.MainArmBean;
import com.leo.robot.ui.choose.ChooseActivity;
import com.leo.robot.ui.setting.cut_line_setting.CutLineSettingActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.utils.ClearWebUtils;
import com.leo.robot.utils.DateUtils;
import com.leo.robot.utils.PowerUtils;
import com.leo.robot.utils.WebErrorUtils;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.ui.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 剪线作业
 * created by Leo on 2019/4/18 10 : 46
 */


public class CutLineActivity extends NettyActivity<CutLineActivityPresenter> {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
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
    private boolean isShown = false;


    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb1;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;

    private List<String> mLogData;
    //操作日志
    private ActionAdapter mLogAdapter;
    private List<String> mStatusData;
    //机器人状态
    private ActionAdapter mStatusAdapter;


    @Override
    protected void notifyData(int status, String message) {
        mTvType.setText(message);

        if (status == 0) {//未连接
            updateInit(false);
            updateReady(false);
            updateCutStart(false);
            updateCutStop(false);
            updateCutReset(false);
            updateEnd(false);
            mSpinKit.setVisibility(View.VISIBLE);
            updateInit(false);
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
            updateInit(true);
        }
    }

    @Override
    protected void notifyMasterData(int status, String message) {
//        mTvType.setText(message);
//
//        if (status == 0) {//未连接
//            updateInit(false);
//            updateReady(false);
//            updateCutStart(false);
//            updateCutStop(false);
//            updateCutReset(false);
//            updateEnd(false);
//            mSpinKit.setVisibility(View.VISIBLE);
//        } else {//已连接
//            mSpinKit.setVisibility(View.GONE);
//            updateInit(true);
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
        DaggerCutLineActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_line);
        ButterKnife.bind(this);
        mPresenter.updateTime(mTvDate);
        initBroadcast(mTvGroundPower);
        mPresenter.initStatus();

        initAdapter();
        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
        initSocketStatus();
        //实时请求行线、引流线距离
        mPresenter.initClient();
        mPresenter.initLineLocation();
//        mPresenter.setUnityView(mRl1);
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
            updateInit(true);
        } else {
            mTvType.setText("与主控断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
            updateInit(false);
        }

        if (status1) {
            mTvType1.setText("与视觉连接成功");
            mSpinKit1.setVisibility(View.GONE);
        } else {
            mTvType1.setText("与视觉断开连接，正在重连");
            mSpinKit1.setVisibility(View.VISIBLE);
        }
    }

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
                .createAgentWeb()
                .ready()
                .go(UrlConstant.ARM_FLOW_CAMERA_UREL);
        WebErrorUtils utils = new WebErrorUtils();
        utils.errorWeb(mAgentWeb4, mLlError4);
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
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb3, mLlError3);
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
                .createAgentWeb()
                .ready()
                .go(UrlConstant.DRAIN_LINE_CAMERA_URL);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb2, mLlError2);
        initWebSetting(mAgentWeb2.getWebCreator().getWebView());
        mAgentWeb2.getWebCreator().getWebView().reload();
    }

    /**
     * 云台画面
     *
     * @author Leo
     * created at 2019/7/9 10:05 PM
     */
    private void initVideo1() {
        mAgentWeb1 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb1, mLlError1);
        initWebSetting(mAgentWeb1.getWebCreator().getWebView());
        mAgentWeb1.getWebCreator().getWebView().reload();
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
                .createAgentWeb()
                .ready()
                .go(UrlConstant.LINE_CAMERA_URL);
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


    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
//        mPresenter.initStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShown = false;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptCutLineMsg(CutLineMsg msg) {
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

    @OnClick({R.id.iv_scram, R.id.iv_take_back, R.id.iv_start, R.id.iv_identification, R.id.iv_setting, R.id.iv_back})
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
                    startActivity(new Intent(CutLineActivity.this, CutLineSettingActivity.class));
                    finish();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void webViewOnPause() {
        mAgentWebMain.getWebLifeCycle().onPause();
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

    //------------------------ 更新UI --------------------------


    public void updateInit(boolean b) {
        mTv1.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv1.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv1.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv1.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
        updateClickStatus(b);
    }

    public void updateReady(boolean b) {
        mTv2.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv2.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv2.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv2.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }


    public void updateCutStart(boolean b) {
        mTv3.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv3.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv3.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv3.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateCutStop(boolean b) {
        mTv4.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv4.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv4.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv4.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateCutReset(boolean b) {
        mTv5.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv5.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv5.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv5.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateEnd(boolean b) {
        mTv6.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv6.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv6.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv6.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }


    public void updateClickStatus(boolean b) {
        mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.yijianshouhui_normal));
        mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.kaishi_normal));
        mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.shibieluxian_normal));
        mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.shoudongshezhi_normal));
        mTvRemind.setText("剪线");
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

    public void refreshLogRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mLogData.add(currentDate + " " + msg);
        mLogAdapter.notifyDataSetChanged();
        mRlLog.scrollToPosition(mLogAdapter.getItemCount() - 1);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraLocationMsg(ChooseCameraLocationMsg msg) {
        if (isShown) {
            String code = msg.getCode();
            mPresenter.jugCameraLocationType(code);
        }

    }


    public void jumpChooseActivity(int camera, int location) {
        Intent intent = new Intent(CutLineActivity.this, ChooseActivity.class);
        intent.putExtra("activity", 3);
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
            ToastUtils.showShortToast("接收到选点命令，即将进入USB2—引流线！");

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
}
