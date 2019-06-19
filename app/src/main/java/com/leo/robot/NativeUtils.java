package com.leo.robot;

/**
 * @author Wastrel
 * @version 1.0
 * @date 2019/6/18 14:59
 * @copyRight 四川金信石信息技术有限公司
 * @since 1.0
 */
public class NativeUtils {
    static {
        System.loadLibrary("ur10");
    }

    public static native String init(String msg, String mode);

    public static native String move(String cmd, String mode);

    public static native String join(String cmd, String mode);

    public static native String stop(String mode);
}
