#ifndef UR_H
#define UR_H
#include "ur10.h"
#include "iostream"
#include "string.h"
//#include "ur_global.h"
#define Marm "Marm"
#define Farm "Farm"


class  UR
{
public:
    UR10 UR_M;
    UR10 UR_F;
    float m=0.54f;
public:
    UR();
public:
    void GetDataPort29999(string str_29999,string name);
    void GetDataPort30003(string str_30003,string name);
    string ActionJoint(string cmd,string name);
    string ActionMove(string cmd,string name);
    string ActionPose(string cmd,string name);
    string ActionDash(string cmd,string name);
    string ActionStopJ(string name);
    string ActionToHome(string name);
    void DealArmFeedbackMsg(string name);
    void SetMoveSpeed(float v,string name);
    void SetMoveAcc(float a,string name);
    void StrToHex(const char str[], unsigned char buf[], int len);
    string ReadURparam(string param,string name);
};

#endif // UR_H
