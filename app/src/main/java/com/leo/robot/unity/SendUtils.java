package com.leo.robot.unity;

import com.unity3d.player.UnityPlayer;

/**
 * 与unity通信
 * created by Leo on 2019/5/6 22 : 15
 */


public class SendUtils {

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
    public static void sendMainArmRotate(String rotate){
        UnityPlayer.UnitySendMessage("MessageController","SetMaRobotValue",rotate);
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
