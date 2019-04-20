package com.leo.robot.ui.wiring;

import android.widget.TextView;

import com.leo.robot.base.RobotPresenter;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;

/**
 * created by Leo on 2019/4/18 10 : 46
 */


public class WiringActivityPresenter extends RobotPresenter<WiringActivity,WiringActivityModel> {
    //是否急停
    private boolean isScram = false;
    //是否开始
    private boolean isStart = false;

    @Inject
    public WiringActivityPresenter() {
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
        if (!isScram) { //急停
            NettyClient.getInstance().sendMsg(CommandUtils.getStop());
            isScram = true;
        } else {//回复急停
            NettyClient.getInstance().sendMsg(CommandUtils.getResumeStop());
            isScram = false;
        }
    }
    /**
     * 一键回收
     *
     *@author Leo
     *created at 2019/4/18 2:17 PM
     */
    public void revocerButton() {
        NettyClient.getInstance().sendMsg(CommandUtils.getRecover());
    }

    /**
     * 开始、停止
     *
     *@author Leo
     *created at 2019/4/18 2:18 PM
     */
    public void startButton() {
        if (!isStart) { //开始
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmStart());
            isStart = true;
        } else {//停止
            NettyClient.getInstance().sendMsg(CommandUtils.getMainArmStop());
            isStart = false;
        }
    }

    public void getPicButton() {

    }
}
