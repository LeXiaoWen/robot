package com.leo.robot.ui.wiring;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.WiringMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.setting.wiring_setting.WiringSettingActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.utils.DateUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;

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

    @Override
    protected void notifyData(String message) {
//        SPUtils utils = new SPUtils(RobotInit.PUSH_KEY);
//        utils.putString(RobotInit.PUSH_MSG, message);
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
        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
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
     * 位姿仿真画面
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
                .setAgentWebParent((RelativeLayout) mRl3, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
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
                .setAgentWebParent((RelativeLayout) mRl2, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
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
                .setAgentWebParent((RelativeLayout) mRl1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb1.getWebCreator().getWebView());
    }


    /**
     * 云台画面
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


    private void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRING_ACTIVITY);
        boolean isToolReady = utils.getBoolean(RobotInit.WIRING_TOOL_READY);
        LogUtils.e("WiringActivity 剥线工具到位  " + isToolReady);
    }


    @Override
    protected void onPause() {
        if (mAgentWebMain != null && mAgentWeb1 != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnPause();
            webViewOnDestroy();
            AgentWebConfig.clearDiskCache(this);
        }
        super.onPause();
        isShown = false;
    }

    @Override
    protected void onResume() {
        if (mAgentWebMain != null && mAgentWeb1 != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnResume();
        }
        super.onResume();
        mPresenter.initStatus();
        isShown = true;
    }

    @Override
    protected void onDestroy() {
        if (mAgentWebMain != null && mAgentWeb1 != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnDestroy();
        }
        super.onDestroy();
    }




    public void updateScramText(String s) {
//        mBtnScram.setText(s);
        ToastUtils.showShortToast(s);
    }

    public void updateStartText(String s) {
//        mBtnStart.setText(s);
        ToastUtils.showShortToast(s);
    }

    @Override
    protected void onStop() {
        onUnBindReceiver();

        super.onStop();
    }

    //------------------------ EventBus --------------------------

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptErroMsg(ErroMsg msg) {
        if (isShown) {
            ToastUtils.showShortToast(msg.getMsg());
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
                break;
            case R.id.iv_setting:
                if (!mPresenter.isFastDoubleClick()){
                    startActivity(new Intent(WiringActivity.this, WiringSettingActivity.class));
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
}
