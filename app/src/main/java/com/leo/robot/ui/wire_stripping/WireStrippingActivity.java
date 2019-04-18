package com.leo.robot.ui.wire_stripping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.ui.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    @Override
    protected void notifyData(String message) {

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
                startActivity(new Intent(WireStrippingActivity.this, SettingActivity.class));
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
