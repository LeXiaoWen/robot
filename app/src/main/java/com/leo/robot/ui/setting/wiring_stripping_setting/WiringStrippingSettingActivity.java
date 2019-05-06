package com.leo.robot.ui.setting.wiring_stripping_setting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ArmFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ExtremityFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ExtremityMoveFragment;
import com.leo.robot.ui.setting.wiring_stripping_setting.fragment.WiringStrippingFragment;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.unity.UnityPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by Leo on 2019/4/20 15 : 23
 */


public class WiringStrippingSettingActivity extends UnityPlayerActivity<WiringStrippingSettingActivityPresenter> {

    @BindView(R.id.fragment)
    FrameLayout mFragment;

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;


    private Fragment mCurrentFragment = new Fragment();
    private ArmFragment mArmFragment = new ArmFragment();
    private WiringStrippingFragment mWiringStrippingFragment = new WiringStrippingFragment();
    private ExtremityFragment mExtremityFragment = new ExtremityFragment();
    private ExtremityMoveFragment mExtremityMoveFragment = new ExtremityMoveFragment();

    @Override
    protected void notifyData(String message) {

    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWiringStrippingSettingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring_stripping_setting);
        ButterKnife.bind(this);
        initFragment();
        //实时更新时间（1秒更新一次）
        mPresenter.updateTime(mTvDate);
        initBroadcast(mTvGroundPower);
    }

    private void initFragment() {
        mArmFragment.setTAG(2);
        mExtremityFragment.setTAG(2);
        mExtremityMoveFragment.setTAG(2);
        switchFragment(mWiringStrippingFragment).commit();
    }


    //Fragment优化
    private FragmentTransaction switchFragment(Fragment targetFragment) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.add(R.id.fragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(mCurrentFragment)
                    .show(targetFragment);
        }
        mCurrentFragment = targetFragment;
        return transaction;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();

    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                changeStatusCliecked(mTv1, 1);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv4, 4);
                switchFragment(mWiringStrippingFragment).commit();
                break;
            case R.id.tv2:
                changeStatusCliecked(mTv2, 2);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv4, 4);
                switchFragment(mExtremityMoveFragment).commit();
                break;
            case R.id.tv3:
                changeStatusCliecked(mTv3, 3);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv4, 4);
                switchFragment(mExtremityFragment).commit();
                break;
            case R.id.tv4:
                changeStatusCliecked(mTv4, 4);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv2, 2);
                switchFragment(mArmFragment).commit();
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WiringStrippingSettingActivity.this, WireStrippingActivity.class));
                    finish();
                }
                break;
        }
    }

    /**
     * 未选中
     *
     * @author Leo
     * created at 2019/4/28 10:15 PM
     */
    private void changeStatusNormal(TextView view, int tag) {
        int color = 0;
        Drawable drawable = null;
        switch (tag) {
            case 1:
                color = getResources().getColor(R.color.setting_text_normal);
                drawable = getResources().getDrawable(R.drawable.gongju_normal);
                break;
            case 2:
                color = getResources().getColor(R.color.setting_text_normal);
                drawable = getResources().getDrawable(R.drawable.weiyidian_normal);
                break;
            case 3:
                color = getResources().getColor(R.color.setting_text_normal);
                drawable = getResources().getDrawable(R.drawable.icon212_normal);

                break;
            case 4:
                color = getResources().getColor(R.color.setting_text_normal);
                drawable = getResources().getDrawable(R.drawable.fill_normal);

                break;
        }
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        view.setCompoundDrawablePadding(10);
        view.setTextColor(color);
    }

    /**
     * 选中
     *
     * @author Leo
     * created at 2019/4/28 10:15 PM
     */

    private void changeStatusCliecked(TextView view, int tag) {
        int color = 0;
        Drawable drawable = null;
        switch (tag) {
            case 1:
                color = getResources().getColor(R.color.setting_text_clicked);
                drawable = getResources().getDrawable(R.drawable.gongju_clicked);
                break;
            case 2:
                color = getResources().getColor(R.color.setting_text_clicked);
                drawable = getResources().getDrawable(R.drawable.weiyidian_clicked);
                break;
            case 3:
                color = getResources().getColor(R.color.setting_text_clicked);
                drawable = getResources().getDrawable(R.drawable.icon212_clicked);

                break;
            case 4:
                color = getResources().getColor(R.color.setting_text_clicked);
                drawable = getResources().getDrawable(R.drawable.fill_clicked);

                break;
        }
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        view.setCompoundDrawablePadding(10);
        view.setTextColor(color);
    }
}
