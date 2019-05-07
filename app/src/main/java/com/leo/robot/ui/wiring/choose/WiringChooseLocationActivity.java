package com.leo.robot.ui.wiring.choose;

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
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.ui.wiring.WiringActivity;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.MiddlewareWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.develop.LogUtils;

/**
 * created by Leo on 2019/5/3 17 : 40
 */


public class WiringChooseLocationActivity extends NettyActivity<WiringChooseLocationActivityPresenter> implements View.OnTouchListener {

    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.tv_3)
    TextView mTv3;
    @BindView(R.id.iv_3)
    ImageView mIv3;
    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.iv_4)
    ImageView mIv4;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.iv_5)
    ImageView mIv5;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.iv_6)
    ImageView mIv6;
    @BindView(R.id.tv_7)
    TextView mTv7;
    @BindView(R.id.iv_7)
    ImageView mIv7;
    @BindView(R.id.tv_8)
    TextView mTv8;
    @BindView(R.id.iv_8)
    ImageView mIv8;
    @BindView(R.id.tv_9)
    TextView mTv9;
    @BindView(R.id.iv_9)
    ImageView mIv9;
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

    @Override
    protected void notifyData(String message) {

    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerWiringChooseLocationActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring_choose_location);
        ButterKnife.bind(this);
        initTile();
        initMainVideo();
        initVideo1();
        initVideo2();
        initVideo3();
        initVideo4();
        mPresenter.initStatus();
        mWebView = mAgentWebMain.getWebCreator().getWebView();
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

    //------------------------ 更新UI --------------------------
    public void updateReady(boolean b) {
        mTv1.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv1.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (b) {
            mTv1.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv1.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateGrab(boolean isGrab) {
        mTv2.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv2.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isGrab) {
            mTv2.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv2.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateEnter(boolean isEnter) {
        mTv3.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv3.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isEnter) {
            mTv3.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv3.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateFixed(boolean isFixed) {
        //没有，预留
        mTv4.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv4.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isFixed) {
            mTv4.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv4.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateToolReady(boolean isToolReady) {
        //没有，预留
    }

    public void updateLineReady(boolean isLineReady) {
        mTv5.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv5.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isLineReady) {
            mTv5.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv5.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateTwist(boolean isTwist) {
        mTv6.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv6.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isTwist) {
            mTv6.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv6.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateClipUnlock(boolean isClipUnlock) {

        mTv7.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv7.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isClipUnlock) {
            mTv7.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv7.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }

    public void updateSleeveUnlock(boolean isSleeveUnlock) {
        mTv8.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv8.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isSleeveUnlock) {
            mTv8.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv8.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }

    }

    public void updateEnd(boolean isEnd) {
        mTv9.setTextColor(getResources().getColor(R.color.color_status_normal));
        mIv9.setImageDrawable(getResources().getDrawable(R.drawable.push_status_normal));
        if (isEnd) {
            mTv9.setTextColor(getResources().getColor(R.color.color_status_wake_up));
            mIv9.setImageDrawable(getResources().getDrawable(R.drawable.push_status_wakeup));
        }
    }


    private void initTile() {
        mPresenter.updateTime(mTvDate);
    }

    @OnClick({R.id.iv_get_pic, R.id.iv_confirm_location, R.id.iv_back})
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
                    startActivity(new Intent(WiringChooseLocationActivity.this, WiringActivity.class));
                    finish();
                }
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


}
