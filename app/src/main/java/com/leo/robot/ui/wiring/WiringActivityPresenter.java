package com.leo.robot.ui.wiring;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.WiringMsg;
import com.leo.robot.constant.PushMsgCode;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.ui.choose.ChooseActivity;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;
import com.unity3d.player.UnityPlayer;
import cree.mvp.util.data.SPUtils;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class WiringActivityPresenter extends RobotPresenter<WiringActivity, WiringActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;

    private boolean isClickble = false;
    private  NettyClient mMasterClient;
    private UnityPlayer mUnityPlayer;

    public Timer mTimer;
    public TimerTask mTimerTask;

    @Inject
    public WiringActivityPresenter() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
    }

    public void initClient() {
        mMasterClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }


    public void destroyClient() {
        if (mMasterClient != null) {
            mMasterClient = null;
        }
    }

    /**
     * 实时请求行线、引流线距离
     *
     * @author Leo
     * created at 2019/5/30 9:06 PM
     */
    public void initLineLocation() {
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

    /**
     * 急停、恢复急停
     *
     * @author Leo
     * created at 2019/4/18 2:11 PM
     */
    public void scramButton() {
            if (!isScram) { //急停
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.getMainArmShutdown());
                    mMasterClient.sendMsgTest(CommandUtils.getFlowArmShutdown());
                    mActivity.refreshLogRv("发送急停命令");
                }
                mActivity.updateScram(true);
                isScram = true;
            } else {//回复急停
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.getMainArmResume());
                    mMasterClient.sendMsgTest(CommandUtils.getFlowArmResume());
                    mActivity.refreshLogRv("发送恢复急停命令");

                }
                mActivity.updateScram(false);
                isScram = false;
            }
    }

    /**
     * 一键回收
     *
     * @author Leo
     * created at 2019/4/18 2:17 PM
     */
    public void revocerButton() {
            if (mMasterClient!=null){
                mMasterClient.sendMsgTest(CommandUtils.getMainArmRecover());
                mActivity.refreshLogRv("发送一键回收命令");
            }
    }

    /**
     * 开始、停止
     *
     * @author Leo
     * created at 2019/4/18 2:18 PM
     */
    public void startButton() {
            if (!isStart) { //开始
                mMasterClient.sendMsgTest(CommandUtils.getMainArmStart());
                isStart = true;
                mActivity.updateStart(true);
                mActivity.refreshLogRv("发送开始命令");
            } else {//停止
                mMasterClient.sendMsgTest(CommandUtils.getMainArmStop());
                isStart = false;
                mActivity.updateStart(false);

                mActivity.refreshLogRv("发送停止命令");
            }
    }



    public String jugType(WiringMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.WIRING_READY)) {//就绪
            mActivity.updateReady(true);
            isClickble = true;
            mActivity.updateClickStatus(true);
            return "接线就绪";
        } else if (type.equals(RobotInit.WIRING_NOT_READY)) {
            mActivity.updateReady(false);
            isClickble = false;
            mActivity.updateClickStatus(false);
        } else if (type.equals(RobotInit.WIRING_GRAB)) {//引流抓取
            mActivity.updateGrab(true);
            return "接线引流抓取";
        } else if (type.equals(RobotInit.WIRING_NOT_GRAB)) {
            mActivity.updateGrab(false);
        } else if (type.equals(RobotInit.WIRING_ENTER)) {//引线进入
            mActivity.updateEnter(true);
            return "引线进入";
        } else if (type.equals(RobotInit.WIRING_NOT_ENTER)) {
            mActivity.updateEnter(false);
        } else if (type.equals(RobotInit.WIRING_FIXED)) {//引线固定
            mActivity.updateFixed(true);
            return "引线夹紧";
        } else if (type.equals(RobotInit.WIRING_NOT_FIXED)) {
            mActivity.updateFixed(false);
        } else if (type.equals(RobotInit.WIRING_TOOL_READY)) {//接线工具就位
            mActivity.updateToolReady(true);
            return "接线工具就位";
        } else if (type.equals(RobotInit.WIRING_NOT_TOOL_READY)) {
            mActivity.updateToolReady(false);
        } else if (type.equals(RobotInit.WIRING_LINE_READY)) {//并勾线夹就位
            mActivity.updateLineReady(true);
            return "并勾线夹就位";
        } else if (type.equals(RobotInit.WIRING_NOT_LINE_READY)) {
            mActivity.updateLineReady(false);
        } else if (type.equals(RobotInit.WIRING_TWIST)) {//拧断螺母
            mActivity.updateTwist(true);
            return "拧断螺母";
        } else if (type.equals(RobotInit.WIRING_NOT_TWIST)) {
            mActivity.updateTwist(false);
        } else if (type.equals(RobotInit.WIRING_CLIP_UNLOCK)) {//线夹解锁
            mActivity.updateClipUnlock(true);
            return "线夹解锁";
        } else if (type.equals(RobotInit.WIRING_NOT_CLIP_UNLOCK)) {
            mActivity.updateClipUnlock(false);
        } else if (type.equals(RobotInit.WIRING_SLEEVE_UNLOCK)) {//套筒解锁
            mActivity.updateSleeveUnlock(true);
            return "套筒解锁";
        } else if (type.equals(RobotInit.WIRINGNOT_SLEEVE_UNLOCK)) {
            mActivity.updateSleeveUnlock(false);

        } else if (type.equals(RobotInit.WIRING_END)) {//结束
            mActivity.updateEnd(true);
            return "接线结束";
        } else if (type.equals(RobotInit.WIRING_NOT_END)) {
            mActivity.updateEnd(false);
        }

        return null;
    }

    public void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.WIRING_ACTIVITY);
        boolean isReady = utils.getBoolean(RobotInit.WIRING_READY);
        boolean isGrab = utils.getBoolean(RobotInit.WIRING_GRAB);
        boolean isEnter = utils.getBoolean(RobotInit.WIRING_ENTER);
        boolean isFixed = utils.getBoolean(RobotInit.WIRING_FIXED);
        boolean isToolReady = utils.getBoolean(RobotInit.WIRING_TOOL_READY);
        boolean isLineReady = utils.getBoolean(RobotInit.WIRING_LINE_READY);
        boolean isTwist = utils.getBoolean(RobotInit.WIRING_TWIST);
        boolean isClipUnlock = utils.getBoolean(RobotInit.WIRING_CLIP_UNLOCK);
        boolean isSleeveUnlock = utils.getBoolean(RobotInit.WIRING_SLEEVE_UNLOCK);
        boolean isEnd = utils.getBoolean(RobotInit.WIRING_END);
        isClickble = isReady;
        mActivity.updateReady(isReady);
        mActivity.updateGrab(isGrab);
        mActivity.updateEnter(isEnter);
        mActivity.updateFixed(isFixed);
        mActivity.updateToolReady(isToolReady);
        mActivity.updateLineReady(isLineReady);
        mActivity.updateTwist(isTwist);
        mActivity.updateClipUnlock(isClipUnlock);
        mActivity.updateSleeveUnlock(isSleeveUnlock);
        mActivity.updateEnd(isEnd);
    }

    public void identificationClick() {
        Intent intent = new Intent(mActivity, ChooseActivity.class);
        intent.putExtra("activity",2);
        mActivity.startActivity(intent);
            mActivity.finish();
    }

    /**
     * 判断当前指令是哪个摄像机选择第几个点
     *
     *@author Leo
     *created at 2019/5/29 9:27 PM
     */
    public void jugCameraLocationType(String code) {
        if (code.equals(PushMsgCode.CAMERA1_CHOOSE_LOCATION1)){//usb1相机选择点1
            mActivity.jumpChooseActivity(1,1);
        }else if (code.equals(PushMsgCode.CAMERA1_CHOOSE_LOCATION2)){//usb1相机选择点2
            mActivity.jumpChooseActivity(1,2);

        }else if (code.equals(PushMsgCode.CAMERA2_CHOOSE_LOCATION1)){//usb2相机选择点1
            mActivity.jumpChooseActivity(2,1);

        }else if (code.equals(PushMsgCode.CAMERA2_CHOOSE_LOCATION2)){//usb2相机选择点2
            mActivity.jumpChooseActivity(2,2);
        }
    }
//    public void setUnityView(RelativeLayout unityView) {
//        // TODO: 2019/5/6  直接使用父类的unityPlayer 不要自己去new一个
//        mUnityPlayer = mActivity.getUnityPlayer();
//        unityView.addView(mActivity.getUnityPlayer());
//        mActivity.getUnityPlayer().requestFocus();
//    }
//
//    public void removeUnityView(RelativeLayout unityView) {
//        if (unityView.getChildAt(0) != null) {
//            unityView.removeView(mUnityPlayer);
//        }
//        mActivity.getUnityPlayer().requestFocus();
//    }

}
