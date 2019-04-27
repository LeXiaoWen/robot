package com.leo.robot.ui.wire_stripping;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.bean.WireStrippingMsg;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.wire_stripping.adapter.ActionAdapter;
import com.leo.robot.utils.CustomManager;
import com.leo.robot.utils.DateUtils;
import com.leo.robot.utils.MultiSampleVideo;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;

/**
 * 剥线作业
 * created by Leo on 2019/4/18 10 : 46
 */


public class WireStrippingActivity extends NettyActivity<WireStrippingActivityPresenter> {

    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_test)
    ImageView mIvTest;
    private List<MultiSampleVideo> mMultiSampleVideos = new ArrayList<>();
    public static final String TAG = "WireStrippingActivity";
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
    @BindView(R.id.rl_action)
    RecyclerView mRlAction;
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
    @BindView(R.id.tv_date)
    TextView mTvDate;


    private boolean isPause;
    private boolean isShown = false;
    private List<String> mData;
    private ActionAdapter mActionAdapter;


    @Override
    protected void notifyData(String message) {
//        SPUtils utils = new SPUtils(RobotInit.PUSH_KEY);
//        utils.putString(RobotInit.PUSH_MSG, message);
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWireStrippingActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wire_stripping);
        ButterKnife.bind(this);
        initTile();
        initAdapter();
        initVideo();
        initBroadcast(mTvGroundPower);
        mPresenter.initStatus();
    }

    private void initAdapter() {
        mData = new ArrayList<>();
        mActionAdapter = new ActionAdapter(this, mData);
        mRlAction.setLayoutManager(new LinearLayoutManager(this));
        mRlAction.setAdapter(mActionAdapter);
    }


    private void initVideo() {
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ic_img);
        mMultiSampleVideos.add(mPlayerMain);
        mMultiSampleVideos.add(mPlayer1);
        mMultiSampleVideos.add(mPlayer2);
        mMultiSampleVideos.add(mPlayer3);
        mMultiSampleVideos.add(mPlayer4);

        //云台画面
        mPlayerMain.setTag(TAG);
        mPlayerMain.setPlayPosition(0);
        mPlayerMain.setUp(UrlConstant.CAMERA_URL, true, "");
        mPlayerMain.setThumbImageView(imageView);

        //行线画面
        mPlayer1.setTag(TAG);
        mPlayer1.setPlayPosition(1);
        mPlayer1.setUp(UrlConstant.LINE_CAMERA_URL, true, "");
        mPlayer1.setThumbImageView(imageView);
        //引流线画面
        mPlayer2.setTag(TAG);
        mPlayer2.setPlayPosition(2);
        mPlayer2.setUp(UrlConstant.DRAIN_LINE_CAMERA_URL, true, "");
        mPlayer2.setThumbImageView(imageView);

        //机械臂画面
        mPlayer3.setTag(TAG);
        //软解码：1、打开，0、关闭
