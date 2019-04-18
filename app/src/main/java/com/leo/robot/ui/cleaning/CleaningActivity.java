package com.leo.robot.ui.cleaning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 清洗绝缘子作业
 * created by Leo on 2019/4/18 10 : 45
 */


public class CleaningActivity extends NettyActivity<CleaningActivityPresenter> {
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;

    @Override
    protected void notifyData(String message) {

    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerCleaningActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        ButterKnife.bind(this);
        mPresenter.updateTime(mTvDate);
    }
}
