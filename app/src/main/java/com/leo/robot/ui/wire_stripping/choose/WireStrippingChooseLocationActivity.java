package com.leo.robot.ui.wire_stripping.choose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ybq.android.spinkit.SpinKitView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebClientBase;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.LineLocationMsg;
import com.leo.robot.bean.LocationMsg;
import com.leo.robot.bean.VisionMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.utils.ByteUtils;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.MiddlewareWebViewClient;
import com.leo.robot.utils.NettyManager;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * created by Leo on 2019/4/27 11 : 27
 */


public class WireStrippingChooseLocationActivity extends NettyActivity<WireStrippingChooseLocationActivityPresenter> implements View.OnTouchListener {

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

    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.rl_1)
    RelativeLayout mRl1;

    @BindView(R.id.rl_2)
    RelativeLayout mRl2;

    @BindView(R.id.rl_3)
    RelativeLayout mRl3;

    @BindView(R.id.rl_4)
    RelativeLayout mRl4;
    @BindView(R.id.tv_remind)
    TextView mTvRemind;

    @BindView(R.id.btn_confirm_location)
    Button mBtnConfirmLocation;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.touch_show)
    TextView mTouchShow;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.rl_show)
    RelativeLayout mRlShow;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;

    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.btn_right)
    Button mBtnRight;
    @BindView(R.id.btn_reset)
    Button mBtnReset;

    @BindView(R.id.btn_up)
    Button mBtnUp;
    @BindView(R.id.btn_down)
    Button mBtnDown;
    @BindView(R.id.btn_reset1)
    Button mBtnReset1;
    @BindView(R.id.btn_switch)
    Button mBtnSwitch;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.ll_level)
    LinearLayout mLlLevel;
    @BindView(R.id.tv_vertical)
    TextView mTvVertical;
    @BindView(R.id.ll_vertical)
    LinearLayout mLlVertical;

    @BindView(R.id.rb1)
    RadioButton mRb1;
    @BindView(R.id.rb2)
    RadioButton mRb2;
    @BindView(R.id.rb3)
    RadioButton mRb3;
    @BindView(R.id.rb4)
    RadioButton mRb4;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.btn_confirm_results)
    Button mBtnConfirmResults;
    @BindView(R.id.btn_giveup_results)
    Button mBtnGiveupResults;
    @BindView(R.id.btn_confirm_slide_table)
    Button mBtnConfirmSlideTable;
    @BindView(R.id.tv_line1)
    TextView mTvLine1;
    @BindView(R.id.tv_line2)
    TextView mTvLine2;


    private AgentWeb mAgentWebMain;

    private WebView mWebView;
    private float mOldScale;
    private float mNewScale;
    private List<AgentWeb> mAgentWebList = new ArrayList<>();

    private int x;
    private int y;
    //水平滑台位置
    private String landSlideTabLocation = "05000000";
    //垂直滑台位置
    private String verticalSlideTabLocation = "08000000";
    private int switchTag = 1;


    public NettyClient mMasterClient;
    public NettyClient mVisionClient;
    private boolean isSwitch = false;
    //第几次选点
    private int chooseCount = 0;

    //发送选点信息
    private StringBuilder location;
    private static byte[] msg = new byte[4];
    private int radioButtonTag = 1;

    @Override
    protected void notifyData(int status, String message) {
        mTvType.setText(message);

        if (status == 0) {//未连接
            updateReady(false);
            updateInit(false);
            updateInPlace(false);
            updateClamping(false);
            updateClosure(false);
            updatePeeling(false);
            updateCutOff(false);
            updateUnlock(false);
            updateEnd(false);
            mSpinKit.setVisibility(View.VISIBLE);
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWireStrippingChooseLocationActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        ButterKnife.bind(this);
        initTile();
        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 1);
        int location = intent.getIntExtra("location", 1);
        if (tag == 1) {
            usb1();
            if (location == 1) {
                mTvRemind.setText("请选择第一个点位");
            } else {
                chooseCount = 1;
                mTvRemind.setText("请选择第二个点位");
            }
        } else {
            usb2();
            if (location == 1) {
                mTvRemind.setText("请选择第一个点位");
            } else {
                chooseCount = 1;
                mTvRemind.setText("请选择第二个点位");
            }
        }

        mPresenter.initStatus();
        initSocketStatus();

        initClient();

        initListener();

        initLineLocation();
    }

    /**
     * 实时请求行线、引流线距离
     *
     * @author Leo
     * created at 2019/5/30 9:06 PM
     */
    private void initLineLocation() {
        mTimer.schedule(mTimerTask, 1000, 500);//延时1s，每隔500毫秒执行一次run方法
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.lineLocation());
                }
            }
            super.handleMessage(msg);
        }
    };

    Timer mTimer = new Timer();
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


    private void initClient() {
        mMasterClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
        mVisionClient = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
    }

    private void initListener() {
        mBtnLeft.setOnTouchListener(mOnDownClickListener);
        mBtnRight.setOnTouchListener(mOnDownClickListener);
        mBtnUp.setOnTouchListener(mOnDownClickListener);
        mBtnDown.setOnTouchListener(mOnDownClickListener);
        mRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb1:
                    radioButtonTag = 1;
                    break;
                case R.id.rb2:
                    radioButtonTag = 2;
                    break;
                case R.id.rb3:
                    radioButtonTag = 3;
                    break;
                case R.id.rb4:
                    radioButtonTag = 4;
                    break;

            }
        });
    }

    private View.OnTouchListener mOnDownClickListener = (v, event) -> {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) { //按下
            switch (v.getId()) {
                case R.id.btn_right:
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.landSlideTableRightMove());
                    }
                    break;
                case R.id.btn_left:
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.landSlideTableLeftMove());
                    }
                    break;

                case R.id.btn_up:
                    if (mMasterClient != null) {

                        mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableUpMove());

                    }
                    break;
                case R.id.btn_down:
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableDownMove());

                    }
                    break;


            }
        } else if (action == MotionEvent.ACTION_UP) {//松开
            if (v.getId() == R.id.btn_left || v.getId() == R.id.btn_right || v.getId() == R.id.btn_reset) {
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.landSlideTableStopMove());
                }
            } else {
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableStopMove());
                }
            }

        }
        return false;
    };

    private void initSocketStatus() {
        SPUtils socket = new SPUtils("socket");
        boolean status = socket.getBoolean("status");
        if (status) {
            mTvType.setText("与主控服务器连接成功");
            mSpinKit.setVisibility(View.GONE);

        } else {
            mTvType.setText("与主控服务器断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("NewApi")
    private void initMainVideo(String url) {
        mAgentWebMain = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .useMiddlewareWebClient(getMiddlewareWebClient())
                .createAgentWeb()
                .ready()
                .go(url);
        mWebView = mAgentWebMain.getWebCreator().getWebView();

        initMainWebSetting(mAgentWebMain.getWebCreator().getWebView());
    }


    /**
     * 获取缩放比例
     *
     * @author Leo
     * created at 2019/5/3 4:13 PM
     */

    protected MiddlewareWebClientBase getMiddlewareWebClient() {
        return new MiddlewareWebViewClient() {
            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
                mNewScale = newScale;
                LogUtils.e("缩放比例 ： " + newScale);
            }
        };
    }


    private void initMainWebSetting(WebView view) {
        //取消滚动条
        view.setHorizontalScrollBarEnabled(false);
        view.setVerticalScrollBarEnabled(false);
        //自适应屏幕
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setLoadWithOverviewMode(true);
        //缩放操作
//        view.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        view.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        view.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
        view.setOnTouchListener(this);
    }


    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }

    //------------------------ 更新UI --------------------------
    public void updateReady(boolean b) {
        mTvReady.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIvReady.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTvReady.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIvReady.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
            mTvRemind.setText("请选择剥线位置第一点");
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

    public void updateClickStatus(boolean b) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            /**
             * 点击的开始位置
             */
            case MotionEvent.ACTION_DOWN:
//                mTouchShowStart.setText("起始位置：(" + event.getX() + "," + event.getY());
                break;
            /**
             * 触屏实时位置
             */
            case MotionEvent.ACTION_MOVE:
//                x = (int) event.getX() / 2;
//                y = (int) event.getY() / 2;
//                mTouchShow.setText("实时位置：(" + x + "," + y + ")");
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
//                x = event.getX() / 2 * mNewScale;
//                y = event.getY() / 2 * mNewScale;
                x = (int) event.getX() / 2;
                y = (int) event.getY() / 2;
                mTouchShow.setText(x + "," + y);
                if (switchTag == 1) {//水平滑台
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.getLandSlideTable());
                    }
                } else if (switchTag == 2) { //垂直滑台
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.getVerticalSlideTable());
                    }
                }
                break;
            default:
                break;
        }
        /**
         *  注意返回值
         *  true：view继续响应Touch操作；
         *  false：view不再响应Touch操作，故此处若为false，只能显示起始位置，不能显示实时位置和结束位置
         */
        return true;
    }

    @OnClick({
            R.id.btn_confirm_location
            , R.id.iv_back
            , R.id.btn_switch
            , R.id.btn_giveup_results
            , R.id.btn_confirm_results
            , R.id.btn_confirm_slide_table
            , R.id.btn_reset
            , R.id.btn_reset1

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_confirm_location:
                sendConfirmMsg();
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WireStrippingChooseLocationActivity.this, WireStrippingActivity.class));
                    finish();
                }
                break;

            case R.id.btn_switch:
                mTvRemind.setText(null);
                for (AgentWeb agentWeb : mAgentWebList) {
                    agentWeb.destroy();
                }
                mAgentWebList.clear();
                mAgentWebMain = null;
                mWebView = null;
                mRlMain.removeViewAt(1);
                if (!isSwitch) {
                    usb2();
                } else {
                    usb1();
                }
                break;

            case R.id.btn_giveup_results:
                cancelResult();
                break;
            case R.id.btn_confirm_results:
                confirmResult();
                break;
            case R.id.btn_confirm_slide_table:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.confirmSlideTable());
                }
                break;
            case R.id.btn_reset:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.landSlideTableResetMove());
                }
                break;
            case R.id.btn_reset1:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableResetMove());

                }
                break;


        }
    }

    private void cancelResult() {
        if (mVisionClient != null) {

            switch (radioButtonTag) {
                case 1:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CANCEL);
                    break;
                case 2:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CANCEL);
                    break;
                case 3:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CANCEL);
                    break;
                case 4:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CANCEL);
                    break;
            }
            chooseCount = 0;
            mTvRemind.setText("已撤销选点，请选择第一个点位");

        } else {
            ToastUtils.showShortToast("未连接视觉服务器");

        }
    }

    /**
    * 确认描点结局
    *
    *@author Leo
    *created at 2019/5/30 10:46 PM
    */

    private void confirmResult() {
        if (mVisionClient != null) {
            switch (radioButtonTag) {
                case 1:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CONFIRM);
                    break;
                case 2:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CONFIRM);
                    break;
                case 3:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CONFIRM);
                    break;
                case 4:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CONFIRM);
                    break;
            }
        }

    }

    private void sendConfirmMsg() {
        StringBuilder s;
        String x = ByteUtils.numToHex32(this.x);
        String y = ByteUtils.numToHex32(this.y);
        String location = ByteUtils.stringLowToHight(x, y);

        if (mVisionClient != null) {
            switch (radioButtonTag) {
                case 1:
                    s = new StringBuilder();
                    s.append(CommandUtils.CAMERA1_LOCATION1);
                    s.append(location);
                    s.append(landSlideTabLocation);
                    s.append("FF");
                    mVisionClient.sendMsgTest(s.toString());
                    break;
                case 2:
                    s = new StringBuilder();
                    s.append(CommandUtils.CAMERA1_LOCATION2);
                    s.append(location);
                    s.append(landSlideTabLocation);
                    s.append("FF");
                    mVisionClient.sendMsgTest(s.toString());
                    break;
                case 3:
                    s = new StringBuilder();
                    s.append(CommandUtils.CAMERA2_LOCATION1);
                    s.append(location);
                    s.append(verticalSlideTabLocation);
                    s.append("FF");
                    mVisionClient.sendMsgTest(s.toString());
                    break;
                case 4:
                    s = new StringBuilder();
                    s.append(CommandUtils.CAMERA2_LOCATION2);
                    s.append(location);
                    s.append(verticalSlideTabLocation);
                    s.append("FF");
                    mVisionClient.sendMsgTest(s.toString());
                    break;
            }
        } else {
            ToastUtils.showShortToast("未连接视觉服务器");
        }

    }


    /**
     * 确认选点
     *
     * @author Leo
     * created at 2019/5/29 10:00 PM
     */
    private void confirmLocation() {
        location = new StringBuilder();
        chooseCount++;


        if (chooseCount == 1) {//第一次选点
            String s = locationMsg(chooseCount);

            if (switchTag == 1 && landSlideTabLocation != null) {
                if (mVisionClient != null) {
                    mVisionClient.sendMsgTest(s);
                }
            } else if (switchTag == 2 && verticalSlideTabLocation != null) {
                if (mVisionClient != null) {
                    mVisionClient.sendMsgTest(s);
                }
            }
            mTvRemind.setText("请选择第二个点位");

            LogUtils.e("发送选点1信息 ： " + s);
        } else if (chooseCount == 2) {//第二次选点
            String s = locationMsg(chooseCount);

            if (switchTag == 1 && landSlideTabLocation != null) {
                if (mVisionClient != null) {
                    mVisionClient.sendMsgTest(s);
                }
            } else if (switchTag == 2 && verticalSlideTabLocation != null) {
                if (mVisionClient != null) {
                    mVisionClient.sendMsgTest(s);
                }
            }
            chooseCount = 0;
            LogUtils.e("发送选点2信息 ： " + s);
            mTvRemind.setText("选择点位完成");
        }
    }

    private void usb1() {
        mBtnSwitch.setText("引流线识别");
        isSwitch = false;
        mTvVertical.setVisibility(View.GONE);
        mLlVertical.setVisibility(View.GONE);
        mTvLevel.setVisibility(View.VISIBLE);
        mLlLevel.setVisibility(View.VISIBLE);
        switchTag = 1;

        initMainVideo(UrlConstant.LINE_CAMERA_URL);
    }

    private void usb2() {
        mBtnSwitch.setText("行线识别");
        isSwitch = true;
        mTvVertical.setVisibility(View.VISIBLE);
        mLlVertical.setVisibility(View.VISIBLE);
        mTvLevel.setVisibility(View.GONE);
        mLlLevel.setVisibility(View.GONE);
        switchTag = 2;

        initMainVideo(UrlConstant.DRAIN_LINE_CAMERA_URL);
    }

    /**
     * 是否缩放
     *
     * @author Leo
     * created at 2019/5/2 5:11 PM
     */
    private void scaleController(boolean isScale) {
        mAgentWebMain.getWebCreator().getWebView().getSettings().setSupportZoom(isScale); //支持缩放，默认为true。是下面那个的前提。
        mAgentWebMain.getWebCreator().getWebView().getSettings().setBuiltInZoomControls(isScale); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mAgentWebMain.getWebCreator().getWebView().getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
    }


    @Override
    protected void onPause() {
        if (mAgentWebMain != null) {
            webViewOnPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAgentWebMain != null) {
            webViewOnResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWebMain != null) {
            webViewOnDestroy();
        }
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }

    private void webViewOnPause() {
        mAgentWebMain.getWebLifeCycle().onPause();
//        mAgentWeb1.getWebLifeCycle().onPause();
//        mAgentWeb2.getWebLifeCycle().onPause();
//        mAgentWeb3.getWebLifeCycle().onPause();
//        mAgentWeb4.getWebLifeCycle().onPause();

    }

    private void webViewOnResume() {
        mAgentWebMain.getWebLifeCycle().onResume();
//        mAgentWeb1.getWebLifeCycle().onResume();
//        mAgentWeb2.getWebLifeCycle().onResume();
//        mAgentWeb3.getWebLifeCycle().onResume();
//        mAgentWeb4.getWebLifeCycle().onResume();
    }

    private void webViewOnDestroy() {
        mAgentWebMain.getWebLifeCycle().onDestroy();
//        mAgentWeb1.getWebLifeCycle().onDestroy();
//        mAgentWeb2.getWebLifeCycle().onDestroy();
//        mAgentWeb3.getWebLifeCycle().onDestroy();
//        mAgentWeb4.getWebLifeCycle().onDestroy();
    }


    /**
     * 接收视觉服务器base64数据
     *
     * @author Leo
     * created at 2019/4/28 7:57 PM
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVisionMsg(VisionMsg msg) {
        LogUtils.e("图片数据 ： " + msg.getMsg());

    }

    /**
     * 主控服务器回复滑台位置
     *
     * @author Leo
     * created at 2019/5/30 10:09 PM
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationMsg(LocationMsg msg) {
        if (msg.getMsg() != null && msg.getMsg().length() > 5) {
            String s = msg.getMsg().substring(8, 16);
            if ("0".equals(msg.getCode())) {//垂直滑台
                verticalSlideTabLocation = s;
            } else if ("1".equals(msg.getCode())) {//水平滑台
                landSlideTabLocation = s;
            }
            ToastUtils.showShortToast("成功获取滑台位置");

        }
    }

    /**
     * 主控服务器行线、引流线位置
     *
     * @author Leo
     * created at 2019/5/30 10:09 PM
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLineLocationMsg(LineLocationMsg msg) {

        if (msg.getCode() != null && msg.getCode().length() > 8) {
            String s = msg.getCode().substring(8, 24);
            String line1 = s.substring(0, 8);
            String line2 = s.substring(8, 16);
            byte[] line11 = ByteUtils.hex2byte(line1);
            byte[] line22 = ByteUtils.hex2byte(line2);

            int lineValue1 = ByteUtils.byteArrayToInt(line11);
            int lineValue2 = ByteUtils.byteArrayToInt(line22);

            mTvLine1.setText(String.valueOf(lineValue1));
            mTvLine2.setText(String.valueOf(lineValue2));

        }
    }


    private String locationMsg(int chooseCount) {
        String x = ByteUtils.numToHex32(this.x);
        String y = ByteUtils.numToHex32(this.y);
        String y1 = y.substring(0, 2);
        String y2 = y.substring(2, 4);
        String y3 = y.substring(4, 6);
        String y4 = y.substring(6, 8);
        String x1 = x.substring(0, 2);
        String x2 = x.substring(2, 4);
        String x3 = x.substring(4, 6);
        String x4 = x.substring(6, 8);

        if (switchTag == 1) {//水平滑台
            location.append("640210");
        } else if (switchTag == 2) {//垂直滑台
            location.append("640610");
        }

        if (chooseCount == 1) {
            location.append("01000000");
        } else if (chooseCount == 2) {
            location.append("02000000");
        }
        location.append(x4);
        location.append(x3);
        location.append(x2);
        location.append(x1);
        location.append(y4);
        location.append(y3);
        location.append(y2);
        location.append(y1);
        if (switchTag == 1) {
            location.append(landSlideTabLocation);
        } else if (switchTag == 2) {
            location.append(verticalSlideTabLocation);
        }
        location.append("FF");

        return location.toString();
    }

}
