package com.leo.robot.ui.cut_line;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class CutLineActivityPresenter extends RobotPresenter<CutLineActivity, CutLineActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;
    private final NettyClient mClient;

    private boolean isClickble = false;

    @Inject
    public CutLineActivityPresenter() {
        mClient = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
    }

    @Override
    protected void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }

    /**
     * 急停、恢复急停
     *
     * @author Leo
     * created at 2019/4/18 2:11 PM
     */
    public void scramButton() {
        if (mClient != null) {
            if (!isScram) { //急停
                mClient.sendMsgTest(CommandUtils.getMainArmShutdown());
//                mActivity.updateScramText("恢复急停");
                isScram = true;
            } else {//回复急停
                mClient.sendMsgTest(CommandUtils.getMainArmResume());
//                mActivity.updateScramText("急停");
                isScram = false;
            }
        }

    }

    /**
     * 一键回收
     *
     * @author Leo
     * created at 2019/4/18 2:17 PM
     */
    public void revocerButton() {
        if (mClient != null) {
            mClient.sendMsgTest(CommandUtils.getMainArmRecover());
        }
    }

    /**
     * 开始、停止
     *
     * @author Leo
     * created at 2019/4/18 2:18 PM
     */
    public void startButton() {
        if (mClient != null) {
            if (!isStart) { //开始
                mClient.sendMsgTest(CommandUtils.getMainArmStart());
                isStart = true;
            } else {//停止
                mClient.sendMsgTest(CommandUtils.getMainArmStop());
                isStart = false;
            }
        }

    }

    public void getPicButton() {

    }

    public void initStatus() {


    }

    public String jugType(CutLineMsg msg) {
        String type = msg.getMsg();
        if (type.equals(RobotInit.CUT_READY)) {
            mActivity.updateInit(true);
            isClickble = true;
            return "剪线工具到位";
        } else if (type.equals(RobotInit.CUT_NOT_READY)) {
            mActivity.updateInit(false);
            isClickble = false;
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
}