//        mPlayer3.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "videotoolbox", 0);
        mPlayer3.setPlayPosition(3);
        mPlayer3.setUp(UrlConstant.CLUTCH_CAMERA_URL, true, "");
        mPlayer3.setThumbImageView(imageView);

        //位姿仿真画面
        mPlayer4.setTag(TAG);
        mPlayer4.setPlayPosition(4);
        mPlayer4.setUp("", true, "");
        mPlayer4.setThumbImageView(imageView);

        mPlayerMain.startPlayLogic();
        mPlayer1.startPlayLogic();
        mPlayer2.startPlayLogic();
        mPlayer3.startPlayLogic();

    }

    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }


    @Override
    protected void onPause() {
        super.onPause();
        CustomManager.onPauseAll();
        isPause = true;
        isShown = false;
        LogUtils.e("暂停剥线界面");
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomManager.onResumeAll();
        isPause = false;
        isShown = true;
        LogUtils.e("恢复剥线界面");
        mPresenter.initStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomManager.clearAllVideo();
    }

    @Override
    protected void onStop() {
        onUnBindReceiver();
        super.onStop();
    }

    //------------------------ EventBus --------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptErroMsg(ErroMsg msg) {
        if (isShown) {
            showNormalDialog(this);
//            ToastUtils.showShortToast(msg.getMsg());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptWireStrippingMsg(WireStrippingMsg msg) {
        if (isShown) {
            String s = mPresenter.jugType(msg);
            if (!StringUtils.isEmpty(s)) {
                refreshRv(s);
            }
//            ToastUtils.showShortToast("接收到剥线推送消息 ： " + msg.getMsg() + "      " + msg.getCode());
        }
    }

    //------------------------ 更新UI --------------------------
    public void updateReady(boolean b) {
        mTvReady.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvReady.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvReady.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvReady.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateInit(boolean b) {
        mTvInit.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvInit.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvInit.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvInit.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));

        }
    }

    public void updateInPlace(boolean b) {
        mTvInPlace.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvInPlace.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvInPlace.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvInPlace.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClamping(boolean b) {
        mTvClamping.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvClamping.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvClamping.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvClamping.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClosure(boolean b) {
        mTvClosure.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvClosure.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvClosure.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvClosure.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updatePeeling(boolean b) {
        mTvPeeling.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvPeeling.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvPeeling.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvPeeling.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateCutOff(boolean b) {
        mTvCutOff.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvCutOff.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvCutOff.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvCutOff.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateUnlock(boolean b) {
        mTvUnlock.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvUnlock.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvUnlock.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvUnlock.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateEnd(boolean b) {
        mTvEnd.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvEnd.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvEnd.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvEnd.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    @OnClick({R.id.iv_scram, R.id.iv_take_back, R.id.iv_start, R.id.iv_identification, R.id.iv_setting, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scram:
                mPresenter.scramButton();
                break;
            case R.id.iv_take_back:
//                shotImage();
                mPlayerMain.taskShotPic(new GSYVideoShotListener() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        if (bitmap!=null){
                        mIvTest.setImageBitmap(bitmap);

                        }
                    }
                },true);
//                mPresenter.revocerButton();
                break;
            case R.id.iv_start:
                mPresenter.startButton();
                break;
            case R.id.iv_identification:
                mPresenter.getPicButton();
                break;
            case R.id.iv_setting:
                mPresenter.settingButton();
//                DetailControlActivityPermissionsDispatcher.shotImageWithPermissionCheck(DetailControlActivity.this, v);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void refreshRv(String msg) {
        String currentDate = DateUtils.getCurrentDate();
        mData.add(currentDate + " " + msg);
        mActionAdapter.notifyDataSetChanged();
        mRlAction.scrollToPosition(mActionAdapter.getItemCount() - 1);
    }


    /**
     * 更新一键收回、开始、识别路线、手动设置是否可点击
     *
     * @author Leo
     * created at 2019/4/27 2:10 AM
     */
    public void updateClickStatus(boolean b) {
        if (b) {
            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.take_back_normal));
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.start_normal));
            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.identification_normal));
            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.setting_normal));
            mTvRemind.setText("请开始选取剥线位置");
        } else {
            mIvTakeBack.setImageDrawable(getResources().getDrawable(R.drawable.take_back_unclick));
            mIvStart.setImageDrawable(getResources().getDrawable(R.drawable.start_unclick));
            mIvIdentification.setImageDrawable(getResources().getDrawable(R.drawable.identification_unclicked));
            mIvSetting.setImageDrawable(getResources().getDrawable(R.drawable.setting_unclick));
        }
    }

    /**
     * 视频截图
     * 这里没有做读写本地sd卡的权限处理，记得实际使用要加上
     */
    void shotImage() {
        //获取截图
        mPlayerMain.taskShotPic(bitmap -> {
            if (bitmap != null) {
                mIvTest.setImageBitmap(bitmap);
//                try {
//                    SaveUtils.saveBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    ToastUtils.showShortToast("save fail ");
//                    e.printStackTrace();
//                    return;
//                }
                ToastUtils.showShortToast("save success ");
            } else {
                ToastUtils.showShortToast("get bitmap fail ");
            }
        });

    }
}
