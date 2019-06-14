package com.leo.robot.ui.choose;

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
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.ByteUtils;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.MiddlewareWebViewClient;
import com.leo.robot.utils.NettyManager;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 选点新页面
 * created by Leo on 2019/6/13 23 : 26
 */


public class ChooseActivity extends NettyActivity<ChooseActivityPresenter> implements View.OnTouchListener {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.tv_line1)
    TextView mTvLine1;
    @BindView(R.id.tv_line2)
    TextView mTvLine2;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.ib_1)
    ImageButton mIb1;
    @BindView(R.id.ib_2)
    ImageButton mIb2;
    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    @BindView(R.id.tv_table1)
    TextView mTvTable1;
    @BindView(R.id.tv_table2)
    TextView mTvTable2;
    @BindView(R.id.btn_controle1)
    Button mBtnControle1;
    @BindView(R.id.btn_controle2)
    Button mBtnControle2;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.ll_level)
    LinearLayout mLlLevel;
    @BindView(R.id.rb1)
    RadioButton mRb1;
    @BindView(R.id.rb2)
    RadioButton mRb2;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.touch_show)
    TextView mTouchShow;
    @BindView(R.id.btn_get_slide_table)
    Button mBtnGetSlideTable;
    @BindView(R.id.btn_confirm_location)
    Button mBtnConfirmLocation;
    @BindView(R.id.btn_confirm_results)
    Button mBtnConfirmResults;
    @BindView(R.id.btn_giveup_results)
    Button mBtnGiveupResults;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.tv_no)
    TextView mTvNo;
    @BindView(R.id.rl_)
    RelativeLayout mRl;
    private AgentWeb mAgentWeb;
    private WebView mWebView;
    private float mNewScale;
    public NettyClient mMasterClient;
    public NettyClient mVisionClient;
    private int radioButtonTag = 1;
    private int mVideoTag = 0;
    private int x;
    private int y;
    //水平滑台位置
    private String landSlideTabLocation = "05000000";
    //垂直滑台位置
    private String verticalSlideTabLocation = "08000000";
    //第几次选点
    private int chooseCount = 0;

    @Override
    protected void notifyData(int status, String message) {
        mTvType.setText(message);

        if (status == 0) {//未连接

            mSpinKit.setVisibility(View.VISIBLE);
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerChooseActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
        initTile();
        initSocketStatus();
        initClient();
        initVideo(UrlConstant.URL[0]);
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

    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }

    private void initClient() {
        mMasterClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
        mVisionClient = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
    }

    private void initListener() {
        mBtnControle1.setOnTouchListener(mOnDownClickListener);
        mBtnControle2.setOnTouchListener(mOnDownClickListener);

        mRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb1:
                    radioButtonTag = 1;
                    break;
                case R.id.rb2:
                    radioButtonTag = 2;
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

//                case R.id.btn_up:
//                    if (mMasterClient != null) {
//
//                        mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableUpMove());
//
//                    }
//                    break;
//                case R.id.btn_down:
//                    if (mMasterClient != null) {
//                        mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableDownMove());
//
//                    }
//                    break;


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


    /**
     * socket状态显示
     *
     * @author Leo
     * created at 2019/6/14 12:08 AM
     */
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

    /**
     * 初始化video
     *
     * @author Leo
     * created at 2019/6/14 12:24 AM
     */
    private void initVideo(String url) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .useMiddlewareWebClient(getMiddlewareWebClient())
                .createAgentWeb()
                .ready()
                .go(url);
        mWebView = mAgentWeb.getWebCreator().getWebView();

        initWebSetting(mAgentWeb.getWebCreator().getWebView());
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

    private void initWebSetting(WebView view) {
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

//                x = (int) event.getX() / 2;
//                y = (int) event.getY() / 2;
//                mTouchShow.setText(x + "," + y);
//                if (switchTag == 1) {//水平滑台
//                    if (mMasterClient != null) {
//                        mMasterClient.sendMsgTest(CommandUtils.getLandSlideTable());
//                    }
//                } else if (switchTag == 2) { //垂直滑台
//                    if (mMasterClient != null) {
//                        mMasterClient.sendMsgTest(CommandUtils.getVerticalSlideTable());
//                    }
//                }
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

    @OnClick({R.id.ib_1,
            R.id.ib_2,
            R.id.btn_confirm_location,
            R.id.iv_back,
            R.id.btn_giveup_results,
            R.id.btn_confirm_results,
            R.id.btn_get_slide_table,
            R.id.btn_confirm,
            R.id.btn_controle1,
            R.id.btn_controle2

    })
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ib_1:
                clearVideo();
                previousVideo();
                break;
            case R.id.ib_2:
                clearVideo();
                nextVideo();
                break;
            case R.id.btn_confirm_location:
                sendConfirmMsg();
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    finish();
                }
                break;
            case R.id.btn_giveup_results://舍弃结果
                cancelResult();
                break;
            case R.id.btn_confirm_results://确认结果
                confirmResult();
                break;
            case R.id.btn_get_slide_table://获取滑台位置
                ToastUtils.showShortToast("获取滑台位置");
                break;
            case R.id.btn_confirm://确认位置
                ToastUtils.showShortToast("确认位置");
                break;
            case R.id.btn_controle1:
                switch (mVideoTag) {
                    case 0://行线
                        ToastUtils.showShortToast("向上");
                        break;
                    case 1://引流线
                        ToastUtils.showShortToast("向左");
                        break;
                }
                break;
            case R.id.btn_controle2:
                switch (mVideoTag) {
                    case 0://行线
                        ToastUtils.showShortToast("向下");
                        break;
                    case 1://引流线
                        ToastUtils.showShortToast("向右");
                        break;
                }
                break;
        }
    }


    /**
     * 确认描点结果
     *
     * @author Leo
     * created at 2019/5/30 10:46 PM
     */

    private void confirmResult() {
        if (mVisionClient != null) {
            switch (mVideoTag) {
                case 1:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CONFIRM);
                    break;
                case 2:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CONFIRM);
                    break;
            }
        }

    }

    /**
     * 切换视频前清除数据
     *
     * @author Leo
     * created at 2019/6/14 6:19 PM
     */
    private void clearVideo() {
        mAgentWeb = null;
        mWebView = null;
        mRlMain.removeViewAt(1);
    }

    /**
     * 上一个视频
     *
     * @author Leo
     * created at 2019/6/14 6:14 PM
     */
    private void previousVideo() {

        switch (mVideoTag) {
            case 0:
                initVideo(UrlConstant.URL[4]);
                mVideoTag = 4;
                changeControleUi(3);
                break;
            case 1:
                initVideo(UrlConstant.URL[0]);
                mVideoTag = 0;
                changeControleUi(1);
                break;
            case 2:
                initVideo(UrlConstant.URL[1]);
                mVideoTag = 1;
                changeControleUi(2);
                break;
            case 3:
                initVideo(UrlConstant.URL[2]);
                mVideoTag = 2;
                changeControleUi(3);
                break;
            case 4:
                initVideo(UrlConstant.URL[3]);
                mVideoTag = 3;
                changeControleUi(3);
                break;
        }
    }

    /**
     * 下一个视频
     *
     * @author Leo
     * created at 2019/6/14 6:15 PM
     */
    private void nextVideo() {

        switch (mVideoTag) {
            case 0:
                initVideo(UrlConstant.URL[1]);
                mVideoTag = 1;
                changeControleUi(2);
                break;
            case 1:
                initVideo(UrlConstant.URL[2]);
                mVideoTag = 2;
                changeControleUi(3);
                break;
            case 2:
                initVideo(UrlConstant.URL[3]);
                mVideoTag = 3;
                changeControleUi(3);
                break;
            case 3:
                initVideo(UrlConstant.URL[4]);
                mVideoTag = 4;
                changeControleUi(3);
                break;
            case 4:
                initVideo(UrlConstant.URL[0]);
                mVideoTag = 0;
                changeControleUi(1);
                break;
        }
    }

    /**
     * 行线或引流线UI
     *
     * @author Leo
     * created at 2019/6/14 6:23 PM
     */
    private void changeControleUi(int tag) {
        switch (tag) {
            case 1://行线
                mBtnControle1.setText("向上");
                mBtnControle2.setText("向下");
                mRb1.setText("行线第一点");
                mRb2.setText("行线第二点");
                showOrHidden(false);
                break;
            case 2://引流线
                mBtnControle1.setText("向左");
                mBtnControle2.setText("向右");
                mRb1.setText("引流线第一点");
                mRb2.setText("引流线第二点");
                showOrHidden(false);
                break;
            case 3:
                showOrHidden(true);
                break;
        }
    }

    private void showOrHidden(boolean isShow) {
        mLlLevel.setVisibility(View.VISIBLE);
        mRl.setVisibility(View.VISIBLE);
        mTvNo.setVisibility(View.GONE);
        if (isShow) {
            mLlLevel.setVisibility(View.GONE);
            mRl.setVisibility(View.GONE);
            mTvNo.setVisibility(View.VISIBLE);
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

    private void sendConfirmMsg() {
        StringBuilder s;
        String x = ByteUtils.numToHex32(this.x);
        String y = ByteUtils.numToHex32(this.y);
        String location = ByteUtils.stringLowToHight(x, y);

        if (mVisionClient != null) {
            switch (radioButtonTag) {
                case 1:
                    switch (mVideoTag) {
                        case 0://行线
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA1_LOCATION1);
                            s.append(location);
                            s.append(landSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            break;
                        case 1://引流线
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA2_LOCATION1);
                            s.append(location);
                            s.append(verticalSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            break;
                    }

                    break;
                case 2:
                    switch (mVideoTag) {
                        case 0:
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA1_LOCATION2);
                            s.append(location);
                            s.append(landSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            break;
                        case 1:
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA2_LOCATION2);
                            s.append(location);
                            s.append(verticalSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            break;
                    }

                    break;
            }
        } else {
            ToastUtils.showShortToast("未连接视觉服务器");
        }

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


    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onDestroy();
        mAgentWeb = null;
        mWebView = null;
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }
}
