package com.leo.robot.ui.cleaning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;

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
    protected void notifyData(int status, String message) {
//        mTvType.setText(message);
//
//        if (status==0){//未连接
//            mSpinKit.setVisibility(View.VISIBLE);
//        }else {//已连接
//            mSpinKit.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void notifyMasterData(int status, String message) {

    }

    @Override
    protected void notifyVisionData(int status, String message) {

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
