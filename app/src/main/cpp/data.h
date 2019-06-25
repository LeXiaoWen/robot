#ifndef DATA_H
#define DATA_H
#include "iostream"
#include <cmath>
#include "string.h"
using namespace std;
//#include "data_global.h"


#define MASTER_POWER_MA  "Master_Power_Ma"    //电源管理模块电量
#define MASTER_POWER_V   "Master_power_V"     //电源管理模块电压
#define MASTER_POWER_A   "Master_Power_A"     //电源管理模块电流
#define WIRE_STRIPPER_MA "Wire_Stripper_Ma"   //剥线工具电量
#define CONNECT_WIRE_MA  "Connect_Wire_Ma"    //接线工具电量
#define CUT_WIRE_MA      "Cut_Wire_Ma"        //剪线工具电量
#define HAND_GRAB_MA     "Hand_Grab_Ma"       //手抓工具电量


class Data //DATASHARED_EXPORT
{
public:
    float MasterPower_Ma;
    float MasterPower_V;
    float MasterPower_A;
    float WireStripperMa;
    float ConnectWireMa;
    float CutWireMa;
    float HandGrabMa;
public:
    Data();
    void  StrToHex(const char str[], unsigned char buf[], int len);
    float StrToFloat(string str);
    void GetDevicePowerMsg(string msg);
    float ReadDevicePower(string device);


};

#endif // DATA_H
