package com.leo.robot.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * created by Leo on 2019/4/20 18 : 08
 */


@SuppressLint("AppCompatCustomView")
public class MyImageButtonView extends ImageButton {
    private static boolean isLongClickModule = false;
    float  startX = 0;
    float  startY= 0;
    Timer timer = null;
    private OnMyLongClickListener mOnLongClickListener;
    private OnUpListener mOnUpListener;

    public void setOnUpListener(OnUpListener onUpListener) {
        mOnUpListener = onUpListener;
    }

    public void setOnMyLongClickListener(OnMyLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public MyImageButtonView(Context context) {
        super(context);
    }

    public MyImageButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyImageButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startX = ev.getX();
                startY = ev.getY();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isLongClickModule = true;
                    }
                }, 1000); // 按下时长设置
                break;
            case MotionEvent.ACTION_MOVE:
                double deltaX = Math.sqrt((ev.getX() - startX) * (ev.getX() - startX) + (ev.getY() - startY) * (ev.getY() - startY));
                if (deltaX > 20 && timer != null) { // 移动大于20像素
                    timer.cancel();
                    timer = null;
                }
                if(isLongClickModule){
                    //添加你长按之后的方法
                    //getDrawingXY();
                    mOnLongClickListener.onLongClick();
                    timer = null;
                }
                break;
            default:
                isLongClickModule = false;
                if ( timer != null) {
                    timer.cancel();
                    timer = null;
                }
                mOnUpListener.onUp();
        }

        return true;
    }

    public interface OnMyLongClickListener{
        void onLongClick();
    }
    public interface OnUpListener{
        void onUp();
    }
}
