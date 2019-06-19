#include "ur.h"

using namespace std;

UR::UR() {}
void UR::GetDataPort29999(string str_29999,string name)
{
    if(name=="Marm")
    {
        UR_M.GetDataPort29999(str_29999);
    }
    else if(name=="Farm")
    {
        UR_F.GetDataPort29999(str_29999);
    }
    else
        return;
}

void UR::GetDataPort30003(string str_30003,string name)
{

    if(name=="Marm")
    {
        StrToHex(str_30003.c_str(), UR_M.Recv_buf, sizeof(UR_M.Recv_buf));
        UR_M.GetDataPort30003();
    }
    else if(name=="Farm")
    {
        StrToHex(str_30003.c_str(), UR_F.Recv_buf, sizeof(UR_F.Recv_buf));
        UR_F.GetDataPort30003();
    }
    else
        return;
}


string UR::ActionJoint(string cmd,string name)
{
    if(name=="Marm")
    {
        return UR_M.ActionJoint(cmd);
    }
    else if(name=="Farm")
    {
        return UR_F.ActionJoint(cmd);
    }
    else
        return "Error";

}
string UR::ActionMove(string cmd,string name)
{
    if(name=="Marm")
    {
        return UR_M.ActionMove(cmd);
    }
    else if(name=="Farm")
    {
        return UR_F.ActionMove(cmd);
    }
    else
        return "Error";

}
string UR::ActionPose(string cmd,string name)
{
    if(name=="Marm")
    {
        return UR_M.ActionPose(cmd);
    }
    else if(name=="Farm")
    {
        return UR_F.ActionPose(cmd);
    }
    else
        return "Error";
}
string UR::ActionDash(string cmd,string name)
{
    if(name=="Marm")
    {
        return UR_M.ActionDash(cmd);
    }
    else if(name=="Farm")
    {
        return UR_F.ActionDash(cmd);
    }
    else
        return "Error";

}
string UR::ActionStopJ(string name)
{
    if(name=="Marm")
    {
        return UR_M.ActionStopJ();
    }
    else if(name=="Farm")
    {
        return UR_F.ActionStopJ();
    }
    else
        return "Error";
}
string UR::ActionToHome(string name)
{

    if(name=="Marm")
    {
        return "NULL";
    }
    else if(name=="Farm")
    {
        return "NULL";
    }
    else
        return "Error";
}
void UR::DealArmFeedbackMsg(string name)
{
    if(name=="Marm")
    {

    }
    else if(name=="Farm")
    {

    }
    else
        return;
}
void UR::SetMoveSpeed(float v,string name)
{
    if(name=="Marm")
    {
        UR_M.SetMoveSpeed(v);
    }
    else if(name=="Farm")
    {
        UR_F.SetMoveSpeed(v);
    }
    else
        return;
}
void UR::SetMoveAcc(float a,string name)
{
    if(name=="Marm")
    {
        UR_M.SetMoveAcc(a);
    }
    else if(name=="Farm")
    {
        UR_F.SetMoveAcc(a);
    }
    else
        return;

}

void UR::StrToHex(const char str[], unsigned char buf[], int len)
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


string UR::ReadURparam(string param,string name)
{

    if(name=="Marm")
    {
        if(param== "Act_X"){return UR_M.act_x;}
        else if(param== "Act_Y"){return UR_M.act_y;}
        else if(param== "Act_Z"){return UR_M.act_z;}
        else if(param== "Act_Rx"){return UR_M.act_Rx;}
        else if(param== "Act_Ry"){return UR_M.act_Ry;}
        else if(param== "Act_Rz"){return UR_M.act_Rz;}

        else if(param== "J0_A"){return UR_M.j0_Angle;}
        else if(param== "J1_A"){return UR_M.j1_Angle;}
        else if(param== "J2_A"){return UR_M.j2_Angle;}
        else if(param== "J3_A"){return UR_M.j3_Angle;}
        else if(param== "J4_A"){return UR_M.j4_Angle;}
        else if(param== "J5_A"){return UR_M.j5_Angle;}

        else if(param== "J0_T"){return UR_M.j0_T;}
        else if(param== "J1_T"){return UR_M.j1_T;}
        else if(param== "J2_T"){return UR_M.j2_T;}
        else if(param== "J3_T"){return UR_M.j3_T;}
        else if(param== "J4_T"){return UR_M.j4_T;}
        else if(param== "J5_T"){return UR_M.j5_T;}

        else if(param== "Robot_Mod"){return UR_M.robot_Mod;}
        else if(param== "Safe_Mod"){return UR_M.safe_Mod;}
        else if(param== "Program_State"){return UR_M.program_state;}
        else if(param== "Power_A"){return UR_M.power_A;}
        else if(param== "Power_V"){return UR_M.power_V;}
        else return "Error";
    }
    else if(name=="Farm")
    {
        if(param== "Act_X"){return UR_F.act_x;}
        else if(param== "Act_Y"){return UR_F.act_y;}
        else if(param== "Act_Z"){return UR_F.act_z;}
        else if(param== "Act_Rx"){return UR_F.act_Rx;}
        else if(param== "Act_Ry"){return UR_F.act_Ry;}
        else if(param== "Act_Rz"){return UR_F.act_Rz;}

        else if(param== "J0_A"){return UR_F.j0_Angle;}
        else if(param== "J1_A"){return UR_F.j1_Angle;}
        else if(param== "J2_A"){return UR_F.j2_Angle;}
        else if(param== "J3_A"){return UR_F.j3_Angle;}
        else if(param== "J4_A"){return UR_F.j4_Angle;}
        else if(param== "J5_A"){return UR_F.j5_Angle;}

        else if(param== "J0_T"){return UR_F.j0_T;}
        else if(param== "J1_T"){return UR_F.j1_T;}
        else if(param== "J2_T"){return UR_F.j2_T;}
        else if(param== "J3_T"){return UR_F.j3_T;}
        else if(param== "J4_T"){return UR_F.j4_T;}
        else if(param== "J5_T"){return UR_F.j5_T;}

        else if(param== "Robot_Mod"){return UR_F.robot_Mod;}
        else if(param== "Safe_Mod"){return UR_F.safe_Mod;}
        else if(param== "Program_State"){return UR_F.program_state;}
        else if(param== "Power_A"){return UR_F.power_A;}
        else if(param== "Power_V"){return UR_F.power_V;}
        else return "Error";
    }
    else
        return  "Error";



}
