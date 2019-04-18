package com.leo.robot.ui.cut_line;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
}
