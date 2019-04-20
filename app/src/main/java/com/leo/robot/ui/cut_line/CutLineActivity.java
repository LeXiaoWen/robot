package com.leo.robot.ui.cut_line;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.ui.setting.SettingActivity;
import com.leo.robot.utils.MultiSampleVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.tv_ready)
    TextView mTvReady;
    @BindView(R.id.tv_cut_start)
    TextView mTvCutStart;
    @BindView(R.id.tv_cut_stop)
    TextView mTvCutStop;
    @BindView(R.id.tv_reset)
    TextView mTvReset;
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

    @Override
    protected void notifyData(String message) {

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
                    startActivity(new Intent(CutLineActivity.this, SettingActivity.class));
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

}
