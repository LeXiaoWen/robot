package com.leo.robot;

/**
 *
 * jni工具类
 *
 * created by Leo on 2019/6/13 20 : 53
 */


public class JNIUtils {
    private static volatile JNIUtils mJNIUtils;

    private JNIUtils() {
    }

    public static JNIUtils getInstance() {
        synchronized (JNIUtils.class) {

            if (mJNIUtils == null) {
                synchronized (JNIUtils.class) {
                    if (mJNIUtils == null) {
                        mJNIUtils = new JNIUtils();
                    }
                }

            }
            return mJNIUtils;
        }
    }

    static {
        System.loadLibrary("ur10");
    }

    public native String hello();

    public native String getDouble(double num);

    /**
     * 获取29999端口字符串数据
     *
     * @author Leo
     * created at 2019/6/13 10:41 PM
     */
    public native void GetDataPort29999(String s);

    /**
     * 获取30003端口16进制数据
     *
     * @author Leo
     * created at 2019/6/13 10:53 PM
     */
    public native void GetDataPort30003();

    /**
     * 末端位移控制
     *
     * @author Leo
     * created at 2019/6/13 10:54 PM
     */
    public native String ActionMove(String s);

    /**
     * 末端位姿控制
     *
     * @author Leo
     * created at 2019/6/13 10:55 PM
     */
    public native String ActionPose(String s);

    /**
     * 关节控制
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public native String ActionJoint(String s);

    /**
     * Dash命令
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public native String ActionDash(String s);

    /**
     * 动作停止命令
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public native String ActionStopJ();

    /**
     * 16进制转double
     *
     * @author Leo
     * created at 2019/6/13 10:59 PM
     */
    public native double HexToDouble(byte[] chars);

    /**
     * 设置机械臂移动速度
     *
     * @author Leo
     * created at 2019/6/13 11:01 PM
     */
    public native void SetMoveSpeed(float v);
    /**
    * 设置机械臂运动加速度
    *
    *@author Leo
    *created at 2019/6/13 11:02 PM
    */
    public native void SetMoveAcc(float v);

    /**
    * double转字符串
    *
    *@author Leo
    *created at 2019/6/13 11:03 PM
    */
    public native String DoubleToString(double num);

    /**
     * double转字符串,保留小数点后四位
     *
     *@author Leo
     *created at 2019/6/13 11:03 PM
     */
    public native String doubleToString4(double num);


    /**
     * 获取29999端口字符串数据
     *
     * @author Leo
     * created at 2019/6/13 10:41 PM
     */
    public native void GetDataPort29999(String s,String name);

    /**
     * 获取30003端口16进制数据
     *
     * @author Leo
     * created at 2019/6/13 10:53 PM
     */
    public native void GetDataPort30003(String s, String name);

    /**
     * 末端位移控制
     *
     * @author Leo
     * created at 2019/6/13 10:54 PM
     */
    public native String ActionMove(String s,String name);

    /**
     * 末端位姿控制
     *
     * @author Leo
     * created at 2019/6/13 10:55 PM
     */
    public native String ActionPose(String s,String name);

    /**
     * 关节控制
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public native String ActionJoint(String s,String name);

    /**
     * Dash命令
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public native String ActionDash(String s,String name);

    /**
     * 动作停止命令
     *
     * @author Leo
     * created at 2019/6/13 10:56 PM
     */
    public native String ActionStopJ(String name);



    /**
     * 设置机械臂移动速度
     *
     * @author Leo
     * created at 2019/6/13 11:01 PM
     */
    public native void SetMoveSpeed(float v,String name);
    /**
     * 设置机械臂运动加速度
     *
     *@author Leo
     *created at 2019/6/13 11:02 PM
     */
    public native void SetMoveAcc(float v,String name);

    public native String testJni();
    public native String testJni2(String msg);
    public native String testJni3(String msg,String mode,String name);

}
