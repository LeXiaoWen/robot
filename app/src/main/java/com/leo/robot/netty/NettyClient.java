package com.leo.robot.netty;

import android.util.Log;

import com.google.gson.Gson;
import com.leo.robot.netty.bean.NettyBaseFeed;

import cree.mvp.util.ui.ToastUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * netty客户端
 *
 * @author Leo
 * created at 2019/4/14 6:04 PM
 */

public class NettyClient {

    private static NettyClient nettyClient = new NettyClient();

    private EventLoopGroup group;

    private NettyListener listener;

    private Channel channel;

    private boolean isConnect = false;

    private int reconnectNum = Integer.MAX_VALUE;
//    private int reconnectNum = 2;

    private long reconnectIntervalTime = 3000;
    public final static String TAG = NettyClient.class.getName();
    private final Gson gson;
    private Bootstrap bootstrap;

    public NettyClient() {
        gson = new Gson();
    }

    public static NettyClient getInstance() {
        return nettyClient;
    }

    public synchronized NettyClient connect(String ip,int port) {
        if (!isConnect) {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap().group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG,  Integer.valueOf(1024))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer(listener));
            try {
                ChannelFuture future = bootstrap.connect(ip, port).sync();
                if (future != null && future.isSuccess()) {
                    channel = future.channel();
                    isConnect = true;
                } else {
                    isConnect = false;
                }


            } catch (Exception e) {
                e.printStackTrace();
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                reconnect(ip,port);
            }
        }
        return this;
    }

    public void disconnect() {
        group.shutdownGracefully();
    }

    public void reconnect(String ip,int port) {
        if (reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disconnect();
            connect(ip,port);
        } else {
            disconnect();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    /**
     * 发送消息
     *
     * @param vo
     * @param futureListener
     */
    public void sendMessage(NettyBaseFeed vo, FutureListener futureListener) {
        boolean flag = channel != null && isConnect;
        if (!flag) {
            Log.e(TAG, "------尚未连接");
            return;
        }
        final String s = gson.toJson(vo);
        if (futureListener == null) {
            channel.writeAndFlush(s).addListener(new FutureListener() {

                @Override
                public void success() {
                    Log.e(TAG, "发送成功--->" + s);
                }

                @Override
                public void error() {
                    Log.e(TAG, "发送失败--->" + s);
                }
            });
        } else {
            channel.writeAndFlush(s).addListener(futureListener);
        }
    }

    public synchronized void sendMsg(String msg) {
        if (channel == null) {
            Log.e(TAG, "send: channel is null");
            return;
        }

        if (!channel.isWritable()) {
            Log.e(TAG, "send: channel is not Writable");
            return;
        }

        if (!channel.isActive()) {
            Log.e(TAG, "send: channel is not active!");
            return;
        }
        if (channel != null) {
            if (isConnect) {
                ToastUtils.showShortToast("发送指令为： " + msg);
                channel.writeAndFlush(msg);
            } else {
                ToastUtils.showShortToast("未连接！");
            }
        }

    }

    public void sendMsgTest(String s){
        boolean flag = channel != null && isConnect;
        if (!flag) {
            Log.e(TAG, "------尚未连接");
            return;
        }
//        final String s = gson.toJson(bean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    channel.writeAndFlush(s).addListener(new FutureListener() {

                        @Override
                        public void success() {
                            Log.e(TAG, "发送成功--->" + s);
                        }

                        @Override
                        public void error() {
                            Log.e(TAG, "发送失败--->" + s);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
