package com.leo.robot.ui.wiring;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.WiringMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.ui.setting.wiring_setting.WiringSettingActivity;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.data.SPUtils;
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
    @BindView(R.id.tv_ready)
    TextView mTvReady;
    @BindView(R.id.tv_grab)
    TextView mTvGrab;
    @BindView(R.id.tv_enter)
    TextView mTvEnter;
    @BindView(R.id.tv_fixed)
    TextView mTvFixed;
    @BindView(R.id.tv_wiring_tool_ready)
    TextView mTvWiringToolReady;
    @BindView(R.id.tv_line_ready)
    TextView mTvLineReady;
    @BindView(R.id.tv_twist)
    TextView mTvTwist;
    @BindView(R.id.tv_clip_unlock)
    TextView mTvClipUnlock;
    @BindView(R.id.tv_sleeve_unlock)
    TextView mTvSleeveUnlock;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.player)
    MultiSampleVideo mPlayer;
    @BindView(R.id.btn_scram)
    Button mBtnScram;
    @BindView(R.id.btn_recover)
    Button mBtnRecover;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_get_pic)
    Button mBtnGetPic;
    @BindView(R.id.btn_setting)
    Button mBtnSetting;

    OrientationUtils orientationUtils;
    @BindView(R.id.btn_back)
    Button mBtnBack;


    private boolean isPause;

    private boolean isShown = false;

    @Override
    protected void notifyData(String message) {
        SPUtils utils = new SPUtils(RobotInit.PUSH_KEY);
        utils.putString(RobotInit.PUSH_MSG, message);
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
        initVideo();
        initBroadcast(mTvGroundPower);
        initStatus();
    }

    private void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRING_ACTIVITY);
        boolean isToolReady = utils.getBoolean(RobotInit.WIRING_TOOL_READY);
        LogUtils.e("WiringActivity 剥线工具到位  " + isToolReady);
    }

    private void initVideo() {
        mPlayer.setUp("", true, "测试视频");
        mPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        CustomManager.onPauseAll();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomManager.onResumeAll();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomManager.clearAllVideo();
    }

    @OnClick({R.id.btn_scram, R.id.btn_recover, R.id.btn_start, R.id.btn_get_pic, R.id.btn_setting,R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scram:
                mPresenter.scramButton();
                break;
            case R.id.btn_recover:
                mPresenter.revocerButton();
                break;
            case R.id.btn_start:
                mPresenter.startButton();
                break;
            case R.id.btn_get_pic:
                mPresenter.getPicButton();
                break;
            case R.id.btn_setting:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WiringActivity.this, WiringSettingActivity.class));
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public void updateScramText(String s) {
        mBtnScram.setText(s);
        ToastUtils.showShortToast(s);
    }

    public void updateStartText(String s) {
        mBtnStart.setText(s);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptWiringMsg(WiringMsg msg) {
        if (isShown) {

        }
    }
}
