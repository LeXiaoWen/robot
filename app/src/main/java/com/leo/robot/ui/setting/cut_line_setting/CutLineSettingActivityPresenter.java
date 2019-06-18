package com.leo.robot.ui.setting.cut_line_setting;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * created by Leo on 2019/4/18 21 : 33
 */


public class CutLineSettingActivityPresenter extends RobotPresenter<CutLineSettingActivity, CutLineSettingActivityModel> {
    public Timer mTimer;
    public TimerTask mTimerTask;
    public NettyClient mMasterClient;

    @Inject
    public CutLineSettingActivityPresenter() {
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

    public void updateTime(TextView view) {
        TimeThread timeThread = new TimeThread(view);
        timeThread.start();
    }
}
