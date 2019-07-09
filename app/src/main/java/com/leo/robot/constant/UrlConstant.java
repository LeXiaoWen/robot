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
    public static final String MASTER_NETTY_HOST = "192.168.1.202";
    public static final String VISION_NETTY_HOST = "192.168.1.203";
//    public static final String MAIN_ARM_NETTY_HOST = "192.168.1.30";
//    public static final String FLOW_ARM_NETTY_HOST = "192.168.1.40";

    //测试
    public static final String MAIN_ARM_NETTY_HOST = "192.168.1.23";
    public static final String FLOW_ARM_NETTY_HOST = "192.168.1.24";
//    public static final String MASTER_NETTY_HOST = "192.168.1.11";
//    public static final String MASTER_NETTY_HOST = "192.168.2.171";
//    public static final String VISION_NETTY_HOST = "192.168.1.5";

    /**
     * socket 服务器端口号
     */
    public static final int SOCKET_PORT = 8001;

    public static final int MAIN_ARM_PORT1 = 29999;
    public static final int MAIN_ARM_PORT2 = 30001;
    public static final int MAIN_ARM_PORT3 = 30003;

    public static final int FLOW_ARM_PORT1 = 29999;
    public static final int FLOW_ARM_PORT2 = 30001;
    public static final int FLOW_ARM_PORT3 = 30003;


    public static final String URL_TEST = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    public static final String URL_TEST1 = "rtmp://58.200.131.2:1935/livetv/hunantv";
    /**
     * 行线相机
     */
//    public static final String LINE_CAMERA_URL = "http://192.168.1.203:8080/stream?topic=/sys_1/output&type=mjpeg";
    public static final String LINE_CAMERA_URL = "http://192.168.1.11:8080/stream?topic=/usb_cam/image_raw&type=ros_compressed";
    /**
     * 引流线相机
     */
    public static final String DRAIN_LINE_CAMERA_URL = "http://192.168.1.203:8080/stream?topic=/sys_0/output&type=mjpeg";
    /**
     * 手爪相机
     */
    public static final String CLUTCH_CAMERA_URL = "http://192.168.1.106:8080/stream?topic=/usb_cam/image_raw&type=ros_compressed";
    /**
     * 云台相机
     */
    public static final String CAMERA_URL = "http://192.168.1.9:8080/stream?topic=/usb_cam/image_raw&type=ros_compressed";
//    public static final String CAMERA_URL = "http://192.168.1.10:8080/stream?topic=/usb_cam/image_raw&type=ros_compressed";

    /**
     * 主机械臂相机
     */
    public static final String ARM_MAIN_CAMERA_UREL = "http://192.168.1.203:8080/stream?topic=/usb_cam3/image_raw&type=ros_compressed";
//    public static final String ARM_MAIN_CAMERA_UREL = "http://192.168.1.11:8080/stream?topic=/usb_cam/image_raw&type=ros_compressed";
    /**
     * 从机械臂相机
     */
    public static final String ARM_FLOW_CAMERA_UREL = "http://192.168.1.203:8080/stream?topic=/usb_cam4/image_raw&type=ros_compressed";

    public static final String[] URL = {LINE_CAMERA_URL, DRAIN_LINE_CAMERA_URL, CAMERA_URL, ARM_MAIN_CAMERA_UREL, ARM_FLOW_CAMERA_UREL};


}
