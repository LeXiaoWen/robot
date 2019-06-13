#ifndef UR10_H
#define UR10_H

//#include "ur10_global.h"
#include <jni.h>
#include "string"
using namespace std;

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



class  UR10
{



private:
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
    double robot_Mod;
    double safe_Mod;
    double Power_V;
    double Power_A;

    jbyteArray buffer_30003;
    uint8_t Recv_buf[1108];
    string str_29999;
    float move_v=0.08;
    float move_a=0.08;

public:
    string GetDataPort29999(string str);
    void GetDataPort30003();

    string ActionJoint(string cmd);
    string ActionMove(string cmd);
    string ActionPose(string cmd);
    string ActionDash(string cmd);
    string ActionStopJ();
    string ActionToHome();
    void DealArmFeedbackMsg();
    uint getbitu(const unsigned char *buff, int pos, int len);
    double HexToDouble(const unsigned char* buf);
    string movel(double x,double y,double z,double Rx,double Ry,double Rz,float a,float v,float t,float r);
    string movel_pose_add(double x,double y,double z,double Rx,double Ry,double Rz);
    string movej(double j0,double j1,double j2,double j3,double j4,double j5,float a,float v,float t,float r);
    void SetMoveSpeed(float v);
    void SetMoveAcc(float a);
    string DoubleToString(double num);
    string doubleToString4(const double dbNum);
};

#endif // UR10_H

