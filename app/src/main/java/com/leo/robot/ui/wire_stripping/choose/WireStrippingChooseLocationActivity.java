package com.leo.robot.ui.wire_stripping.choose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.MiddlewareWebClientBase;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.GetPicBean;
import com.leo.robot.bean.VisionMsg;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.MiddlewareWebViewClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.develop.LogUtils;

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
    @BindView(R.id.iv_get_pic)
    ImageView mIvGetPic;
    @BindView(R.id.iv_confirm_location)
    ImageView mIvConfirmLocation;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.touch_show)
    TextView mTouchShow;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.rl_show)
    RelativeLayout mRlShow;

    private AgentWeb mAgentWebMain;
    private AgentWeb mAgentWeb1;
    private AgentWeb mAgentWeb4;
    private AgentWeb mAgentWeb3;
    private AgentWeb mAgentWeb2;
    private WebView mWebView;
    private float mOldScale;
    private float mNewScale;

    private float x;
    private float y;

    private MiddlewareWebClientBase mMiddleWareWebClient;

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
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWireStrippingChooseLocationActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        ButterKnife.bind(this);
        initTile();
        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
        mPresenter.initStatus();
    }


    /**
     * 位姿仿真画面
     *
     * @author Leo
     * created at 2019/4/27 5:27 PM
     */
    private void initVideo4() {
        mAgentWeb4 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl4, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb4.getWebCreator().getWebView());
    }

    /**
     * 机械臂画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo3() {
        mAgentWeb3 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl3, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb3.getWebCreator().getWebView());
    }

    /**
     * 引流线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo2() {
        mAgentWeb2 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl2, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb2.getWebCreator().getWebView());
    }

    /**
     * 行线画面
     *
     * @author Leo
     * created at 2019/4/27 5:26 PM
     */
    private void initVideo1() {
        mAgentWeb1 = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRl1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);

        initWebSetting(mAgentWeb1.getWebCreator().getWebView());
    }

    @SuppressLint("NewApi")
    private void initMainVideo() {
        mAgentWebMain = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlMain, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .useMiddlewareWebClient(getMiddlewareWebClient())
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);
        mWebView = mAgentWebMain.getWebCreator().getWebView();

        initMainWebSetting(mAgentWebMain.getWebCreator().getWebView());
    }

    private void initShowVideo(){
        AgentWeb agentWebShow = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mRlShow, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .useMiddlewareWebClient(getMiddlewareWebClient())
                .createAgentWeb()
                .ready()
                .go(UrlConstant.CAMERA_URL);
        initMainWebSetting(agentWebShow.getWebCreator().getWebView());
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

        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setLoadWithOverviewMode(true);
        //缩放
//        view.getSettings().setBuiltInZoomControls(true);
    }

    private void initMainWebSetting(WebView view) {
        //取消滚动条
        view.setHorizontalScrollBarEnabled(false);
        view.setVerticalScrollBarEnabled(false);
        //自适应屏幕
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setLoadWithOverviewMode(true);
        //缩放操作
        view.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        view.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        view.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件

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
                mTouchShow.setText("实时位置：(" + event.getX() + "," + event.getY());
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
                x = event.getX() / 2 * mNewScale;
                y = event.getY() / 2 * mNewScale;
                mTouchShow.setText(event.getX() + "," + event.getY());
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

    @OnClick({R.id.iv_get_pic
            , R.id.iv_confirm_location
            , R.id.iv_back
            , R.id.rl_main
            , R.id.rl_1
            , R.id.rl_2
            , R.id.rl_3
            , R.id.rl_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_get_pic:
                scaleController(false);
                mAgentWebMain.getWebCreator().getWebView().setOnTouchListener(this);
                break;
            case R.id.iv_confirm_location:
                String x1;
                String y1;
                mWebView.setOnTouchListener(null);
                scaleController(true);
                if ("0.0".equals(String.valueOf(mNewScale))) {
                    x1 = String.valueOf(x);
                    y1 = String.valueOf(y);
                    GetPicBean bean = CommandUtils.getPicBean1(x1 + "," + y1);
                    LogUtils.e(bean.toString());

                } else {
                    x1 = String.valueOf(x / mNewScale);
                    y1 = String.valueOf(y / mNewScale);
                    GetPicBean bean = CommandUtils.getPicBean1(x1 + "," + y1);
                    LogUtils.e(bean.toString());

                }

                break;
            case R.id.iv_back:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(new Intent(WireStrippingChooseLocationActivity.this, WireStrippingActivity.class));
                    finish();
                }
                break;
            case R.id.rl_main:
//                if (mAgentWebMain!=null&&mAgentWeb1!=null&&mAgentWeb2!=null&&mAgentWeb3!=null&&mAgentWeb4!=null){
//                    webViewOnPause();
//                    webViewOnDestroy();
//                }
//                mRlMain.setVisibility(View.GONE);
//                mRl1.setVisibility(View.GONE);
//                mRl2.setVisibility(View.GONE);
//                mRl3.setVisibility(View.GONE);
//                mRl4.setVisibility(View.GONE);
//                initShowVideo();
                break;
            case R.id.rl_1:
                break;
            case R.id.rl_2:
                break;
            case R.id.rl_3:
                break;
            case R.id.rl_4:
                break;
        }
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
        if (mAgentWebMain != null && mAgentWeb1 != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnPause();
            webViewOnDestroy();
            AgentWebConfig.clearDiskCache(this);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAgentWebMain != null && mAgentWeb1 != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWebMain != null && mAgentWeb1 != null && mAgentWeb2 != null && mAgentWeb3 != null && mAgentWeb4 != null) {
            webViewOnDestroy();
        }
        super.onDestroy();
    }

    private void webViewOnPause() {
        mAgentWebMain.getWebLifeCycle().onPause();
        mAgentWeb1.getWebLifeCycle().onPause();
        mAgentWeb2.getWebLifeCycle().onPause();
        mAgentWeb3.getWebLifeCycle().onPause();
        mAgentWeb4.getWebLifeCycle().onPause();

    }

    private void webViewOnResume() {
        mAgentWebMain.getWebLifeCycle().onResume();
        mAgentWeb1.getWebLifeCycle().onResume();
        mAgentWeb2.getWebLifeCycle().onResume();
        mAgentWeb3.getWebLifeCycle().onResume();
        mAgentWeb4.getWebLifeCycle().onResume();
    }

    private void webViewOnDestroy() {
        mAgentWebMain.getWebLifeCycle().onDestroy();
        mAgentWeb1.getWebLifeCycle().onDestroy();
        mAgentWeb2.getWebLifeCycle().onDestroy();
        mAgentWeb3.getWebLifeCycle().onDestroy();
        mAgentWeb4.getWebLifeCycle().onDestroy();
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


}
