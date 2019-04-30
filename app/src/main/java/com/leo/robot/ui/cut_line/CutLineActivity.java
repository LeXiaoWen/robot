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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.constant.PushMsgCode;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.setting.cut_line_setting.CutLineSettingActivity;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;

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
    @BindView(R.id.iv_ready)
    ImageView mIvReady;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.iv_init)
    ImageView mIvInit;
    @BindView(R.id.tv_3)
    TextView mTv3;
    @BindView(R.id.iv_in_place)
    ImageView mIvInPlace;
    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.iv_clamping)
    ImageView mIvClamping;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.iv_closure)
    ImageView mIvClosure;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.iv_end)
    ImageView mIvEnd;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.rl_action)
    RecyclerView mRlAction;
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
    private boolean isShown = false;

    private List<String> mData;
    private ActionAdapter mActionAdapter;
    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb1;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;
    @Override
    protected void notifyData(String message) {
        SPUtils utils = new SPUtils(RobotInit.PUSH_KEY);
        utils.putString(RobotInit.PUSH_MSG, message);
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
//        mPresenter.updateTime(mTvDate);
//        initBroadcast(mTvGroundPower);
        initStatus();

        initAdapter();
        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
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

    private void initAdapter() {
        mData = new ArrayList<>();
        mActionAdapter = new ActionAdapter(this, mData);
        mRlAction.setLayoutManager(new LinearLayoutManager(this));
        mRlAction.setAdapter(mActionAdapter);
    }



    private void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.CUT_LINE_ACTIVITY);
        boolean isReady = utils.getBoolean(PushMsgCode.CUT_READY);
        LogUtils.e("CutLineActivity  剪线 到位" + isReady);
    }


    public void updateScramText(String s) {
//        mBtnScram.setText(s);
//        ToastUtils.showShortToast(s);
    }

    public void updateStartText(String s) {
//        mBtnStart.setText(s);
//        ToastUtils.showShortToast(s);
    }

    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
    }

    @Override
    protected void onResume() {
        webViewOnResume();
        super.onResume();
        isShown = true;
    }

    @Override
    protected void onPause() {
        webViewOnPause();
        super.onPause();
        isShown = false;
    }

    @Override
    protected void onDestroy() {
        webViewOnDestroy();
        super.onDestroy();
    }

    //------------------------ EventBus --------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptErroMsg(ErroMsg msg) {
        if (isShown) {
            ToastUtils.showShortToast(msg.getMsg());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptCutLineMsg(CutLineMsg msg) {
        if (isShown) {

        }
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
                if (!mPresenter.isFastDoubleClick()){
                    mPresenter.startButton();
                }
                break;
            case R.id.iv_identification:
                break;
            case R.id.iv_setting:
                if (!mPresenter.isFastDoubleClick()){
                    startActivity(new Intent(CutLineActivity.this, CutLineSettingActivity.class));
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

}
