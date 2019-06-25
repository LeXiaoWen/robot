package com.leo.robot;

/**
 * jni工具类
 * <p>
 * created by Leo on 2019/6/13 20 : 53
 */


public class JNIUtils {

    static {
        System.loadLibrary("ur10");
    }

    /**
     * 获取29999端口字符串数据
     *
     * @author Leo
     * created at 2019/6/13 10:41 PM
     */
    public static native String GetDataPort29999(String msg, String mode);

    /**
     * 获取30003端口16进制数据
     *
     * @author Leo
     * created at 2019/6/13 10:53 PM
     */
    public static native String GetDataPort30003(String msg, String mode);

    /**
     * 末端位移控制
     *
     * @author Leo
     * created at 2019/6/13 10:54 PM
     */
    public static native String ActionMove(String msg, String mode);

    /**
     * 末端位姿控制
     *
     * @author Leo
     * created at 2019/6/13 10:55 PM
     */
    public static native String ActionPose(String msg, String mode);

    /**
     * 关节控制
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public static native String ActionJoint(String msg, String mode);

    /**
     * Dash命令
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public static native String ActionDash(String msg, String mode);

    /**
     * 动作停止命令
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public static native String ActionStopJ(String mode);


    /**
     * 设置机械臂移动速度
     *
     * @author Leo
     * created at 2019/6/13 11:01 PM
     */
    public static native void SetMoveSpeed(float v, String mode);

    /**
     * 设置机械臂运动加速度
     *
     * @author Leo
     * created at 2019/6/13 11:02 PM
     */
    public static native void SetMoveAcc(float v, String mode);

    /**
    * 获取机械臂参数
    *
    *@author Leo
    *created at 2019/6/19 11:08 PM
    */
    public static native String ReadURparam(String params,String mode);

    /**
    * 接收电量信息
    *
    *@author Leo
    *created at 2019/6/25 10:23 PM
    */
    public static native void GetDevicePowerMsg(String msg);

    /**
    * 读取设备电量
    *
    *@author Leo
    *created at 2019/6/25 10:24 PM
    */
    public static native float ReadDevicePower(String device);

}
