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
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.MiddlewareWebClientBase;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.LineLocationMsg;
import com.leo.robot.bean.LocationMsg;
import com.leo.robot.bean.MasterPowerDataMsg;
import com.leo.robot.constant.URConstants;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.arm.ArmNettyClient;
import com.leo.robot.netty.arm.MainArmBean;
import com.leo.robot.ui.cut_line.CutLineActivity;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.ui.wiring.WiringActivity;
import com.leo.robot.utils.*;
import com.leo.robot.view.DrawView;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.data.StringUtils;
import cree.mvp.util.develop.LogUtils;
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
    @BindView(R.id.tv_video)
    TextView mTvVideo;
    @BindView(R.id.tv_type1)
    TextView mTvType1;
    @BindView(R.id.spin_kit1)
    SpinKitView mSpinKit1;
    @BindView(R.id.ll_status1)
    LinearLayout mLlStatus1;
    @BindView(R.id.tv_wiring)
    TextView mTvWiring;
    @BindView(R.id.tv_wire_stripping)
    TextView mTvWireStripping;
    @BindView(R.id.tv_claw)
    TextView mTvClaw;
    @BindView(R.id.tv_cut_line)
    TextView mTvCutLine;
    @BindView(R.id.tv_con)
    TextView mTvCon;
    @BindView(R.id.ll_1)
    LinearLayout mLl1;
    @BindView(R.id.ll_error_main)
    LinearLayout mLlErrorMain;
    private AgentWeb mAgentWeb;
    //    private WebView mWebView;
    private float mNewScale;
    private int radioButtonTag = 1;
    private int mVideoTag = 0;
    private int x;
    private int y;
    private int mActivityTag = 0;


    private static Gson mGson = new Gson();
    private List<Integer> ports = new ArrayList<>();
    private ArmNettyClient client;
    private DrawView mDrawView;
    private String mode = "Marm";

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
    protected void notifyMasterData(int status, String message) {

    }

    @Override
    protected void notifyVisionData(int status, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvType1.setText(message);
                if (status == 0) {
                    mSpinKit1.setVisibility(View.VISIBLE);
                } else {
                    mSpinKit1.setVisibility(View.GONE);
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

        showMsg("请选择行线作业点");
        mPresenter.initClient();
        mDrawView = new DrawView(this);
        mPresenter.updatePower();
    }

    private void initVideoStatus() {
        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 1);
        int location = intent.getIntExtra("location", 1);
        mActivityTag = intent.getIntExtra("activity", 0);
        if (tag == 1) {
            initVideo(UrlConstant.URL[0]);
            mVideoTag = 0;
            changeControleUi(1);
            videoText();
            if (location == 1) {
                radioButtonCheckedStatus(true, false);
            } else {
                radioButtonCheckedStatus(false, true);
                mPresenter.chooseCount = 1;
            }
            showMsg("请选择行线作业点");
        } else if (tag == 2) {
            initVideo(UrlConstant.URL[1]);
            mVideoTag = 1;
            changeControleUi(2);
            videoText();
            if (location == 1) {
                radioButtonCheckedStatus(true, false);

            } else {
                radioButtonCheckedStatus(false, true);
                mPresenter.chooseCount = 1;
            }
            showMsg("请选择引流线作业点");
        }
    }

    private void radioButtonCheckedStatus(boolean b, boolean b2) {
        mRb1.setChecked(b);
        mRb2.setChecked(b2);
        if (!b) {
            radioButtonTag = 2;
        } else {
            radioButtonTag = 1;
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
        SPUtils socket = new SPUtils("masterSocket");
        SPUtils socket1 = new SPUtils("visionSocket");
        boolean status1 = socket1.getBoolean("status");
        boolean status = socket.getBoolean("status");
        if (status) {
            mTvType.setText("与主控服务器连接成功");
            mSpinKit.setVisibility(View.GONE);

        } else {
            mTvType.setText("与主控服务器断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
        }

        if (status1) {
            mTvType1.setText("与视觉服务器连接成功");
            mSpinKit1.setVisibility(View.GONE);
        } else {
            mTvType1.setText("与视觉服务器断开连接，正在重连");
            mSpinKit1.setVisibility(View.VISIBLE);
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

        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb, mLlErrorMain);

        initWebSetting(mAgentWeb.getWebCreator().getWebView());
        mAgentWeb.getWebCreator().getWebView().reload();
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
                mTouchShow.setText(x + "," + y);
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
                DrawView drawView = new DrawView(this);
//                drawView.setLayoutParams(new ViewGroup.LayoutParams(640,480));
                drawView.currentY = (int) event.getY();
                drawView.currentX = (int) event.getX();
                //添加标记（控件）
                mRlMain.addView(drawView);
                //添加标记（删除）
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(() -> mRlMain.removeView(drawView));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                x = (int) event.getX() / 2;
                y = (int) event.getY() / 2;
                mTouchShow.setText(x + "," + y);

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
//                    mWebView = null;
                    finish();
                }
                break;
            case R.id.btn_giveup_results://舍弃结果
                mPresenter.cancelResult(mVideoTag);
                radioButtonCheckedStatus(true, false);
                break;
            case R.id.btn_confirm_results://确认结果
                mPresenter.confirmResult(mVideoTag);
                break;
            case R.id.btn_get_slide_table://获取滑台位置
                mPresenter.getSlideTableLocation();

//                ToastUtils.showShortToast("获取滑台位置");
                break;
            case R.id.btn_confirm://确认位置
                mPresenter.confirmLocation(mVideoTag);

                break;

        }
    }

    private void clearVideo() {
        ClearWebUtils.clearVideo(mAgentWeb, this);
        if (mAgentWeb != null) {
            mAgentWeb = null;
        }

        RemoveViewUtils.removeView(mRlMain);
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
                videoText();
                break;
            case 1:
                initVideo(UrlConstant.URL[0]);
                mVideoTag = 0;
                changeControleUi(1);
                videoText();
                break;
            case 2:
                initVideo(UrlConstant.URL[1]);
                mVideoTag = 1;
                changeControleUi(2);
                videoText();
                break;
            case 3:
                initVideo(UrlConstant.URL[2]);
                mVideoTag = 2;
                changeControleUi(3);
                videoText();
                break;
            case 4:
                initVideo(UrlConstant.URL[3]);
                mVideoTag = 3;
                changeControleUi(3);
                videoText();
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
                videoText();
                break;
            case 1:
                initVideo(UrlConstant.URL[2]);
                mVideoTag = 2;
                changeControleUi(3);
                videoText();
                break;
            case 2:
                initVideo(UrlConstant.URL[3]);
                mVideoTag = 3;
                changeControleUi(3);
                videoText();
                break;
            case 3:
                initVideo(UrlConstant.URL[4]);
                mVideoTag = 4;
                changeControleUi(3);
                videoText();
                break;
            case 4:
                initVideo(UrlConstant.URL[0]);
                mVideoTag = 0;
                changeControleUi(1);
                videoText();
                break;
        }
    }

    private void videoText() {
        switch (mVideoTag) {
            case 0:
                mTvVideo.setText("行线画面");
                break;
            case 1:
                mTvVideo.setText("引流线画面");
                break;
            case 2:
                mTvVideo.setText("云台画面");
                break;
            case 3:
                mTvVideo.setText("主臂画面");
                break;
            case 4:
                mTvVideo.setText("从臂画面");
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
                mBtnControle1.setText("向左");
                mBtnControle2.setText("向右");
                mRb1.setText("行线第一点");
                mRb2.setText("行线第二点");
                showOrHidden(false);
                switch (radioButtonTag) {
                    case 1:
                        showMsg("请选择行线画面第一个点位");
                        break;
                    case 2:
                        showMsg("请选择行线画面第二个点位");
                        break;
                }
                break;
            case 2://引流线
                mBtnControle1.setText("向上");
                mBtnControle2.setText("向下");
                mRb1.setText("引流线第一点");
                mRb2.setText("引流线第二点");
                showOrHidden(false);
                switch (radioButtonTag) {
                    case 1:
                        showMsg("请选择引流线画面第一个点位");
                        break;
                    case 2:
                        showMsg("请选择引流线画面第二个点位");
                        break;
                }
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
                int verticalTable = ByteUtils.strToInt(s);
                mTvTable2.setText(String.valueOf(verticalTable));

            } else if ("1".equals(msg.getCode())) {//水平滑台
                mPresenter.landSlideTabLocation = s;
                int landTable = ByteUtils.strToInt(s);
                mTvTable1.setText(String.valueOf(landTable));
            }
//            ToastUtils.showShortToast("成功获取滑台位置");

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

            int lineValue1 = ByteUtils.strToInt(line1);
            int lineValue2 = ByteUtils.strToInt(line2);
            mTvLine1.setText(String.valueOf(lineValue1));
            mTvLine2.setText(String.valueOf(lineValue2));

        }
    }


    @Override
    protected void onDestroy() {
//        if (mAgentWeb != null) {
//            mAgentWeb.getWebLifeCycle().onPause();
//        }
        mPresenter.destroyClient();
        mPresenter.onDestroy();
        super.onDestroy();
//        mAgentWeb = null;
//        mWebView = null;
        clearVideo();
        AgentWebConfig.clearDiskCache(this);
        onUnBindReceiver();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainArmMsg(MainArmBean bean) {
        String code = bean.getCode();
        String msg = bean.getMsg();
        if (msg.length() == 2216) {
            handlerMsg(msg);
        }

    }

    private void handlerMsg(String msg) {
        JNIUtils.GetDataPort30003(msg, mode);

        String actionMove = JNIUtils.ActionMove("ACTION_MOVE_1", mode);
        LogUtils.e("actionMove  " + actionMove);

        String actionJoint = JNIUtils.ActionJoint("ACTION_J0_1", mode);
        LogUtils.e("actionJoint  " + actionJoint);

        String actionDash = JNIUtils.ActionDash("CMD_POWER_ON", mode);
        LogUtils.e("actionDash   " + actionDash);

        String actionPose = JNIUtils.ActionPose("ACTION_POSE_1", mode);
        LogUtils.e("actionPose   " + actionPose);

        String actionStop = JNIUtils.ActionStopJ(mode);
        LogUtils.e("actionStop   " + actionStop);

        String urParams = JNIUtils.ReadURparam("Act_Y", mode);
        LogUtils.e("urParams   " + urParams);


    }


    /**
     * 提示文字
     *
     * @author Leo
     * created at 2019/6/16 4:24 PM
     */
    public void showMsg(String s) {
        mTvRemind.setText(s);
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
        updatePw(ownPower, Wire_Stripper_Ma, Connect_Wire_Ma, Cut_Wire_Ma, Hand_Grab_Ma);

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
