package com.leo.robot.ui.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.leo.robot.R;
import com.leo.robot.bean.LineLocationMsg;
import com.leo.robot.bean.LocationMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.*;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 滑台控制
 * created by Leo on 2019/4/18 22 : 06
 */


public class SlideTableFragment extends Fragment implements View.OnClickListener {
    private boolean isPause;
    private int TAG = 0;

    private TextView tvLine1;
    private TextView tvLine2;
    private TextView tvTable1;
    private TextView tvTable2;
    private Button btnLfet;
    private Button btnRight;
    private Button btnHorizontalReset;
    private Button btnUp;
    private Button btnDown;
    private Button btnVerticalReset;
    private NettyClient mMasterClient;
    private RelativeLayout mRlMain;
    private AgentWeb mAgentWeb;
    private WebView mWebView;
    private int mVideoTag = 0;
    private TextView tvVideo;

    private LinearLayout mLlErrorMain;


    public void setTAG(int TAG) {
        this.TAG = TAG;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_slide_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initVideo(UrlConstant.URL[0]);
        initClient();

    }

    private void initClient() {
        mMasterClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
            clearVideo();
            AgentWebConfig.clearDiskCache(this.getContext());


        } else {
            //Fragment显示时调用
//            webViewOnPause();
            initVideo(UrlConstant.URL[0]);
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
                .createAgentWeb()
                .ready()
                .go(url);
        WebErrorUtils webErrorUtils = new WebErrorUtils();
        webErrorUtils.errorWeb(mAgentWeb, mLlErrorMain);
        initWebSetting(mAgentWeb.getWebCreator().getWebView());
        mAgentWeb.getWebCreator().getWebView().reload();
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
//        view.setOnTouchListener(this);
    }

    private void initView(View view) {

        mRlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
        tvLine1 = (TextView) view.findViewById(R.id.tv_line1);
        tvLine2 = (TextView) view.findViewById(R.id.tv_line2);
        tvTable1 = (TextView) view.findViewById(R.id.tv_table1);
        tvTable2 = (TextView) view.findViewById(R.id.tv_table2);
        btnLfet = (Button) view.findViewById(R.id.btn_left);
        btnRight = (Button) view.findViewById(R.id.btn_right);
        btnHorizontalReset = (Button) view.findViewById(R.id.btn_horizontal_reset);
        btnUp = (Button) view.findViewById(R.id.btn_up);
        btnDown = (Button) view.findViewById(R.id.btn_down);
        btnVerticalReset = (Button) view.findViewById(R.id.btn_vertical_reset);
        tvVideo = (TextView) view.findViewById(R.id.tv_video);

        mLlErrorMain = (LinearLayout) view.findViewById(R.id.ll_error_main);


        ImageButton rb1 = (ImageButton) view.findViewById(R.id.ib_1);
        ImageButton rb2 = (ImageButton) view.findViewById(R.id.ib_2);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);

        btnLfet.setOnTouchListener(mOnDownClickListener);
        btnRight.setOnTouchListener(mOnDownClickListener);
        btnHorizontalReset.setOnTouchListener(mOnDownClickListener);
        btnUp.setOnTouchListener(mOnDownClickListener);
        btnDown.setOnTouchListener(mOnDownClickListener);
        btnVerticalReset.setOnTouchListener(mOnDownClickListener);
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
                case R.id.btn_horizontal_reset:
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.landSlideTableResetMove());

                    }
                    break;
                case R.id.btn_vertical_reset:
                    if (mMasterClient != null) {
                        mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableResetMove());

                    }
                    break;


            }
        } else if (action == MotionEvent.ACTION_UP) {//松开
            if (v.getId() == R.id.btn_left || v.getId() == R.id.btn_right || v.getId() == R.id.btn_up || v.getId() == R.id.btn_down) {
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


    @Override
    public void onPause() {
//        webViewOnPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
//        webViewOnResume();
        super.onResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        clearVideo();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        clearVideo();
        super.onDestroyView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_1:
                clearVideo();
                previousVideo();
                break;
            case R.id.ib_2:
                clearVideo();
                nextVideo();
                break;
        }
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
                videoText();
                break;
            case 1:
                initVideo(UrlConstant.URL[0]);
                mVideoTag = 0;
                videoText();
                break;
            case 2:
                initVideo(UrlConstant.URL[1]);
                mVideoTag = 1;
                videoText();
                break;
            case 3:
                initVideo(UrlConstant.URL[2]);
                mVideoTag = 2;
                videoText();
                break;
            case 4:
                initVideo(UrlConstant.URL[3]);
                mVideoTag = 3;
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
                videoText();
                break;
            case 1:
                initVideo(UrlConstant.URL[2]);
                mVideoTag = 2;
                videoText();
                break;
            case 2:
                initVideo(UrlConstant.URL[3]);
                mVideoTag = 3;
                videoText();
                break;
            case 3:
                initVideo(UrlConstant.URL[4]);
                mVideoTag = 4;
                videoText();
                break;
            case 4:
                initVideo(UrlConstant.URL[0]);
                mVideoTag = 0;
                videoText();
                break;
        }
    }

    private void clearVideo() {

        ClearWebUtils.clearVideo(mAgentWeb, getContext());
        if (mAgentWeb != null) {
            mAgentWeb = null;
        }

       RemoveViewUtils.removeView(mRlMain);

    }




    private void videoText() {
        switch (mVideoTag) {
            case 0:
                tvVideo.setText("行线画面");
                break;
            case 1:
                tvVideo.setText("引流线画面");
                break;
            case 2:
                tvVideo.setText("云台画面");
                break;
            case 3:
                tvVideo.setText("主臂画面");
                break;
            case 4:
                tvVideo.setText("从臂画面");
                break;
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
                int verticalTable = ByteUtils.strToInt(s);
                tvTable2.setText(String.valueOf(verticalTable));

            } else if ("1".equals(msg.getCode())) {//水平滑台
                int landTable = ByteUtils.strToInt(s);
                tvTable1.setText(String.valueOf(landTable));
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
            tvLine1.setText(String.valueOf(lineValue1));
            tvLine2.setText(String.valueOf(lineValue2));

        }
    }
}
