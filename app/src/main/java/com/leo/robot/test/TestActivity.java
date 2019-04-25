package com.leo.robot.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.utils.MultiSampleVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {


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
    @BindView(R.id.iv_ready)
    ImageView mIvReady;
    @BindView(R.id.tv_init)
    TextView mTvInit;
    @BindView(R.id.iv_init)
    ImageView mIvInit;
    @BindView(R.id.tv_in_place)
    TextView mTvInPlace;
    @BindView(R.id.iv_in_place)
    ImageView mIvInPlace;
    @BindView(R.id.tv_clamping)
    TextView mTvClamping;
    @BindView(R.id.iv_clamping)
    ImageView mIvClamping;
    @BindView(R.id.tv_closure)
    TextView mTvClosure;
    @BindView(R.id.iv_closure)
    ImageView mIvClosure;
    @BindView(R.id.tv_peeling)
    TextView mTvPeeling;
    @BindView(R.id.iv_peeling)
    ImageView mIvPeeling;
    @BindView(R.id.tv_cut_off)
    TextView mTvCutOff;
    @BindView(R.id.iv_cut_off)
    ImageView mIvCutOff;
    @BindView(R.id.tv_unlock)
    TextView mTvUnlock;
    @BindView(R.id.iv_unlock)
    ImageView mIvUnlock;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.iv_end)
    ImageView mIvEnd;
    @BindView(R.id.player_main)
    MultiSampleVideo mPlayerMain;
    @BindView(R.id.fl_main)
    FrameLayout mFlMain;
    @BindView(R.id.lv_action)
    ListView mLvAction;
    @BindView(R.id.player1)
    MultiSampleVideo mPlayer1;
    @BindView(R.id.fl_1)
    FrameLayout mFl1;
    @BindView(R.id.player2)
    MultiSampleVideo mPlayer2;
    @BindView(R.id.fl_2)
    FrameLayout mFl2;
    @BindView(R.id.player3)
    MultiSampleVideo mPlayer3;
    @BindView(R.id.fl_3)
    FrameLayout mFl3;
    @BindView(R.id.player4)
    MultiSampleVideo mPlayer4;
    @BindView(R.id.fl_4)
    FrameLayout mFl4;
    @BindView(R.id.iv_scram)
    ImageView mIvScram;
    @BindView(R.id.iv_take_back)
    ImageView mIvTakeBack;
    @BindView(R.id.iv_start)
    ImageView mIvStart;
    @BindView(R.id.iv_identification)
    ImageView mIvIdentification;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;

    private boolean isScramClick = false;
    private boolean isTakeBackClick = false;
    private boolean isStartClick = false;
    private boolean isIdentificationClick=false;
    private boolean isSettingClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wire_stripping1);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_scram, R.id.iv_take_back, R.id.iv_start, R.id.iv_identification, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scram:
                if (!isScramClick) {
                    mIvScram.setImageDrawable(getResources().getDrawable(R.drawable.emergency_stop_clicked));
                    isScramClick = true;
                }else {
                    mIvScram.setImageDrawable(getResources().getDrawable(R.drawable.emergency_stop_normal));
                    isScramClick = false;
                }
                break;
            case R.id.iv_take_back:
                if (!isTakeBackClick) {
                    mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.take_back_clicked));
                    isTakeBackClick = true;
                }else {
                    mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.take_back_normal));
                    isTakeBackClick = false;
                }
                break;
            case R.id.iv_start:
                if (!isStartClick) {
                    mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.start_clicked));
                    isStartClick = true;
                }else {
                    mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.start_normal));
                    isStartClick = false;
                }
                break;
            case R.id.iv_identification:
                if (!isIdentificationClick) {
                    mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.identification_clicked));
                    isIdentificationClick = true;
                }else {
                    mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.identification_normal));
                    isIdentificationClick = false;
                }
                break;
            case R.id.iv_setting:
                if (!isSettingClick) {
                    mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.setting_clicked));
                    isSettingClick = true;
                }else {
                    mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.setting_normal));
                    isSettingClick = false;
                }
                break;
        }
    }
}
