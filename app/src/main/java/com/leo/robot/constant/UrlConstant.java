package com.leo.robot.constant;

/**
 * ip地址常量类
 *
 * @author Leo
 * created at 2019/4/15 8:40 PM
 */
public class UrlConstant {
    /**
     * socket 服务器IP地址
     */
    public static final String SOCKET_HOST = "192.168.1.202";
    /**
     * socket 服务器端口号
     */
    public static final int SOCKET_PORT = 8001;


    public static final String URL_TEST = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    public static final String URL_TEST1 = "rtmp://58.200.131.2:1935/livetv/hunantv";
    /**
     * 行线相机
     */
    public static final String LINE_CAMERA_URL = "rtsp://192.168.1.106:8554/back";
    /**
     * 引流线相机
     */
    public static final String DRAIN_LINE_CAMERA_URL = "rtsp://192.168.1.106:8080/back";
    /**
     * 手爪相机
     */
    public static final String CLUTCH_CAMERA_URL = "rtsp://192.168.1.106:9090/back";
    /**
     * 云台相机
     */
    public static final String CAMERA_URL = "http://192.168.1.199:8004";

    /**
     * 机械臂相机
     */
    public static final String ARM_CAMERA_UREL = "";

}
