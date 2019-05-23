package com.leo.robot.ui.cut_line;

import android.content.Intent;
import android.widget.TextView;
import com.leo.robot.base.RobotPresenter;
import com.leo.robot.bean.CutLineMsg;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.ui.cut_line.choose.CutLineChooseLocationActivity;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.TimeThread;
import com.unity3d.player.UnityPlayer;
import cree.mvp.util.data.SPUtils;

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
    private UnityPlayer mUnityPlayer;
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
        if (isClickble) {
            if (!isScram) { //急停
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getMainArmShutdown());
                    mActivity.refreshLogRv("发送急停命令");
                }
                mActivity.updateScram(true);
                isScram = true;
            } else {//回复急停
                if (mClient != null) {
                    mClient.sendMsgTest(CommandUtils.getMainArmResume());
                    mActivity.refreshLogRv("发送恢复急停命令");

                }
                mActivity.updateScram(false);
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
        if (isClickble) {
            if (mClient != null) {
                mClient.sendMsgTest(CommandUtils.getMainArmRecover());
                mActivity.refreshLogRv("发送一键回收命令");
            }
        }
    }

    /**
     * 开始、停止
     *
     * @author Leo
     * created at 2019/4/18 2:18 PM
     */
    public void startButton() {
        if (isClickble) {
            if (!isStart) { //开始
                mClient.sendMsgTest(CommandUtils.getMainArmStart());
                isStart = true;
                mActivity.updateStart(true);
                mActivity.refreshLogRv("发送开始命令");
            } else {//停止
                mClient.sendMsgTest(CommandUtils.getMainArmStop());
                isStart = false;
                mActivity.updateStart(false);

                mActivity.refreshLogRv("发送停止命令");
            }
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
        if (isClickble) {
            mActivity.startActivity(new Intent(mActivity, CutLineChooseLocationActivity.class));
            mActivity.finish();
        }
    }

//    public void setUnityView(RelativeLayout unityView) {
//        // TODO: 2019/5/6  直接使用父类的unityPlayer 不要自己去new一个
//        mUnityPlayer = mActivity.getUnityPlayer();
//        unityView.addView(mActivity.getUnityPlayer());
//        mActivity.getUnityPlayer().requestFocus();
//    }


}
