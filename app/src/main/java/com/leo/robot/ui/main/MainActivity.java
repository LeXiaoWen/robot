package com.leo.robot.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ybq.android.spinkit.SpinKitView;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.bean.AllMsg;
import com.leo.robot.bean.ErroMsg;
import com.leo.robot.constant.Constants;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.NettyListener;
import com.leo.robot.ui.cut_line.CutLineActivity;
import com.leo.robot.ui.wire_stripping.WireStrippingActivity;
import com.leo.robot.ui.wiring.WiringActivity;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.ResultUtils;
import cree.mvp.util.data.SPUtils;
import cree.mvp.util.ui.ToastUtils;
import io.netty.channel.Channel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * created by Leo on 2019/4/14 18 : 11
 */


public class MainActivity extends NettyActivity<MainActivityPresenter> {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.ibtn_wire_stripping)
    ImageButton mIbtnWireStripping;
    @BindView(R.id.ibtn_wirin)
    ImageButton mIbtnWirin;
    @BindView(R.id.ibtn_cut_line)
    ImageButton mIbtnCutLine;
    @BindView(R.id.ibtn_cleaning)
    ImageButton mIbtnCleaning;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_port)
    EditText mEtPort;
    @BindView(R.id.btn_connect)
    Button mBtnConnect;
    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.et_ip1)
    EditText mEtIp1;
    @BindView(R.id.et_port1)
    EditText mEtPort1;
    @BindView(R.id.btn_connect1)
    Button mBtnConnect1;
    @BindView(R.id.tv_type1)
    TextView mTvType1;
    @BindView(R.id.spin_kit1)
    SpinKitView mSpinKit1;
    @BindView(R.id.ll_status1)
    LinearLayout mLlStatus1;
    private boolean isShown = false;
    private boolean isFirst = false;

    private FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerMainActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //实时更新时间（1秒更新一次）
        mPresenter.updateTime(mTvDate);
        initBroadcast(mTvGroundPower);
        initSocketStatus();

    }

    /**
     * 初始化sokcet连接状态
     *
     * @author Leo
     * created at 2019/5/24 11:30 PM
     */
    private void initSocketStatus() {
        SPUtils socket = new SPUtils("masterSocket");
        boolean status = socket.getBoolean("status");
        SPUtils socket1 = new SPUtils("visionSocket");
        boolean status1 = socket1.getBoolean("status");
        if (status) {
            mTvType.setText("与主控服务器连接成功");
            mSpinKit.setVisibility(View.GONE);
        } else {
            mTvType.setText("与主控服务器断开连接，正在重连");
            mSpinKit.setVisibility(View.VISIBLE);
        }

        if (status1){
            mTvType1.setText("与视觉服务器连接成功");
            mSpinKit1.setVisibility(View.GONE);
        }else {
            mTvType1.setText("与视觉服务器断开连接，正在重连");
            mSpinKit1.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void notifyData(int status, String message) {
        mTvType.setText(message);

        if (status == 0) {//未连接
            mSpinKit.setVisibility(View.VISIBLE);
        } else {//已连接
            mSpinKit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void notifyMasterData(int status, String message) {

    }

    @Override
    protected void notifyVisionData(int status, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvType1.setText(message);
                if (status == 0) {
                    mSpinKit1.setVisibility(View.VISIBLE);
                } else {
                    mSpinKit1.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        isShown = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShown = true;
    }

    @Override
    protected void onDestroy() {
        onUnBindReceiver();
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptErroMsg(ErroMsg msg) {
        if (isShown) {
            ToastUtils.showShortToast(msg.getMsg());
        }
    }

    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }

    private long firstTime;// 记录点击返回时第一次的时间毫秒值

    /**
     * 重写该方法，判断用户按下返回按键的时候，执行退出应用方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 点击了返回按键
            if (manager.getBackStackEntryCount() != 0) {
                manager.popBackStack();
            } else {
                exitApp(2000);// 退出应用
            }
            return true;// 返回true，防止该事件继续向下传播
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用
     *
     * @param timeInterval 设置第二次点击退出的时间间隔
     */
    private void exitApp(long timeInterval) {
        // 第一次肯定会进入到if判断里面，然后把firstTime重新赋值当前的系统时间
        // 然后点击第二次的时候，当点击间隔时间小于2s，那么退出应用；反之不退出应用
        if (System.currentTimeMillis() - firstTime >= timeInterval) {
            ToastUtils.showShortToast("再按一次退出程序");
//            Intent intent2 = new Intent(MainActivity.this, NettyService.class);
//            stopService(intent2);// 关闭服务
            firstTime = System.currentTimeMillis();
        } else {
            finish();// 销毁当前activity
            System.exit(0);// 完全退出应用
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMsg(AllMsg msg) {
        if (isShown) {
            ToastUtils.showShortToast(msg.getMsg() + "    " + msg.getCode());
        }
    }


    @OnClick({R.id.ibtn_wire_stripping, R.id.ibtn_wirin, R.id.ibtn_cut_line, R.id.ibtn_cleaning, R.id.btn_connect, R.id.btn_connect1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_wire_stripping:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(WireStrippingActivity.class);
                }
                break;
            case R.id.ibtn_wirin:
                if (!mPresenter.isFastDoubleClick()) {
                    if (Constants.isFinishWrieStripping()) {
                        startActivity(WiringActivity.class);
                    } else {
                        startActivity(WireStrippingActivity.class);
                    }

                }
                break;
            case R.id.ibtn_cut_line:
                if (!mPresenter.isFastDoubleClick()) {
                    startActivity(CutLineActivity.class);
//                    ToastUtils.showShortToast("剪线作业作业！");

                }
                break;
            case R.id.ibtn_cleaning:
                ToastUtils.showShortToast("清洗绝缘子作业！");

                break;
            case R.id.btn_connect:
                String ip = mEtIp.getText().toString().trim();
                String port = mEtPort.getText().toString().trim();
                if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
                    initMasterNetty(ip, Integer.valueOf(port));
                }
                break;
            case R.id.btn_connect1:
                String ip1 = mEtIp1.getText().toString().trim();
                String port1 = mEtPort1.getText().toString().trim();
                if (!TextUtils.isEmpty(ip1) && !TextUtils.isEmpty(port1)) {
                    initVisionNetty(ip1, Integer.valueOf(port1));
                }
                break;
        }
    }

    /**
     * 视觉服务器
     *
     * @param ip
     * @param
     */
    private void initMasterNetty(String ip, Integer port) {
        NettyClient client = new NettyClient();
        NettyManager.getInstance().addNettyClient(RobotInit.MASTER_CONTROL_NETTY, client);

        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg, RobotInit.MASTER_CONTROL_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    ResultUtils.onConnectSuccess(RobotInit.MASTER_CONTROL_NETTY);
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {
                    ResultUtils.onConnectErro(RobotInit.MASTER_CONTROL_NETTY);
                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(ip, port);//连接服务器
                    }).start();
                }
            }

            @Override
            public void onServiceHeart(Channel channel) {

            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(ip, port);//连接服务器
            }).start();
        }
    }


    /**
     * 视觉服务器
     */
    private void initVisionNetty(String ip, int port) {
        NettyClient client = new NettyClient();
        NettyManager.getInstance().addNettyClient(RobotInit.VISION_NETTY, client);
        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg, RobotInit.VISION_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    ResultUtils.onConnectSuccess(RobotInit.VISION_NETTY);
//                    notifyData(1,"与视觉控服务器连接成功");
//                    String s = mGson.toJson(CommandUtils.getVisionBean());
//                    NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
//                    if (client != null) {
//                        client.sendMsgTest(s);
//                    }
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {//通信异常
//                    notifyData(1,"与视觉控服务器连接异常，正在重连");
                    ResultUtils.onConnectErro(RobotInit.VISION_NETTY);
                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {//服务器主动断开
//                    notifyData(1,"视觉控服务器断开连接，正在重连");

                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.VISION_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
                    }).start();
                }
            }

            @Override
            public void onServiceHeart(Channel channel) {

            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(ip, port);//连接服务器
            }).start();
        }
    }

}
