package com.leo.robot.ui.cut_line;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ybq.android.spinkit.SpinKitView;
import com.just.agentweb.AgentWeb;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.bean.SocketStatusBean;
import com.leo.robot.bean.VisionMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.cut_line.choose.CutLineChooseLocationActivity;
import com.leo.robot.ui.setting.cut_line_setting.CutLineSettingActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.utils.DateUtils;
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
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
        }
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
        initVideo2();
        initVideo3();
        initVideo4();
        initSocketStatus();

//        mPresenter.setUnityView(mRl1);
    }

    private void initSocketStatus() {
        SPUtils socket = new SPUtils("socket");
        boolean status = socket.getBoolean("status");
        if (status) {
            mTvType.setText("与主控服务器连接成功");
            mSpinKit.setVisibility(View.GONE);
        } else {
            mTvType.setText("与主控服务器断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
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


    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShown = true;
        mPresenter.initStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShown = false;
    }

    @Override
    protected void onDestroy() {
        if (mAgentWebMain != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnDestroy();
        }
        super.onDestroy();
//        mUnityPlayer.quit();
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
    public void acceptVisionMsg(VisionMsg msg) {
        if (isShown) {
            ToastUtils.showShortToast("接收到选点命令，即将进入选点页面！");
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(CutLineActivity.this, CutLineChooseLocationActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
