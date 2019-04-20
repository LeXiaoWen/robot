package com.leo.robot.ui.wire_stripping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.setting.wiring_stripping_setting.WiringStrippingSettingActivity;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.MultiSampleVideo;

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
    private List<MultiSampleVideo> mMultiSampleVideos = new ArrayList<>();
    public static final String TAG = "WireStrippingActivity";


    private boolean isPause;


    @Override
    protected void notifyData(String message) {
//        byte[] bytes = ConvertCode.hexString2Bytes(message);
//        LogUtils.e(message);
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

    @OnClick({R.id.btn_scram, R.id.btn_recover, R.id.btn_start, R.id.btn_get_pic, R.id.btn_setting})
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
    public void onBackPressed() {
//        if (CustomManager.backFromWindowFull(this, listMultiNormalAdapter.getFullKey())) {
//            return;
//        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CustomManager.onPauseAll();
        isPause = true;
        LogUtils.e("暂停剥线界面");
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomManager.onResumeAll();
        isPause = false;
        LogUtils.e("恢复剥线界面");
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
}
