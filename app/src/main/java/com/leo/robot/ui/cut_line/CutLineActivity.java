package com.leo.robot.ui.cut_line;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.constant.PushMsgCode;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.ui.setting.cut_line_setting.CutLineSettingActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;

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
    @BindView(R.id.tv_ready)
    TextView mTvReady;
    @BindView(R.id.tv_cut_start)
    TextView mTvCutStart;
    @BindView(R.id.tv_cut_stop)
    TextView mTvCutStop;
    @BindView(R.id.tv_reset)
    TextView mTvReset;
    @BindView(R.id.tv_end)
    TextView mTvEnd;

    @BindView(R.id.btn_scram)
    Button mBtnScram;
    @BindView(R.id.btn_recover)
    Button mBtnRecover;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_get_pic)
    Button mBtnGetPic;
    @BindView(R.id.btn_setting)
    Button mBtnSetting;
    @BindView(R.id.btn_back)
    Button mBtnBack;

    private boolean isShown = false;


    @Override
    protected void notifyData(String message) {
        SPUtils utils = new SPUtils(RobotInit.PUSH_KEY);
        utils.putString(RobotInit.PUSH_MSG, message);
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
        initVideo();
        mPresenter.updateTime(mTvDate);
        initBroadcast(mTvGroundPower);
        initStatus();
    }

    private void initVideo() {

    }

    private void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.CUT_LINE_ACTIVITY);
        boolean isReady = utils.getBoolean(PushMsgCode.CUT_READY);
        LogUtils.e("CutLineActivity  剪线 到位" + isReady);
    }

    @OnClick({R.id.btn_scram, R.id.btn_recover, R.id.btn_start, R.id.btn_get_pic, R.id.btn_setting, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scram:
                mPresenter.scramButton();
                break;
            case R.id.btn_recover:
                mPresenter.revocerButton();
                ToastUtils.showShortToast("一键回收");
                break;
            case R.id.btn_start:
                mPresenter.startButton();
                break;
            case R.id.btn_get_pic:
                mPresenter.getPicButton();
                ToastUtils.showShortToast("获取当前帧");
                break;
            case R.id.btn_setting:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(CutLineActivity.this, CutLineSettingActivity.class));
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public void updateScramText(String s) {
        mBtnScram.setText(s);
        ToastUtils.showShortToast(s);
    }

    public void updateStartText(String s) {
        mBtnStart.setText(s);
        ToastUtils.showShortToast(s);
    }

    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShown = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShown = false;
    }


    //------------------------ EventBus --------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptErroMsg(ErroMsg msg) {
        if (isShown) {
            ToastUtils.showShortToast(msg.getMsg());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptCutLineMsg(CutLineMsg msg) {
        if (isShown) {

        }
    }

}
