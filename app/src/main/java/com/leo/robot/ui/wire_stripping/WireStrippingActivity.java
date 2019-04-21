package com.leo.robot.ui.wire_stripping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.setting.wiring_stripping_setting.WiringStrippingSettingActivity;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;

/**
 * 剥线作业
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivity extends NettyActivity<WireStrippingActivityPresenter> {
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
    @BindView(R.id.tv_init)
    TextView mTvInit;
    @BindView(R.id.tv_in_place)
    TextView mTvInPlace;
    @BindView(R.id.tv_clamping)
    TextView mTvClamping;
    @BindView(R.id.tv_closure)
    TextView mTvClosure;
    @BindView(R.id.tv_peeling)
    TextView mTvPeeling;
    @BindView(R.id.tv_cut_off)
    TextView mTvCutOff;
    @BindView(R.id.tv_unlock)
    TextView mTvUnlock;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
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
    @BindView(R.id.player_main)
    MultiSampleVideo mPlayerMain;
    @BindView(R.id.player1)
    MultiSampleVideo mPlayer1;
    @BindView(R.id.player2)
    MultiSampleVideo mPlayer2;
    @BindView(R.id.player3)
    MultiSampleVideo mPlayer3;
    @BindView(R.id.player4)
    MultiSampleVideo mPlayer4;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    private List<MultiSampleVideo> mMultiSampleVideos = new ArrayList<>();
    public static final String TAG = "WireStrippingActivity";


    private boolean isPause;
    private boolean isShown = false;


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
        initVideo();
        initBroadcast(mTvGroundPower);
        mPresenter.initStatus();
    }


    private void initVideo() {

        mMultiSampleVideos.add(mPlayerMain);
        mMultiSampleVideos.add(mPlayer1);
        mMultiSampleVideos.add(mPlayer2);
        mMultiSampleVideos.add(mPlayer3);
        mMultiSampleVideos.add(mPlayer4);


        for (int i = 0; i < mMultiSampleVideos.size(); i++) {
            mMultiSampleVideos.get(i).setPlayTag(TAG);
            mMultiSampleVideos.get(i).setPlayPosition(i);
            if (i == 0) {
                mMultiSampleVideos.get(i).setUp(UrlConstant.URL_TEST1, true, "测试");
            } else {
                mMultiSampleVideos.get(i).setUp(UrlConstant.URL_TEST, true, "测试");
            }
            mMultiSampleVideos.get(i).startPlayLogic();
        }
    }

    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }

    @OnClick({R.id.btn_scram, R.id.btn_recover, R.id.btn_start, R.id.btn_get_pic, R.id.btn_setting, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scram:
                mPresenter.scramButton();
                break;
            case R.id.btn_recover:
                mPresenter.revocerButton();
                ToastUtils.showShortToast("一键回收");
                break;
            case R.id.btn_start:
                mPresenter.startButton();
                break;
            case R.id.btn_get_pic:
                mPresenter.getPicButton();
                ToastUtils.showShortToast("获取当前帧");
                break;
            case R.id.btn_setting:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WireStrippingActivity.this, WiringStrippingSettingActivity.class));
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

//    @Override
//    public void onBackPressed() {
////        if (CustomManager.backFromWindowFull(this, listMultiNormalAdapter.getFullKey())) {
////            return;
////        }
//        super.onBackPressed();
//        finish();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        CustomManager.onPauseAll();
        isPause = true;
        isShown = false;
        LogUtils.e("暂停剥线界面");
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomManager.onResumeAll();
        isPause = false;
        isShown = true;
        LogUtils.e("恢复剥线界面");
        mPresenter.initStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomManager.clearAllVideo();
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
    public void acceptWireStrippingMsg(WireStrippingMsg msg) {
        if (isShown) {
            mPresenter.jugType(msg);
            ToastUtils.showShortToast("接收到剥线推送消息 ： " + msg.getMsg() + "      "  +msg.getCode());
        }
    }

    //------------------------ 更新UI --------------------------
    public void updateReady(boolean b) {
        mTvReady.setBackgroundColor(Color.RED);
        if (b) {
            mTvReady.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateInit(boolean b) {
        mTvInit.setBackgroundColor(Color.RED);
        if (b) {
            mTvInit.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateInPlace(boolean b) {
        mTvInPlace.setBackgroundColor(Color.RED);
        if (b) {
            mTvInPlace.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateClamping(boolean b) {
        mTvClamping.setBackgroundColor(Color.RED);
        if (b) {
            mTvClamping.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateClosure(boolean b) {
        mTvClosure.setBackgroundColor(Color.RED);
        if (b) {
            mTvClosure.setBackgroundColor(Color.GREEN);
        }
    }

    public void updatePeeling(boolean b) {
        mTvPeeling.setBackgroundColor(Color.RED);
        if (b) {
            mTvPeeling.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateCutOff(boolean b) {
        mTvCutOff.setBackgroundColor(Color.RED);
        if (b) {
            mTvCutOff.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateUnlock(boolean b) {
        mTvUnlock.setBackgroundColor(Color.RED);
        if (b) {
            mTvUnlock.setBackgroundColor(Color.GREEN);
        }
    }

    public void updateEnd(boolean b) {
        mTvEnd.setBackgroundColor(Color.RED);
        if (b) {
            mTvEnd.setBackgroundColor(Color.GREEN);
        }
    }
}
