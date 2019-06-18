package com.leo.robot.netty.arm;

import android.util.Log;
import com.google.gson.Gson;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.netty.FutureListener;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.NettyClientInitializer;
import com.leo.robot.netty.NettyListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * created by Leo on 2019/6/14 20 : 31
 */


public class ArmNettyClient extends NettyClient {
    private static ArmNettyClient armNettyClient = new ArmNettyClient();

    private EventLoopGroup group;

    private NettyListener listener;
    private ArmNettyListener1 listener1;
    private ArmNettyListener2 listener2;
    private ArmNettyListener3 listener3;

    private Channel channel1;
    private Channel channel2;
    private Channel channel3;

    private boolean isConnect = false;

    private int reconnectNum = Integer.MAX_VALUE;
//    private int reconnectNum = 2;

    private long reconnectIntervalTime = 3000;
    public final static String TAG = ArmNettyClient.class.getName();
    private final Gson gson;
    private Bootstrap bootstrap;

    public ArmNettyClient() {
        gson = new Gson();
    }

    public static ArmNettyClient getInstance() {
        return armNettyClient;
    }

    public synchronized ArmNettyClient connect(String ip, List<Integer> ports) {
        if (!isConnect) {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap().group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer(listener));

                //同时连接多个端口
                for (int i = 0; i < ports.size(); i++) {
                    try {
                    ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(ip, ports.get(i)));
                    int port = ports.get(i);
                    channelFuture.addListener(future -> {
                        if (future.isSuccess()) {
                            if (port == UrlConstant.MAIN_ARM_PORT1) {
                                channel1 = channelFuture.channel();
                            } else if (port == UrlConstant.MAIN_ARM_PORT2) {
                                channel2 = channelFuture.channel();
                            }
                            isConnect = true;
                            System.out.println("connect success port : " + port);
                        } else {
                            isConnect = false;
                            System.out.println("connect failed port : " + port);
                        }
                    });
                    Channel channel = channelFuture.channel();
                    channel.closeFuture().addListener(future -> channel.close());
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                        reconnect(ip, ports);
                    }
                }
        }
        return this;
    }

    public void disconnect() {
        group.shutdownGracefully();
    }

    public void reconnect(String ip, List<Integer> port) {
        if (reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disconnect();
            connect(ip, port);
        } else {
            disconnect();
        }
    }

    public Channel getChannel1() {
        return channel1;
    }

    public Channel getChannel2() {
        return channel2;
    }

    public Channel getChannel3() {
        return channel3;
    }


    public void sendMsg29999(String s) {
        boolean flag = channel1 != null && isConnect;
        if (!flag) {
            Log.e(TAG, "------尚未连接");
            return;
        }
//        final String s = gson.toJson(bean);
        channel1.writeAndFlush(s).addListener(new FutureListener() {

            @Override
            public void success() {
                Log.e(TAG, "发送成功--->" + s);
            }

            @Override
            public void error() {
                Log.e(TAG, "发送失败--->" + s);
            }
        });
    }

    public void sendMsg30001(String s) {
        boolean flag = channel2 != null && isConnect;
        if (!flag) {
            Log.e(TAG, "------尚未连接");
            return;
        }
//        final String s = gson.toJson(bean);
        channel2.writeAndFlush(s).addListener(new FutureListener() {

            @Override
            public void success() {
                Log.e(TAG, "发送成功--->" + s);
            }

            @Override
            public void error() {
                Log.e(TAG, "发送失败--->" + s);
            }
        });
    } public void sendMsg30003(String s) {
        boolean flag = channel3 != null && isConnect;
        if (!flag) {
            Log.e(TAG, "------尚未连接");
            return;
        }
//        final String s = gson.toJson(bean);
        channel3.writeAndFlush(s).addListener(new FutureListener() {

            @Override
            public void success() {
                Log.e(TAG, "发送成功--->" + s);
            }

            @Override
            public void error() {
                Log.e(TAG, "发送失败--->" + s);
            }
        });
    }



    /**
     * 设置重连次数
     *
     * @param reconnectNum 重连次数
     */
    public void setReconnectNum(int reconnectNum) {
        this.reconnectNum = reconnectNum;
    }

    /**
     * 设置重连时间间隔
     *
     * @param reconnectIntervalTime 时间间隔
     */
    public void setReconnectIntervalTime(long reconnectIntervalTime) {
        this.reconnectIntervalTime = reconnectIntervalTime;
    }

    public boolean getConnectStatus() {
        return isConnect;
    }

    /**
     * 设置连接状态
     *
     * @param status
     */
    public void setConnectStatus(boolean status) {
        this.isConnect = status;
    }

    public void setListener(NettyListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener == null ");
        }
        this.listener = listener;
    }
}
