package com.leo.robot.ui.wire_stripping;

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
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.ui.wire_stripping.choose.ChooseLocationActivity;
import com.leo.robot.utils.DateUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.develop.LogUtils;

/**
 * 剥线作业
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivity extends NettyActivity<WireStrippingActivityPresenter> {

    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_test)
    ImageView mIvTest;

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


    private boolean isPause;
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
//        SPUtils utils = new SPUtils(RobotInit.PUSH_KEY);
//        utils.putString(RobotInit.PUSH_MSG, message);
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
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
        initBroadcast(mTvGroundPower);
        mPresenter.initStatus();


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
                .go("");

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
                .go("");

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
                .go("");

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
                .go("");

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
    }

    private void initAdapter() {
        mData = new ArrayList<>();
        mActionAdapter = new ActionAdapter(this, mData);
        mRlAction.setLayoutManager(new LinearLayoutManager(this));
        mRlAction.setAdapter(mActionAdapter);
    }

    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }


    @Override
    protected void onPause() {
        webViewOnPause();
        super.onPause();

        LogUtils.e("暂停剥线界面");
    }

    private void webViewOnPause() {
        mAgentWebMain.getWebLifeCycle().onPause();
        mAgentWeb1.getWebLifeCycle().onPause();
        mAgentWeb2.getWebLifeCycle().onPause();
        mAgentWeb3.getWebLifeCycle().onPause();
        mAgentWeb4.getWebLifeCycle().onPause();

    }

    @Override
    protected void onResume() {
        webViewOnResume();
        super.onResume();

        LogUtils.e("恢复剥线界面");
        mPresenter.initStatus();
    }

    private void webViewOnResume() {
        mAgentWebMain.getWebLifeCycle().onResume();
        mAgentWeb1.getWebLifeCycle().onResume();
        mAgentWeb2.getWebLifeCycle().onResume();
        mAgentWeb3.getWebLifeCycle().onResume();
        mAgentWeb4.getWebLifeCycle().onResume();
    }


    @Override
    protected void onDestroy() {
        webViewOnDestroy();
        super.onDestroy();
    }

    private void webViewOnDestroy() {
        mAgentWebMain.getWebLifeCycle().onDestroy();
        mAgentWeb1.getWebLifeCycle().onDestroy();
        mAgentWeb2.getWebLifeCycle().onDestroy();
        mAgentWeb3.getWebLifeCycle().onDestroy();
        mAgentWeb4.getWebLifeCycle().onDestroy();
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
            showNormalDialog(this);
//            ToastUtils.showShortToast(msg.getMsg());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptWireStrippingMsg(WireStrippingMsg msg) {
        if (isShown) {
            String s = mPresenter.jugType(msg);
            if (!StringUtils.isEmpty(s)) {
                refreshRv(s);
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
        }
    }

    @OnClick({R.id.iv_scram, R.id.iv_take_back, R.id.iv_start, R.id.iv_identification, R.id.iv_setting, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scram:
                mPresenter.scramButton();
                break;
            case R.id.iv_take_back:

//                mPresenter.revocerButton();
                break;
            case R.id.iv_start:
                mPresenter.startButton();
                break;
            case R.id.iv_identification:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WireStrippingActivity.this, ChooseLocationActivity.class));
                }
//                mPresenter.getPicButton();
                break;
            case R.id.iv_setting:
                mPresenter.settingButton();
//                DetailControlActivityPermissionsDispatcher.shotImageWithPermissionCheck(DetailControlActivity.this, v);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void refreshRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mData.add(currentDate + " " + msg);
        mActionAdapter.notifyDataSetChanged();
        mRlAction.scrollToPosition(mActionAdapter.getItemCount() - 1);
    }


    /**
     * 更新一键收回、开始、识别路线、手动设置是否可点击
     *
     * @author Leo
     * created at 2019/4/27 2:10 AM
     */
    public void updateClickStatus(boolean b) {
        if (b) {
            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.take_back_normal));
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.start_normal));
            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.identification_normal));
            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.setting_normal));
            mTvRemind.setText("请开始选取剥线位置");
        } else {
            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.take_back_unclick));
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.start_unclick));
            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.identification_unclicked));
            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.setting_unclick));
        }
    }


}
