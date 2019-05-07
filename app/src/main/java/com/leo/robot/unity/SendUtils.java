package com.leo.robot.unity;

import com.unity3d.player.UnityPlayer;

/**
 * 与unity通信
 * created by Leo on 2019/5/6 22 : 15
 */


public class SendUtils {

    private static String test = "{\"msgId\":\"MA\",\"params\":{\"A\":\"45.00\",\"B\":\"-90.00\",\"C\":\"0.00\",\"D\":\"-90.00\",\"E\":\"0.00\",\"F\":\"0.00\"}}";

    /**
    * 设置机械运动速度，类型float
    *
    *@author Leo
    *created at 2019/5/6 10:17 PM
    */
    public static void sendMoveSpeed(String speed){
        UnityPlayer.UnitySendMessage("MessageController","SetMoveSpeed",speed);
    }

    /**
    * 设置主臂旋转，类型string  （将json以string的形式传参）
    *
    *@author Leo
    *created at 2019/5/6 10:19 PM
    */
    public static void sendMainArmRotate(){
        UnityPlayer.UnitySendMessage("MessageController","SetMaRobotValue",test);
    }

    /**
    * 设置从臂旋转，类型string  （将json以string的形式传参）
    *
    *@author Leo
    *created at 2019/5/6 10:19 PM
    */
    public static void sendFlowArmRotate(String rotate){
        UnityPlayer.UnitySendMessage("MessageController","SetFaRobotValue",rotate);
    }
}
