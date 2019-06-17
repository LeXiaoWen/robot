package com.leo.robot.ui.choose;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.ByteUtils;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;
import cree.mvp.util.ui.ToastUtils;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * created by Leo on 2019/6/13 23 : 26
 */


public class ChooseActivityPresenter extends RobotPresenter<ChooseActivity, ChooseActivityModel> {

    public NettyClient mMasterClient;
    public NettyClient mVisionClient;

    //水平滑台位置
    public String landSlideTabLocation = "05000000";
    //垂直滑台位置
    public String verticalSlideTabLocation = "08000000";
    //第几次选点
    public int chooseCount = 0;
    public Timer mTimer;
    public TimerTask mTimerTask;

    @Inject
    public ChooseActivityPresenter() {
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

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
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


    public void initClient() {
        mMasterClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
        mVisionClient = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
    }

    public void controle1ActionDown(int videoTag) {
        switch (videoTag) {
            case 0:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.landSlideTableLeftMove());
                }

                break;
            case 1:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableUpMove());
                }
                break;
        }
    }

    public void controle2ActionDown(int videoTag) {
        switch (videoTag) {
            case 0:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.landSlideTableRightMove());
                }
                break;
            case 1:
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableDownMove());
                }
                break;
        }
    }

    public void controleActionUp(int videoTag) {
        switch (videoTag) {
            case 0://水平滑台
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.landSlideTableStopMove());
                    mMasterClient.sendMsgTest(CommandUtils.getLandSlideTable());
                }
                break;
            case 1://垂直滑台
                if (mMasterClient != null) {
                    mMasterClient.sendMsgTest(CommandUtils.verticalSlideTableStopMove());
                    mMasterClient.sendMsgTest(CommandUtils.getVerticalSlideTable());
                }
                break;
        }
    }

    /**
     * 获取滑台位置
     *
     * @author Leo
     * created at 2019/6/16 4:22 PM
     */
    public void getSlideTableLocation() {
        if (mMasterClient != null) {
            mMasterClient.sendMsgTest(CommandUtils.getLandSlideTable());
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    mMasterClient.sendMsgTest(CommandUtils.getVerticalSlideTable());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 确认描点结果
     *
     * @author Leo
     * created at 2019/5/30 10:46 PM
     */

    public void confirmResult(int videoTag) {
        if (mVisionClient != null) {
            switch (videoTag) {
                case 0:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CONFIRM);
                    break;
                case 1:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CONFIRM);
                    break;
            }
        }

    }

    /**
     * 撤销选点
     *
     * @author Leo
     * created at 2019/6/16 4:24 PM
     */
    public void cancelResult(int videoTag) {
        if (mVisionClient != null) {

            switch (videoTag) {
                case 0:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA1_CANCEL);
                    break;
                case 1:
                    mVisionClient.sendMsgTest(CommandUtils.CAMERA2_CANCEL);
                    break;
            }
            chooseCount = 0;
            mActivity.showMsg("已撤销选点，请选择第一个点位");
        } else {
            ToastUtils.showShortToast("未连接视觉服务器");
        }
    }

    public void sendConfirmMsg(int x1, int y1, int videoTag, int radioButtonTag) {
        StringBuilder s;
        String x = ByteUtils.numToHex32(x1);
        String y = ByteUtils.numToHex32(y1);
        String location = ByteUtils.stringLowToHight(x, y);

        if (mVisionClient != null) {
            switch (radioButtonTag) {
                case 1:
                    switch (videoTag) {
                        case 0://行线
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA1_LOCATION1);
                            s.append(location);
                            s.append(landSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            mActivity.showMsg("请选择行线画面第二个点位");
                            break;
                        case 1://引流线
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA2_LOCATION1);
                            s.append(location);
                            s.append(verticalSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            mActivity.showMsg("请选择引流线画面第二个点位");
                            break;
                    }

                    break;
                case 2:
                    switch (videoTag) {
                        case 0:
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA1_LOCATION2);
                            s.append(location);
                            s.append(landSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            mActivity.showMsg("请选择行线画面选点完成");
                            break;
                        case 1:
                            s = new StringBuilder();
                            s.append(CommandUtils.CAMERA2_LOCATION2);
                            s.append(location);
                            s.append(verticalSlideTabLocation);
                            s.append("FF");
                            mVisionClient.sendMsgTest(s.toString());
                            mActivity.showMsg("请选择引流线画面选点完成");
                            break;
                    }

                    break;
            }
        } else {
            ToastUtils.showShortToast("未连接视觉服务器");
        }

    }

    public void onDestroy() {
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }


    public void confirmLocation(int videoTag) {
        if (mMasterClient!=null){
            switch (videoTag){
                case 0:
                    mMasterClient.sendMsgTest(CommandUtils.confirmLandSlideTable());
                    break;
                case 1:
                    mMasterClient.sendMsgTest(CommandUtils.confirmVerticalSlideTable());
                    break;
            }
        }
    }
}
