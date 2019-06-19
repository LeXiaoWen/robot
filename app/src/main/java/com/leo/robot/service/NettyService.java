package com.leo.robot.service;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.gson.Gson;
import com.leo.robot.base.ActivityManager;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.constant.RobotInit;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.ClientIdleStateTrigger;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.NettyListener;
import com.leo.robot.netty.arm.ArmNettyClient;
import com.leo.robot.utils.CommandUtils;
import com.leo.robot.utils.NettyManager;
import com.leo.robot.utils.ResultUtils;
import cree.mvp.util.data.SPUtils;
import io.netty.channel.Channel;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.TimeUnit;


/**
 * @author Leo
 * created at 2019/4/14 6:04 PM
 */
public class NettyService extends Service {
    private static Gson mGson = new Gson();
    private NetworkReceiver receiver;
    public static final String TAG = NettyService.class.getName();
    private List<Integer> ports = new ArrayList<>();


    public NettyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ports.add(UrlConstant.FLOW_ARM_PORT1);
        ports.add(UrlConstant.FLOW_ARM_PORT2);
        ports.add(UrlConstant.FLOW_ARM_PORT3);
        initMasterNetty();
        initVisionNetty();
        initMainArmNetty();
//        initFlowArmNetty();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        Map<String, NettyClient> clientMap = NettyManager.getInstance().getClientMap();
        for (NettyClient client : clientMap.values()) {
            client.setReconnectNum(0);
            client.disconnect();
        }
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
                ResultUtils.onResultByType(msg, RobotInit.VISION_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                SPUtils socket = new SPUtils("visionSocket");
                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {

                    notifyVisionData(1, "与视觉服务器连接成功");
                    String s = mGson.toJson(CommandUtils.getVisionBean());
                    NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.VISION_NETTY);
                    if (client != null) {
                        client.sendMsgTest(s);
                    }
                    socket.putBoolean("status", true);
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {//通信异常
                    notifyVisionData(0,"与视觉服务器连接异常，正在重连");
                    socket.putBoolean("status", false);
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.VISION_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
                    }).start();
                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {//服务器主动断开
                    notifyVisionData(0,"与视觉服务器断开连接，正在重连");
                    socket.putBoolean("status", false);
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
                ResultUtils.onResultByType(msg, RobotInit.MASTER_CONTROL_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                SPUtils socket = new SPUtils("masterSocket");

                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    notifyData(1, "与主控服务器连接成功");
                    String s = mGson.toJson(CommandUtils.getMasterControlBean());
                    NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
                    if (client != null) {
                        client.sendMsgTest(s);
                    }
                    socket.putBoolean("status", true);
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {//通信异常
                    notifyData(0, "与主控服务器连接异常，正在重连");
                    socket.putBoolean("status", false);
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.MASTER_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
                    }).start();
                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {//服务器主动断开
                    socket.putBoolean("status", false);
                    notifyData(0, "主控服务器断开连接，正在重连");
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.MASTER_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
                    }).start();
                }
            }

            @Override
            public void onServiceHeart(Channel channel) {
                ping(channel);
            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.MASTER_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
            }).start();
        }
    }



    /**
     * 主机械臂
     *
     * @author Leo
     * created at 2019/6/14 8:18 PM
     */
    private void initMainArmNetty() {
        ArmNettyClient client = new ArmNettyClient();
        NettyManager.getInstance().addNettyClient(RobotInit.MAIN_ARM_NETTY, client);
        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg, RobotInit.MAIN_ARM_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                SPUtils socket = new SPUtils("socket");

                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    notifyData(1, "与主机械臂连接成功");
                    String s = mGson.toJson(CommandUtils.getMasterControlBean());
                    NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
                    if (client != null) {
                        client.sendMsgTest(s);
                    }
                    socket.putBoolean("status", true);
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {//通信异常
                    notifyData(0, "与主机械臂连接异常，正在重连");
                    socket.putBoolean("status", false);

                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {//服务器主动断开
                    socket.putBoolean("status", false);
                    notifyData(0, "主机械臂断开连接，正在重连");
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.MAIN_ARM_NETTY_HOST, ports);//连接服务器
                    }).start();
                }
            }

            @Override
            public void onServiceHeart(Channel channel) {

            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.MAIN_ARM_NETTY_HOST, ports);//连接服务器
            }).start();
        }
    }

    /**
     * 从机械臂
     *
     * @author Leo
     * created at 2019/6/14 8:18 PM
     */
    private void initFlowArmNetty() {
        ArmNettyClient client = new ArmNettyClient();
        NettyManager.getInstance().addNettyClient(RobotInit.FLOW_ARM_NETTY, client);
        client.setListener(new NettyListener() {
            @Override
            public void onMessageResponse(String msg) {
                ResultUtils.onResultByType(msg, RobotInit.FLOW_ARM_NETTY);
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {
                SPUtils socket = new SPUtils("socket");

                if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
                    notifyData(1, "与从机械臂连接成功");
                    String s = mGson.toJson(CommandUtils.getMasterControlBean());
                    NettyClient client = NettyManager.getInstance().getClientByTag(RobotInit.MASTER_CONTROL_NETTY);
                    if (client != null) {
                        client.sendMsgTest(s);
                    }
                    socket.putBoolean("status", true);
                } else if (statusCode == NettyListener.STATUS_CONNECT_ERROR) {//通信异常
                    notifyData(0, "与从机械臂连接异常，正在重连");
                    socket.putBoolean("status", false);

                } else if (statusCode == NettyListener.STATUS_CONNECT_CLOSED) {//服务器主动断开
                    socket.putBoolean("status", false);
                    notifyData(0, "从机械臂断开连接，正在重连");
                    client.setConnectStatus(false);
                    new Thread(() -> {
                        client.connect(UrlConstant.FLOW_ARM_NETTY_HOST, ports);//连接服务器
                    }).start();
                }
            }

            @Override
            public void onServiceHeart(Channel channel) {

            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.FLOW_ARM_NETTY_HOST, ports);//连接服务器
            }).start();
        }
    }


    private void notifyData(int type, String messageHolder) {
        final Stack<Activity> activities = ActivityManager.getInstance().getActivities();
        for (Activity activity : activities) {
            if (activity == null || activity.isFinishing()) {
                continue;
            }
            Message message = Message.obtain();
            message.what = type;
            message.obj = messageHolder;
            if (activity instanceof NettyActivity) {
                ((NettyActivity) activity).getHandler().sendMessage(message);
            }
        }
    }
    private void notifyVisionData(int type, String messageHolder) {
        final Stack<Activity> activities = ActivityManager.getInstance().getActivities();
        for (Activity activity : activities) {
            if (activity == null || activity.isFinishing()) {
                continue;
            }
            Message message = Message.obtain();
            message.what = type;
            message.obj = messageHolder;
            if (activity instanceof NettyActivity) {
                ((NettyActivity) activity).getVisionHandler().sendMessage(message);
            }
        }
    }


    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    initMasterNetty();
                    initVisionNetty();
                    Log.e(TAG, "connecting ...");
                }
            }
        }
    }
    private void ping(Channel channel) {
//        int second = Math.max(1, random.nextInt(baseRandom));
//        System.out.println("next heart beat will send after " + second + "s.");
        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                if (channel.isActive()) {
                    System.out.println("sending heart beat to the server...");
                    channel.writeAndFlush(ClientIdleStateTrigger.HEART_BEAT);
                } else {
                    System.err.println("The connection had broken, cancel the task that will send a heart beat.");
                    channel.closeFuture();
                    throw new RuntimeException();
                }
            }
        }, 2, TimeUnit.SECONDS);

        future.addListener((GenericFutureListener) future1 -> {
            if (future1.isSuccess()) {
                ping(channel);
            }
        });
    }

}
