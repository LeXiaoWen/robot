package com.leo.robot.ui.choose;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebClientBase;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.LineLocationMsg;
import com.leo.robot.bean.LocationMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.NettyListener;
import com.leo.robot.netty.arm.ArmBean;
import com.leo.robot.netty.arm.ArmNettyClient;
import com.leo.robot.ui.cut_line.CutLineActivity;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.ui.wiring.WiringActivity;
import com.leo.robot.utils.*;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.develop.LogUtils;
import cree.mvp.util.ui.ToastUtils;
import io.netty.channel.Channel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    private int radioButtonTag = 1;
    private int mVideoTag = 0;
    private int x;
    private int y;
    private int mActivityTag = 0;


    private static Gson mGson = new Gson();
    private List<Integer> ports = new ArrayList<>();
    private ArmNettyClient client;

    @Override
    protected void notifyData(int status, String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvType.setText(message);
                if (status == 0) {//未连接

                    mSpinKit.setVisibility(View.VISIBLE);
                } else {//已连接
                    mSpinKit.setVisibility(View.GONE);
                }
            }
        });


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
        initBroadcast(mTvGroundPower);

        initVideoStatus();


        initSocketStatus();

        initListener();
        mPresenter.initLineLocation();


        mPresenter.initClient();
    }

    private void initVideoStatus() {
        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 1);
        int location = intent.getIntExtra("location", 1);
        mActivityTag = intent.getIntExtra("activity", 0);
        if (tag == 1) {
            initVideo(UrlConstant.URL[0]);
            if (location == 1) {
                mTvRemind.setText("请选择第一个点位");
            } else {
                mPresenter.chooseCount = 1;
                mTvRemind.setText("请选择第二个点位");
            }
        } else if (tag == 2) {
            initVideo(UrlConstant.URL[1]);
            if (location == 1) {
                mTvRemind.setText("请选择第一个点位");
            } else {
                mPresenter.chooseCount = 1;
                mTvRemind.setText("请选择第二个点位");
            }
        }
    }


    private void initTile() {
        mPresenter.updateTime(mTvDate);
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
                case R.id.btn_controle1:
                    mPresenter.controle1ActionDown(mVideoTag);
                    break;
                case R.id.btn_controle2:
                    mPresenter.controle2ActionDown(mVideoTag);
                    break;


            }
        } else if (action == MotionEvent.ACTION_UP) {//松开
            mPresenter.controleActionUp(mVideoTag);
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
                x = (int) event.getX() / 2;
                y = (int) event.getY() / 2;
                mTouchShow.setText("实时位置：(" + x + "," + y + ")");
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:

                x = (int) event.getX() / 2;
                y = (int) event.getY() / 2;
                mTouchShow.setText(x + "," + y);
//                if (mVideoTag == 0) {//水平滑台
//                    if (mMasterClient != null) {
//                        mMasterClient.sendMsgTest(CommandUtils.getLandSlideTable());
//                    }
//                } else if (mVideoTag == 1) { //垂直滑台
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
//                if (!mPresenter.isFastDoubleClick()) {
                    clearVideo();
                    previousVideo();
//                }
                break;
            case R.id.ib_2:
//                if (!mPresenter.isFastDoubleClick()) {
                    clearVideo();
                    nextVideo();
//                }
                break;
            case R.id.btn_confirm_location:
                mPresenter.sendConfirmMsg(x, y, mVideoTag, radioButtonTag);
                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    switch (mActivityTag) {
                        case 1:
                            startActivity(new Intent(ChooseActivity.this, WireStrippingActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(ChooseActivity.this, WiringActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(ChooseActivity.this, CutLineActivity.class));
                            break;
                    }
                    mAgentWeb = null;
                    mWebView = null;
                    finish();
                }
                break;
            case R.id.btn_giveup_results://舍弃结果
                mPresenter.cancelResult(radioButtonTag);
                break;
            case R.id.btn_confirm_results://确认结果
                mPresenter.confirmResult(mVideoTag);
                break;
            case R.id.btn_get_slide_table://获取滑台位置
                mPresenter.getSlideTableLocation();

                ToastUtils.showShortToast("获取滑台位置");
                break;
            case R.id.btn_confirm://确认位置
//                initMainArmNetty();
//                ToastUtils.showShortToast("确认位置");
                break;
            case R.id.btn_controle1:
//                client.sendMsg29999("hello word1");
//                ArmNettyClient client1 = (ArmNettyClient) NettyManager.getInstance().getClientByTag(RobotInit.MAIN_ARM_NETTY);
//                client1.sendMsg29999(Test.msg);
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
//                ArmNettyClient client2 = (ArmNettyClient) NettyManager.getInstance().getClientByTag(RobotInit.MAIN_ARM_NETTY);
//                client2.sendMsg30001(Test.msg);

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
     * 主机械臂
     *
     * @author Leo
     * created at 2019/6/14 8:18 PM
     */
    private void initMainArmNetty() {
        client = new ArmNettyClient();
//        NettyManager.getInstance().addNettyClient(RobotInit.MAIN_ARM_NETTY, client);
        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg, RobotInit.MAIN_ARM_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                SPUtils socket = new SPUtils("socket");

                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    notifyData(1, "与主控服务器连接成功");
                    String s = mGson.toJson(CommandUtils.getMasterControlBean());
                    NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
                    if (client != null) {
                        client.sendMsgTest(s);
                    }
                    socket.putBoolean("status", true);
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {//通信异常
                    notifyData(0, "与主控服务器连接异常，正在重连");
                    socket.putBoolean("status", false);

                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {//服务器主动断开
                    socket.putBoolean("status", false);
                    notifyData(0, "主控服务器断开连接，正在重连");
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.MAIN_ARM_NETTY_HOST, ports);//连接服务器
                    }).start();
                }
            }

            @Override
            public void onServiceHeart(Channel channel) {

            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.MAIN_ARM_NETTY_HOST, ports);//连接服务器
            }).start();
        }
    }


    /**
     * 切换视频前清除数据
     *
     * @author Leo
     * created at 2019/6/14 6:19 PM
     */
    private void clearVideo() {
//        mAgentWeb.getWebLifeCycle().onDestroy();
        mAgentWeb = null;
        mWebView = null;
//        mRlMain.removeViewAt(1);
        mRlMain.removeAllViews();
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
                mPresenter.verticalSlideTabLocation = s;
            } else if ("1".equals(msg.getCode())) {//水平滑台
                mPresenter.landSlideTabLocation = s;
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
        mPresenter.onDestroy();
        onUnBindReceiver();
        super.onDestroy();
        mAgentWeb = null;
        mWebView = null;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainArmMsg(ArmBean bean) {
        String code = bean.getCode();
        String msg = bean.getMsg();
        if (msg.length() == 2216) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    JNIUtils.getInstance().GetDataPort30003(msg, "Marm");
                    String s = JNIUtils.getInstance().ActionMove("ACTION_MOVE_1", "Marm");
                    LogUtils.e(s);

                }
            }).start();
            if (code.equals("0")) {//30003数据
                ToastUtils.showShortToast("30003数据 " + msg);
            } else if (code.equals("1")) {//29999数据
                ToastUtils.showShortToast("29999数据 " + msg);
            }
        }

    }


    /**
     * 撤销选点后提示文字
     *
     * @author Leo
     * created at 2019/6/16 4:24 PM
     */
    public void showMsg(String s) {
        mTvRemind.setText(s);
    }
}
