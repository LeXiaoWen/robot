package com.leo.robot.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.ui.cleaning.CleaningActivity;
import com.leo.robot.ui.cut_line.CutLineActivity;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.ui.wiring.WiringActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by Leo on 2019/4/14 18 : 11
 */


public class MainActivity extends NettyActivity<MainActivityPresenter> {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.ll_wire_stripping)
    LinearLayout mLlWireStripping;
    @BindView(R.id.ll_wiring)
    LinearLayout mLlWiring;
    @BindView(R.id.ll_cut_line)
    LinearLayout mLlCutLine;
    @BindView(R.id.ll_cleaning)
    LinearLayout mLlCleaning;
    @BindView(R.id.fragment)
    FrameLayout mFragment;
    @BindView(R.id.ll_choose)
    LinearLayout mLlChoose;


    @Override
    protected void notifyData(String message) {

    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerMainActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //实时更新时间（1秒更新一次）
        mPresenter.updateTime(mTvDate);
    }

    @OnClick({R.id.ll_wire_stripping, R.id.ll_wiring, R.id.ll_cut_line, R.id.ll_cleaning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wire_stripping:
                startActivity(WireStrippingActivity.class);
                break;
            case R.id.ll_wiring:
                startActivity(WiringActivity.class);
                break;
            case R.id.ll_cut_line:
                startActivity(CutLineActivity.class);
                break;
            case R.id.ll_cleaning:
                startActivity(CleaningActivity.class);
                break;
        }
    }

    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }


}
