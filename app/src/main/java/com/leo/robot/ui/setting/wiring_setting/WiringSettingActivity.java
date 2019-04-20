package com.leo.robot.ui.setting.wiring_setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ArmFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ExtremityFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ExtremityMoveFragment;
import com.leo.robot.ui.setting.wiring_setting.fragment.WiringFragment;
import com.leo.robot.utils.CustomManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by Leo on 2019/4/20 15 : 24
 */


public class WiringSettingActivity extends NettyActivity<WiringSettingActivityPresenter> {
    @BindView(R.id.fragment)
    FrameLayout mFragment;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private Fragment mCurrentFragment = new Fragment();
    private ArmFragment mArmFragment = new ArmFragment();
    private WiringFragment mWiringFragment = new WiringFragment();
    private ExtremityFragment mExtremityFragment = new ExtremityFragment();
    private ExtremityMoveFragment mExtremityMoveFragment = new ExtremityMoveFragment();


    @Override
    protected void notifyData(String message) {

    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWiringSettingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring_setting);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        switchFragment(mWiringFragment).commit();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_wiring:
                switchFragment(mWiringFragment).commit();
                return true;
            case R.id.navigation_extremity_move:
                switchFragment(mExtremityMoveFragment).commit();
                return true;
            case R.id.navigation_extremity:
                switchFragment(mExtremityFragment).commit();
                return true;
            case R.id.navigation_arm:
                switchFragment(mArmFragment).commit();
                return true;
        }
        return false;
    };


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
        CustomManager.clearAllVideo();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}