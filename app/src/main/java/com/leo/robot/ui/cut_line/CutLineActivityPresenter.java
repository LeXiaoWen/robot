package com.leo.robot.ui.cut_line;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.leo.robot.JNIUtils;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.constant.PushMsgCode;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.URConstants;
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


public class CutLineActivityPresenter extends RobotPresenter<CutLineActivity, CutLineActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;
    private  NettyClient mMasterClient;

    private boolean isClickble = false;
    private UnityPlayer mUnityPlayer;
    public Timer mTimer;
    public TimerTask mTimerTask;

    @Inject
    public CutLineActivityPresenter() {
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
                updateData();
            }
            super.handleMessage(msg);
        }
    };

    private void updateData() {
        initMode();
        initSafeMode();
        initArmStatus();
    }

    /**
     * 软件状态
     *
     *@author Leo
     *created at 2019/7/4 10:11 PM
     */

    private void initArmStatus() {
        String status = JNIUtils.ReadURparam(URConstants.Program_State, URConstants.Marm);
        mActivity.showStatus(status);
    }

    /**
     * 安全模式
     *
     *@author Leo
     *created at 2019/7/4 10:10 PM
     */
    private void initSafeMode() {
        String safeMode = JNIUtils.ReadURparam(URConstants.Safe_Mod, URConstants.Marm);
        mActivity.showSafeMode(safeMode);
    }

    /**
     * 模式
     *
     *@author Leo
     *created at 2019/7/4 10:10 PM
     */
    private void initMode() {
        String mode = JNIUtils.ReadURparam(URConstants.Robot_Mod, URConstants.Marm);
        mActivity.showMode(mode);
    }

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
        if (mMasterClient != null) {
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

    public void getPicButton() {

    }

    public void initStatus() {
        SPUtils utils = new SPUtils(RobotInit.CUT_LINE_ACTIVITY);
        boolean isInit = utils.getBoolean(RobotInit.CUT_INIT);
        boolean isReady = utils.getBoolean(RobotInit.CUT_READY);
        boolean isStart = utils.getBoolean(RobotInit.CUT_START);
        boolean isStop = utils.getBoolean(RobotInit.CUT_STOP);
        boolean isReset = utils.getBoolean(RobotInit.CUT_RESET);
        boolean isEnd = utils.getBoolean(RobotInit.CUT_END);
        isClickble = isInit;

        mActivity.updateInit(isInit);
        mActivity.updateReady(isReady);
        mActivity.updateCutStart(isStart);
        mActivity.updateCutStop(isStop);
        mActivity.updateCutReset(isReset);
        mActivity.updateEnd(isEnd);
    }

    public String jugType(CutLineMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.CUT_INIT)) {
            mActivity.updateInit(true);
            isClickble = true;
            return "剪线工具就绪";
        } else if (type.equals(RobotInit.CUT_NOT_INIT)) {
            mActivity.updateInit(false);
            isClickble = false;
        } else if (type.equals(RobotInit.CUT_READY)) {
            mActivity.updateReady(true);
            return "剪线工具到位";
        } else if (type.equals(RobotInit.CUT_NOT_READY)) {
            mActivity.updateReady(false);
        } else if (type.equals(RobotInit.CUT_START)) {
            mActivity.updateCutStart(true);
            return "剪线开始";
        } else if (type.equals(RobotInit.CUT_NOT_START)) {
            mActivity.updateCutStart(false);
        } else if (type.equals(RobotInit.CUT_STOP)) {
            mActivity.updateCutStop(true);
            return "剪线停止";
        } else if (type.equals(RobotInit.CUT_NOT_STOP)) {
            mActivity.updateCutStop(false);
        } else if (type.equals(RobotInit.CUT_RESET)) {
            mActivity.updateCutReset(true);
            return "钳口复位";
        } else if (type.equals(RobotInit.CUT_NOT_RESET)) {
            mActivity.updateCutReset(false);
        } else if (type.equals(RobotInit.CUT_END)) {
            mActivity.updateEnd(true);
            return "剪线结束";
        } else if (type.equals(RobotInit.CUT_NOT_END)) {
            mActivity.updateEnd(false);
        }
        return null;
    }

    public void identificationClick() {
        Intent intent = new Intent(mActivity, ChooseActivity.class);
        intent.putExtra("activity", 3);

        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * 判断当前指令是哪个摄像机选择第几个点
     *
     * @author Leo
     * created at 2019/5/29 9:27 PM
     */
    public void jugCameraLocationType(String code) {
        if (code.equals(PushMsgCode.CAMERA1_CHOOSE_LOCATION1)) {//usb1相机选择点1
            mActivity.jumpChooseActivity(1, 1);
        } else if (code.equals(PushMsgCode.CAMERA1_CHOOSE_LOCATION2)) {//usb1相机选择点2
            mActivity.jumpChooseActivity(1, 2);

        } else if (code.equals(PushMsgCode.CAMERA2_CHOOSE_LOCATION1)) {//usb2相机选择点1
            mActivity.jumpChooseActivity(2, 1);

        } else if (code.equals(PushMsgCode.CAMERA2_CHOOSE_LOCATION2)) {//usb2相机选择点2
            mActivity.jumpChooseActivity(2, 2);
        }
    }

//    public void setUnityView(RelativeLayout unityView) {
//        // TODO: 2019/5/6  直接使用父类的unityPlayer 不要自己去new一个
//        mUnityPlayer = mActivity.getUnityPlayer();
//        unityView.addView(mActivity.getUnityPlayer());
//        mActivity.getUnityPlayer().requestFocus();
//    }


}
