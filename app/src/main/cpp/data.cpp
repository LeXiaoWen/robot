#include "data.h"


Data::Data()
{
}


void Data::StrToHex(const char str[], unsigned char buf[], int len)
{
      int i = 0;
      int n = 0;
      while (*str != 0 && (n = ((i++) >> 1)) <len) {
          buf[n] <<= 4;
          if (*str >= '0' && *str <= '9')
              {
                 buf[n] |= *str - '0';
              }
          else if (*str >= 'a' && *str <= 'f')
              {
                  buf[n] |= *str - 'a' + 10;
               }
          else if (*str >= 'A' && *str <= 'F')
              {
                  buf[n] |= *str - 'A' + 10;
                }
                str++;
            }
            len = n;
}

float Data::StrToFloat(string str)
{

    unsigned char tmp[4]={0};
    union
     {
       unsigned char data[4];
       float m;
     }data_type;  
    StrToHex(str.c_str(), tmp,4);
   // memcpy(data_type.data,tmp,4);s
    data_type.data[0]=tmp[0];
    data_type.data[1]=tmp[1];
    data_type.data[2]=tmp[2];
    data_type.data[3]=tmp[3];
    return data_type.m;
}
//根据电量数据位信息解析数据
void Data::GetDevicePowerMsg(string msg) //输入数据位字符串数据
{
    #define POWER_LEN 56
    string str;
   if(msg.length()!=POWER_LEN) return;
    str=msg.substr(0,8);
    MasterPower_Ma=StrToFloat(str);
    str=msg.substr(8,8);
    MasterPower_V=StrToFloat(str);
    str=msg.substr(16,8);
    MasterPower_A=StrToFloat(str);
    str=msg.substr(24,8);
    WireStripperMa=StrToFloat(str);
    str=msg.substr(32,8);
    ConnectWireMa=StrToFloat(str);
    str=msg.substr(40,8);
    CutWireMa=StrToFloat(str);
    str=msg.substr(48,8);
    HandGrabMa=StrToFloat(str);
}
float Data::ReadDevicePower(string device)
{
    if(device=="Master_Power_Ma"){ return MasterPower_Ma;}
    else if(device=="Master_Power_V"){ return MasterPower_V;}
    else if(device=="Master_Power_A"){ return MasterPower_A;}
    else if(device=="Wire_Stripper_Ma"){ return WireStripperMa;}
    else if(device=="Connect_Wire_Ma"){ return ConnectWireMa;}
    else if(device=="Cut_Wire_Ma"){ return CutWireMa;}
    else if(device=="Hand_Grab_Ma"){ return HandGrabMa;}
    else return 0;
}
