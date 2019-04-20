package com.leo.robot.ui.setting.cut_line_setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ArmFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.CutLineFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ExtremityFragment;
import com.leo.robot.ui.setting.cut_line_setting.fragment.ExtremityMoveFragment;
import com.leo.robot.utils.BottomNavigationViewHelper;
import com.leo.robot.utils.CustomManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 手动设置页面
 * created by Leo on 2019/4/18 21 : 33
 */


public class CutLineSettingActivity extends NettyActivity<CutLineSettingActivityPresenter> {

    @BindView(R.id.fragment)
    FrameLayout mFragment;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private Fragment mCurrentFragment = new Fragment();
    private ArmFragment mArmFragment = new ArmFragment();
    private CutLineFragment mCutLineFragment = new CutLineFragment();
    private ExtremityFragment mExtremityFragment = new ExtremityFragment();
    private ExtremityMoveFragment mExtremityMoveFragment = new ExtremityMoveFragment();

    @Override
    protected void notifyData(String message) {

    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerCutLineSettingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_line_setting);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mExtremityFragment.setTAG(1);
        mExtremityMoveFragment.setTAG(1);
        mArmFragment.setTAG(1);
        switchFragment(mCutLineFragment).commit();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_cut_line:
                switchFragment(mCutLineFragment).commit();
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
