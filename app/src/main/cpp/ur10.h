#ifndef UR10_H
#define UR10_H

//#include "ur10_global.h"
#include "iostream"
#include <cmath>
using namespace std;
//---------------------------Action---------------------------//
//关节控制
#define    ACTION_J0_1         "ACTION_J0_1"
#define    ACTION_J0_2         "ACTION_J0_2"
#define    ACTION_J1_1         "ACTION_J1_1"
#define    ACTION_J1_2         "ACTION_J1_2"
#define    ACTION_J2_1         "ACTION_J2_1"
#define    ACTION_J2_2         "ACTION_J2_2"
#define    ACTION_J3_1         "ACTION_J3_1"
#define    ACTION_J3_2         "ACTION_J3_2"
#define    ACTION_J4_1         "ACTION_J4_1"
#define    ACTION_J4_2         "ACTION_J4_2"
#define    ACTION_J5_1         "ACTION_J5_1"
#define    ACTION_J5_2         "ACTION_J5_2"
//末端位移
#define    ACTION_MOVE_1       "ACTION_MOVE_1"
#define    ACTION_MOVE_2       "ACTION_MOVE_2"
#define    ACTION_MOVE_3       "ACTION_MOVE_3"
#define    ACTION_MOVE_4       "ACTION_MOVE_4"
#define    ACTION_MOVE_5       "ACTION_MOVE_5"
#define    ACTION_MOVE_6       "ACTION_MOVE_6"
//末端位姿
#define    ACTION_POSE_1       "ACTION_POSE_1"
#define    ACTION_POSE_2       "ACTION_POSE_2"
#define    ACTION_POSE_3       "ACTION_POSE_3"
#define    ACTION_POSE_4       "ACTION_POSE_4"
#define    ACTION_POSE_5       "ACTION_POSE_5"
#define    ACTION_POSE_6       "ACTION_POSE_6"
//其他命令
#define    ACTION_STOPJ        "ACTION_STOPJ"
#define    ACTION_TO_HOME      "ACTION_TO_HOME"
//DashBoard 命令
#define    CMD_POWER_ON        "CMD_POWER_ON"
#define    CMD_POWER_OFF       "CMD_POWER_OFF"
#define    CMD_UNLOCK_STOP     "CMD_UNLOCK_STOP"
#define    CMD_SHUT_DOWN       "CMD_SHUT_DOWN"

//-----------------------------param----------------------------//

#define    Act_X         "Act_X"
#define    Act_Y         "Act_Y"
#define    Act_Z         "Act_Z"
#define    Act_Rx        "Act_Rx"
#define    Act_Ry        "Act_Ry"
#define    Act_Rz        "Act_Rz"

#define    J0_A          "J0_A"
#define    J1_A          "j1_A"
#define    J2_A          "j2_A"
#define    J3_A          "j3_A"
#define    J4_A          "J4_A"
#define    J5_A          "J5_A"

#define    J0_T          "J0_T"
#define    J1_T          "J1_T"
#define    J2_T          "J2_T"
#define    J3_T          "J3_T"
#define    J4_T          "J4_T"
#define    J5_T          "J5_T"

#define    Robot_Mod     "Robot_Mod"
#define    Safe_Mod      "Safe_Mod"
#define    Program_State "Program_State"

#define    Power_A       "Power_A"
#define    Power_V       "Power_V"

class  UR10  //UR10SHARED_EXPORT
{

public:
    double actual_X,actual_Y,actual_Z;
    double actual_Rx,actual_Ry,actual_Rz;

    double actJoint_Base, actJoint_Shoulder,actJoint_Elbow;
    double actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3;
public:
    string act_x,act_y,act_z;
    string act_Rx,act_Ry,act_Rz;

    string j0_Angle,j1_Angle,j2_Angle;
    string j3_Angle,j4_Angle,j5_Angle;

    string j0_T,j1_T,j2_T;
    string j3_T,j4_T,j5_T;
    bool linkState=false;
    string robot_Mod;
    string safe_Mod;
    string program_state;
    string power_V;
    string power_A;


    // QByteArray buffer_30003;
    uint8_t Recv_buf[1108];
    string str_29999;
    float move_v=0.04;
    float move_a=0.04;

public:
    void GetDataPort29999(string str);
    void GetDataPort30003();
    string ActionJoint(string cmd);
    string ActionMove(string cmd);
    string ActionPose(string cmd);
    string ActionDash(string cmd);
    string ActionStopJ();
    string ActionToHome();
    void DealArmFeedbackMsg();
    unsigned int getbitu(const unsigned char *buff, int pos, int len);
    double HexToDouble(const unsigned char* buf);
    string  doubleToString(double num);
    string doubleToString_4(const double &dbNum);
    string movel(double x,double y,double z,double Rx,double Ry,double Rz,float a,float v,float t,float r);
    string movel_pose_add(double x,double y,double z,double Rx,double Ry,double Rz);
    string movej(double j0,double j1,double j2,double j3,double j4,double j5,float a,float v,float t,float r);
    void SetMoveSpeed(float v);
    void SetMoveAcc(float a);
};

#endif // UR10_H

