package com.leo.robot.ui.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cree.mvp.util.ui.ToastUtils;

/**
 * created by Leo on 2019/4/15 18 : 06
 */


public class ControllerActivity extends NettyActivity<ControllerActivityPresenter> implements View.OnTouchListener {
    @BindView(R.id.touch_show_start)
    TextView mTouchShowStart;
    @BindView(R.id.touch_show)
    TextView mTouchShow;
    @BindView(R.id.ll_touch)
    LinearLayout mLlTouch;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.btn_connect)
    Button mBtnConnect;
    @BindView(R.id.ibtn_arm_up)
    ImageButton mIbtnArmUp;
    @BindView(R.id.ibtn_arm_down)
    ImageButton mIbtnArmDown;
    @BindView(R.id.ibtn_arm_left)
    ImageButton mIbtnArmLeft;
    @BindView(R.id.ibtn_arm_right)
    ImageButton mIbtnArmRight;
    @BindView(R.id.ibtn_arm_stop)
    ImageButton mIbtnArmStop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        ButterKnife.bind(this);
        mLlTouch.setOnTouchListener(this);
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerControllerActivityComponent.create().inject(this);
    }


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
    protected void notifyMasterData(int status, String message) {

    }

    @Override
    protected void notifyVisionData(int status, String message) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            /**
             * 点击的开始位置
             */
            case MotionEvent.ACTION_DOWN:
                mTouchShowStart.setText("起始位置：(" + event.getX() + "," + event.getY());
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
                mTouchShow.setText("结束位置：(" + event.getX() + "," + event.getY());
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

    @OnClick({R.id.ibtn_arm_up, R.id.ibtn_arm_down, R.id.ibtn_arm_left, R.id.ibtn_arm_right, R.id.ibtn_arm_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_arm_up:
                ToastUtils.showShortToast("机械臂末端前移！");
                break;
            case R.id.ibtn_arm_down:
                ToastUtils.showShortToast("机械臂末端后移！");
                break;
            case R.id.ibtn_arm_left:
                ToastUtils.showShortToast("机械臂末端左移！");
                break;
            case R.id.ibtn_arm_right:
                ToastUtils.showShortToast("机械臂末端右移！");
                break;
            case R.id.ibtn_arm_stop:
                ToastUtils.showShortToast("机械臂末端急停！");
                break;
        }
    }
}
