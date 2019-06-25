package com.leo.robot.constant;

/**
 * created by Leo on 2019/6/19 23 : 20
 */


public class URConstants {
    public static String Marm = "Marm";
    public static String Farm = "Farm";

    //关节控制
    public static String ACTION_J0_1 = "ACTION_J0_1";
    public static String ACTION_J0_2 = "ACTION_J0_2";
    public static String ACTION_J1_1 = "ACTION_J1_1";
    public static String ACTION_J1_2 = "ACTION_J1_2";
    public static String ACTION_J2_1 = "ACTION_J2_1";
    public static String ACTION_J2_2 = "ACTION_J2_2";
    public static String ACTION_J3_1 = "ACTION_J3_1";
    public static String ACTION_J3_2 = "ACTION_J3_2";
    public static String ACTION_J4_1 = "ACTION_J4_1";
    public static String ACTION_J4_2 = "ACTION_J4_2";
    public static String ACTION_J5_1 = "ACTION_J5_1";
    public static String ACTION_J5_2 = "ACTION_J5_2";

    //控制末端位移
    public static String ACTION_MOVE_1 = "ACTION_MOVE_1";
    public static String ACTION_MOVE_2 = "ACTION_MOVE_2";
    public static String ACTION_MOVE_3 = "ACTION_MOVE_3";
    public static String ACTION_MOVE_4 = "ACTION_MOVE_4";
    public static String ACTION_MOVE_5 = "ACTION_MOVE_5";
    public static String ACTION_MOVE_6 = "ACTION_MOVE_6";

    //控制末端位姿
    public static String ACTION_POSE_1 = "ACTION_POSE_1";
    public static String ACTION_POSE_2 = "ACTION_POSE_2";
    public static String ACTION_POSE_3 = "ACTION_POSE_3";
    public static String ACTION_POSE_4 = "ACTION_POSE_4";
    public static String ACTION_POSE_5 = "ACTION_POSE_5";
    public static String ACTION_POSE_6 = "ACTION_POSE_6";


    //DashBoard 命令
    public static String CMD_POWER_ON = "CMD_POWER_ON";
    public static String CMD_POWER_OFF = "CMD_POWER_OFF";
    public static String CMD_UNLOCK_STOP = "CMD_UNLOCK_STOP";
    public static String CMD_SHUT_DOWN = "CMD_SHUT_DOWN";

    //读取末端位姿参数
    public static String Act_X = "Act_X";
    public static String Act_Y = "Act_Y";
    public static String Act_Z = "Act_Z";
    public static String Act_Rx = "Act_Rx";
    public static String Act_Ry = "Act_Ry";
    public static String Act_Rz = "Act_Rz";

    //读取关节角度
    public static String J0_A = "J0_A";
    public static String J1_A = "J1_A";
    public static String J2_A = "J2_A";
    public static String J3_A = "J3_A";
    public static String J4_A = "J4_A";
    public static String J5_A = "J5_A";

    //读取关节温度
    public static String J0_T = "J0_T";
    public static String J1_T = "J1_T";
    public static String J2_T = "J2_T";
    public static String J3_T = "J3_T";
    public static String J4_T = "J4_T";
    public static String J5_T = "J5_T";

    //状态数据
    public static String Robot_Mod = "Robot_Mod";
    public static String Safe_Mod = "Safe_Mod";
    public static String Program_State = "Program_State";
    public static String Power_A = "Power_A";
    public static String Power_V = "Power_V";

    //机器模式
    public static String ROBOT_MODE_DISCONNECTED = "DISCONNECTED";
    public static String ROBOT_MODE_CONFIRM_SAFETY = "CONFIRM_SAFETY";
    public static String ROBOT_MODE_BOOTING = "BOOTING";
    public static String ROBOT_MODE_POWER_OFF = "POWER_OFF";
    public static String ROBOT_MODE_POWER_ON = "POWER_ON";
    public static String ROBOT_MODE_IDLE = "IDLE";
    public static String ROBOT_MODE_BACKDRIVE = "BACKDRIVE";
    public static String ROBOT_MODE_RUNNING = "RUNNING";
    public static String ROBOT_MODE_UPDATING_FIRMWARE = "UPDATING_FIRMWARE";


    //安全模式
    public static String SAFETY_MODE_NORMAL="NORMAL";
    public static String SAFETY_MODE_REDUCED="REDUCED";
    public static String SAFETY_MODE_PROTECTIVE_STOP="PROTECTIVE_STOP";
    public static String SAFETY_MODE_RECOVERY="RECOVERY";
    public static String SAFETY_MODE_SAFEGUARD_STOP="SAFEGUARD_STOP";
    public static String SAFETY_MODE_SYSTEM_EMERGENCY_STOP="SYSTEM_EMERGENCY_STOP";
    public static String SAFETY_MODE_ROBOT_EMERGENCY_STOP="ROBOT_EMERGENCY_STOP";
    public static String SAFETY_MODE_VIOLATION="VIOLATION";
    public static String SAFETY_MODE_FAULT="FAULT";

    //程序状态
    public static String idle="idle";
    public static String running="running";
    public static String paused="paused";

    //电量
    public static String Master_Power_Ma = "Master_Power_Ma";
    public static String Master_Power_V = "Master_Power_V";
    public static String Master_Power_A = "Master_Power_A";
    public static String Wire_Stripper_Ma = "Wire_Stripper_Ma";
    public static String Connect_Wire_Ma = "Connect_Wire_Ma";
    public static String Cut_Wire_Ma = "Cut_Wire_Ma";
    public static String Hand_Grab_Ma = "Hand_Grab_Ma";



}
