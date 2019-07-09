package com.leo.robot.ui.setting.cut_line_setting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.MasterPowerDataMsg;
import com.leo.robot.bean.SocketStatusBean;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.URConstants;
import com.leo.robot.netty.arm.MainArmBean;
import com.leo.robot.ui.cut_line.CutLineActivity;
import com.leo.robot.ui.setting.fragment.*;
import com.leo.robot.ui.setting.wiring_stripping_setting.WiringStrippingSettingActivity;
import com.leo.robot.utils.PowerUtils;
import cree.mvp.util.data.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 手动设置页面
 * created by Leo on 2019/4/18 21 : 33
 */


public class CutLineSettingActivity extends NettyActivity<CutLineSettingActivityPresenter> {

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
    @BindView(R.id.tv6)
    Button mTv6;
    @BindView(R.id.tv7)
    Button mTv7;
    @BindView(R.id.tv8)
    Button mTv8;
    @BindView(R.id.tv_wiring)
    TextView mTvWiring;
    @BindView(R.id.tv_wire_stripping)
    TextView mTvWireStripping;
    @BindView(R.id.tv_claw)
    TextView mTvClaw;
    @BindView(R.id.tv_cut_line)
    TextView mTvCutLine;

    private boolean isShown = false;
    private Fragment mCurrentFragment = new Fragment();
    private ArmFragment mArmFragment = new ArmFragment();
    private CutLineFragment mCutLineFragment = new CutLineFragment();
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
        DaggerCutLineSettingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_line_setting);
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

        mPresenter.updatePower();
    }

    private void initFragment() {
        mExtremityFragment.setTAG(1);
        mExtremityMoveFragment.setTAG(1);
        mArmFragment.setTAG(1);
        switchFragment(mCutLineFragment).commit();
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
        mPresenter.onDestroy();
        super.onDestroy();
        AgentWebConfig.clearDiskCache(this);
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

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                changeStatusCliecked(mTv1, 1);
                changeStatusNormal(mTv2, 2);
                changeStatusNormal(mTv3, 3);
                changeStatusNormal(mTv4, 4);
                changeStatusNormal(mTv5, 5);
                switchFragment(mCutLineFragment).commit();
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
            case R.id.tv6:
                mPresenter.continueWork();
                break;
            case R.id.tv7:
                mPresenter.undoException();
                break;
            case R.id.tv8:
                Intent intent = new Intent(CutLineSettingActivity.this, WiringStrippingSettingActivity.class);
                intent.putExtra("tag", 2);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    if (mIntentTag == 1) {
                        startActivity(new Intent(CutLineSettingActivity.this, WiringStrippingSettingActivity.class));
                    } else {
                        startActivity(new Intent(CutLineSettingActivity.this, CutLineActivity.class));
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainArmMsg(MainArmBean bean) {
        String code = bean.getCode();
        String msg = bean.getMsg();
        if (msg.length() == 2216) {
            if (code.equals("0")) {//30003数据
                handler30003Msg(msg);
            } else if (code.equals("1")) {//29999数据
                handler29999Msg(msg);
            }
        }
    }

    /**
     * 主臂30003端口数据
     *
     * @author Leo
     * created at 2019/6/19 11:18 PM
     */
    private void handler29999Msg(String msg) {
        JNIUtils.GetDataPort29999(msg, URConstants.Marm);
    }

    /**
     * 主臂29999端口数据
     *
     * @author Leo
     * created at 2019/6/19 11:18 PM
     */
    private void handler30003Msg(String msg) {
        JNIUtils.GetDataPort30003(msg, URConstants.Marm);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateOwnPower(MasterPowerDataMsg msg) {
        String code = msg.getCode();
        String ownPower = PowerUtils.getPowerByType(code, URConstants.Master_Power_Ma);
        //剥线工具电量
        String Wire_Stripper_Ma = PowerUtils.getPowerByType(code, URConstants.Wire_Stripper_Ma);
        //接线工具电量
        String Connect_Wire_Ma = PowerUtils.getPowerByType(code, URConstants.Connect_Wire_Ma);
        //剪线工具电量
        String Cut_Wire_Ma = PowerUtils.getPowerByType(code, URConstants.Cut_Wire_Ma);
        //手爪工具电量
        String Hand_Grab_Ma = PowerUtils.getPowerByType(code, URConstants.Hand_Grab_Ma);
        updatePw(ownPower,Wire_Stripper_Ma,Connect_Wire_Ma,Cut_Wire_Ma,Hand_Grab_Ma);

    }

    public void updatePw(String ownPower, String wire_Stripper_Ma, String connect_Wire_Ma, String cut_Wire_Ma, String hand_Grab_Ma) {
        if (!StringUtils.isEmpty(ownPower)) {
            mTvOwnPower.setText(ownPower);
        }

        if (!StringUtils.isEmpty(wire_Stripper_Ma)) {
            mTvWireStripping.setText(wire_Stripper_Ma);
        }
        if (!StringUtils.isEmpty(connect_Wire_Ma)) {
            mTvWiring.setText(connect_Wire_Ma);
        }
        if (!StringUtils.isEmpty(cut_Wire_Ma)) {
            mTvCutLine.setText(cut_Wire_Ma);
        }
        if (!StringUtils.isEmpty(hand_Grab_Ma)) {
            mTvClaw.setText(hand_Grab_Ma);
        }
    }

    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
    }
}
