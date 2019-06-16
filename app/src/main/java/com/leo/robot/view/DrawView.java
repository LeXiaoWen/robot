package com.leo.robot.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * created by Leo on 2019/6/17 01 : 45
 */


public class DrawView extends View {
    //定义并创建画笔
    Paint mPaint;
    public float currentX;
    public float currentY;

    public DrawView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
    }

    public DrawView(Context context, AttributeSet set) {
        super(context, set);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(currentX, currentY, 6,mPaint);
    }



}
