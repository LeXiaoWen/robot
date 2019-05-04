package com.leo.robot.ui.launcher;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.leo.robot.R;
import com.leo.robot.bean.TestBean;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.NettyListener;
import com.leo.robot.service.NettyService;
import com.leo.robot.ui.main.MainActivity;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.ResultUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import cree.mvp.util.permissions.PermissionsUtils;
import cree.mvp.util.permissions.rx.PerAction1;
import cree.mvp.util.permissions.rx.PerActionError;
import cree.mvp.util.ui.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 启动页
 *
 * @author Leo
 * created at 2019/4/14 6:09 PM
 */

public class LauncherActivity extends AppCompatActivity {

    private int count = 0;
    private int sumCount = 3;
    private Gson mGson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        //主控服务器
        initMasterNetty();
        //视觉服务器
//        initVisionNetty();
        initService();
        initPermisson();
    }

    /**
     * 视觉服务器
     */
    private void initVisionNetty() {
        NettyClient client = new NettyClient();
        NettyManager.getInstance().addNettyClient(RobotInit.VISION_NETTY, client);
        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg,RobotInit.VISION_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    ResultUtils.onConnectSuccess(RobotInit.VISION_NETTY);
                } else {
                    ResultUtils.onConnectErro(RobotInit.VISION_NETTY);
                }
            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.VISION_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
            }).start();
        }
    }

    /**
     * 主控服务器
     *
     * @author Leo
     * created at 2019/4/27 10:06 PM
     */
    private void initMasterNetty() {
        NettyClient client = new NettyClient();
        NettyManager.getInstance().addNettyClient(RobotInit.MASTER_CONTROL_NETTY, client);

        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg,RobotInit.MASTER_CONTROL_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    ResultUtils.onConnectSuccess(RobotInit.MASTER_CONTROL_NETTY);
                } else {
                    ResultUtils.onConnectErro(RobotInit.MASTER_CONTROL_NETTY);
                }
            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.MASTER_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
            }).start();
        }
    }

    /**
     * 申请权限
     *
     * @author Leo
     * created at 2019/4/14 6:08 PM
     */

    private void initPermisson() {
        applyPermisson(Manifest.permission.CAMERA);
        applyPermisson(Manifest.permission.READ_EXTERNAL_STORAGE);
        applyPermisson(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 启动service
     *
     * @author Leo
     * created at 2019/4/14 6:08 PM
     */
    private void initService() {
        final Intent intent = new Intent(getApplication(), NettyService.class);
        startService(intent);
    }


    private void applyPermisson(String... permission) {
        PermissionsUtils
                .<Boolean>getInstance(this)
                .request(permission)
                .subscrbe(new PerAction1() {
                    @Override
                    public void onCallBack(Boolean aBoolean) {
                        count++;
                        if (aBoolean) {
                            if (count == sumCount) {
                                init();
                            }
                        }
                    }
                }, new PerActionError() {
                    @Override
                    public void onCallEror(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    private void init() {
        countdown(3)
                .doOnSubscribe(() -> {
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                    }
                });
    }


    /**
     * 倒计时
     *
     * @author Leo
     * created at 2019/4/14 6:08 PM
     */
    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toastMsg(TestBean bean){
        ToastUtils.showShortToast(bean.getMsg());
    }
}
