package com.leo.robot.ui.setting.wiring_setting;

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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.SocketStatusBean;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.ui.setting.fragment.*;
import com.leo.robot.ui.setting.wiring_stripping_setting.WiringStrippingSettingActivity;
import com.leo.robot.ui.wiring.WiringActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * created by Leo on 2019/4/20 15 : 24
 */


public class WiringSettingActivity extends NettyActivity<WiringSettingActivityPresenter> {
    @BindView(R.id.fragment)
    FrameLayout mFragment;
    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.tv5)
    TextView mTv5;

    private boolean isShown = false;
    private Fragment mCurrentFragment = new Fragment();
    private ArmFragment mArmFragment = new ArmFragment();
    private WiringFragment mWiringFragment = new WiringFragment();
    private ExtremityFragment mExtremityFragment = new ExtremityFragment();
    private ExtremityMoveFragment mExtremityMoveFragment = new ExtremityMoveFragment();
    private SlideTableFragment mSlideTableFragment = new SlideTableFragment();
    private int mIntentTag;

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
        DaggerWiringSettingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring_setting);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        mIntentTag = intent.getIntExtra("tag", 0);
        initFragment();
        //实时更新时间（1秒更新一次）
        mPresenter.updateTime(mTvDate);
        initBroadcast(mTvGroundPower);

        mPresenter.initClient();
        //实时请求行线、引流线距离
        mPresenter.initLineLocation();

    }

    private void initFragment() {
        mArmFragment.setTAG(1);
        mExtremityFragment.setTAG(1);
        mExtremityMoveFragment.setTAG(1);
        switchFragment(mWiringFragment).commit();
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
        mPresenter.destroyClient();
        super.onDestroy();
        onUnBindReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShown = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShown = true;
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                changeStatusCliecked(mTv1, 1);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv4, 4);
                changeStatusNormal(mTv5, 5);
                switchFragment(mWiringFragment).commit();
                break;
            case R.id.tv2:
                changeStatusCliecked(mTv2, 2);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv4, 4);
                changeStatusNormal(mTv5, 5);
                switchFragment(mExtremityMoveFragment).commit();
                break;
            case R.id.tv3:
                changeStatusCliecked(mTv3, 3);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv4, 4);
                changeStatusNormal(mTv5, 5);
                switchFragment(mExtremityFragment).commit();
                break;
            case R.id.tv4:
                changeStatusCliecked(mTv4, 4);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv5, 5);
                switchFragment(mArmFragment).commit();
                break;
            case R.id.tv5:
                changeStatusCliecked(mTv5, 5);
                changeStatusNormal(mTv1, 1);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv4, 4);
                switchFragment(mSlideTableFragment).commit();
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    if (mIntentTag == 1) {
                        startActivity(new Intent(WiringSettingActivity.this, WiringStrippingSettingActivity.class));
                    } else {
                        startActivity(new Intent(WiringSettingActivity.this, WiringActivity.class));
                    }
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
            case 5:
                color = getResources().getColor(R.color.setting_text_normal);
                drawable = getResources().getDrawable(R.drawable.weiyidian_normal);
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
            case 5:
                color = getResources().getColor(R.color.setting_text_clicked);
                drawable = getResources().getDrawable(R.drawable.weiyidian_clicked);
                break;
        }
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        view.setCompoundDrawablePadding(10);
        view.setTextColor(color);
    }

    /**
     * socket连接状态信息
     *
     * @author Leo
     * created at 2019/5/24 1:44 AM
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void socketStatus(SocketStatusBean bean) {
        String type = bean.getType();
        String code = bean.getCode();
        if (isShown) {
            if (type.equals(RobotInit.MASTER_CONTROL_NETTY)) {//主控服务器
                if ("0".equals(code)) {//连接失败或断开连接

                } else if ("1".equals(code)) {//连接成功

                }
            } else if (type.equals(RobotInit.VISION_NETTY)) {//视觉服务器
                if ("0".equals(code)) {//连接失败或断开连接

                } else if ("1".equals(code)) {//连接成功

                }
            }
        }
    }
}
