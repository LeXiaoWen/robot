package com.leo.robot.utils;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.leo.robot.R;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoViewBridge;

import cree.mvp.util.ui.ToastUtils;


/**
 * 多个同时播放的播放控件
 * Created by guoshuyu on 2018/1/31.
 */

public class MultiSampleVideo extends StandardGSYVideoPlayer {

    private final static String TAG = "MultiSampleVideo";


    ImageView mCoverImage;

    String mCoverOriginUrl;

    int mDefaultRes;

    public MultiSampleVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MultiSampleVideo(Context context) {
        super(context);
    }

    public MultiSampleVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = (ImageView) findViewById(R.id.thumbImage);
        if (mThumbImageViewLayout != null &&
                (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }
        onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        //todo 判断如果不是外界造成的就不处理
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        //todo 判断如果不是外界造成的就不处理
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        break;
                }
            }
        };
    }

    @Override
    public GSYVideoViewBridge getGSYVideoManager() {
        CustomManager.getCustomManager(getKey()).initContext(getContext().getApplicationContext());
        return CustomManager.getCustomManager(getKey());
    }

    @Override
    protected boolean backFromFull(Context context) {
        return CustomManager.backFromWindowFull(context, getKey());
    }

    @Override
    protected void releaseVideos() {
        CustomManager.releaseAllVideos(getKey());
    }


    @Override
    protected int getFullId() {
        return CustomManager.FULLSCREEN_ID;
    }

    @Override
    protected int getSmallId() {
        return CustomManager.SMALL_ID;
    }


    @Override
    public int getLayoutId() {
        return R.layout.empty_control_video;
    }

    public void loadCoverImage(String url, int res) {
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(res)
                                .placeholder(res))
                .load(url)
                .into(mCoverImage);
    }

//    @Override
//    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
//        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
//        MultiSampleVideo multiSampleVideo = (MultiSampleVideo) gsyBaseVideoPlayer;
//        multiSampleVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
//        return multiSampleVideo;
//    }


//    @Override
//    public GSYBaseVideoPlayer showSmallVideo(Point size, boolean actionBar, boolean statusBar) {
//        //下面这里替换成你自己的强制转化
//        MultiSampleVideo multiSampleVideo = (MultiSampleVideo) super.showSmallVideo(size, actionBar, statusBar);
//        multiSampleVideo.mStartButton.setVisibility(GONE);
//        multiSampleVideo.mStartButton = null;
//        return multiSampleVideo;
//    }

    public String getKey() {
        if (mPlayPosition == -22) {
            Debuger.printfError(getClass().getSimpleName() + " used getKey() " + "******* PlayPosition never set. ********");
        }
        if (TextUtils.isEmpty(mPlayTag)) {
            Debuger.printfError(getClass().getSimpleName() + " used getKey() " + "******* PlayTag never set. ********");
        }
        return TAG + mPlayPosition + mPlayTag;
    }
    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false;

//        ToastUtils.showShortToast("X，Y坐标 " + absDeltaX+ " , " + absDeltaY);
    }

    @Override
    protected void touchDoubleUp() {
        //super.touchDoubleUp();
        //不需要双击暂停
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
                ToastUtils.showShortToast("实时位置：(" + event.getX() + "," + event.getY());
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
//                mTouchShow.setText("结束位置：(" + event.getX() + "," + event.getY());
                break;
            default:
                break;
        }
        return true;
    }
}
