#include "ur10.h"
#include "iostream"




void UR10::GetDataPort29999(string str)
{
    str_29999=str;
}

void UR10::GetDataPort30003()
{
    uint8_t tmp[18][8]={0};
    uint8_t buf1[48]={0};
    uint8_t buf2[48]={0};
    uint8_t buf3[8]={0};
    uint8_t buf4[8]={0};
    uint8_t buf5[8]={0};
    uint8_t buf6[8]={0};
    uint8_t buf7[48]={0};
    uint8_t buf8[8]={0};

    memcpy(buf1,&Recv_buf[252],48);
    memcpy(buf2,&Recv_buf[444],48);
    memcpy(buf3,&Recv_buf[812],8);
    memcpy(buf4,&Recv_buf[756],8);
    memcpy(buf5,&Recv_buf[980],8);
    memcpy(buf6,&Recv_buf[988],8);
    memcpy(buf7,&Recv_buf[692],48);
    memcpy(buf8,&Recv_buf[1051],8);

    memcpy(&tmp[0][0],&buf1[0],8);
    memcpy(&tmp[1][0],&buf1[8],8);
    memcpy(&tmp[2][0],&buf1[16],8);
    memcpy(&tmp[3][0],&buf1[24],8);
    memcpy(&tmp[4][0],&buf1[32],8);
    memcpy(&tmp[5][0],&buf1[40],8);

    memcpy(&tmp[6][0],&buf2[0],8);
    memcpy(&tmp[7][0],&buf2[8],8);
    memcpy(&tmp[8][0],&buf2[16],8);
    memcpy(&tmp[9][0],&buf2[24],8);
    memcpy(&tmp[10][0],&buf2[32],8);
    memcpy(&tmp[11][0],&buf2[40],8);

    memcpy(&tmp[12][0],&buf7[0],8);
    memcpy(&tmp[13][0],&buf7[8],8);
    memcpy(&tmp[14][0],&buf7[16],8);
    memcpy(&tmp[15][0],&buf7[24],8);
    memcpy(&tmp[16][0],&buf7[32],8);
    memcpy(&tmp[17][0],&buf7[40],8);


    actJoint_Base=HexToDouble(tmp[0]);
    actJoint_Shoulder=HexToDouble(tmp[1]);
    actJoint_Elbow=HexToDouble(tmp[2]);
    actJoint_Wrist1=HexToDouble(tmp[3]);
    actJoint_Wrist2=HexToDouble(tmp[4]);
    actJoint_Wrist3=HexToDouble(tmp[5]);

    actual_X=HexToDouble(tmp[6]);  //m
    actual_Y=HexToDouble(tmp[7]);
    actual_Z=HexToDouble(tmp[8]);
    actual_Rx=HexToDouble(tmp[9]);
    actual_Ry=HexToDouble(tmp[10]);
    actual_Rz=HexToDouble(tmp[11]);

    act_x =doubleToString_4(actual_X*1000);
    act_y =doubleToString_4(actual_Y*1000);
    act_z =doubleToString_4(actual_Z*1000);
    act_Rx=doubleToString_4(actual_Rx);
    act_Ry=doubleToString_4(actual_Ry);
    act_Rz=doubleToString_4(actual_Rz);



    j0_Angle=doubleToString_4((180.0/3.141592)*actJoint_Base);
    j1_Angle=doubleToString_4((180.0/3.141592)*actJoint_Shoulder);
    j2_Angle=doubleToString_4((180.0/3.141592)*actJoint_Elbow);
    j3_Angle=doubleToString_4((180.0/3.141592)*actJoint_Wrist1);
    j4_Angle=doubleToString_4((180.0/3.141592)*actJoint_Wrist2);
    j5_Angle=doubleToString_4((180.0/3.141592)*actJoint_Wrist3);


    j0_T=doubleToString_4(HexToDouble(tmp[12]));
    j1_T=doubleToString_4(HexToDouble(tmp[13]));
    j2_T=doubleToString_4(HexToDouble(tmp[14]));
    j3_T=doubleToString_4(HexToDouble(tmp[15]));
    j4_T=doubleToString_4(HexToDouble(tmp[16]));
    j5_T=doubleToString_4(HexToDouble(tmp[17]));


    safe_Mod=doubleToString_4(HexToDouble(buf3));
    robot_Mod=doubleToString_4(HexToDouble(buf4));
    program_state=doubleToString_4(HexToDouble(buf8));
    power_V=doubleToString_4(HexToDouble(buf5));
    power_A=doubleToString_4(HexToDouble(buf6));


}
string UR10::ActionMove(string cmd)
{
    string str_movel="";
    string CMD=cmd;
    if(CMD==ACTION_MOVE_1)
    {
        str_movel= movel(-1.46073,actual_Y,actual_Z,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_MOVE_2)
    {
        str_movel= movel(1.46073,actual_Y,actual_Z,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_MOVE_3)
    {
        str_movel= movel(actual_X,-1.46073,actual_Z,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_MOVE_4)
    {
        str_movel= movel(actual_X,1.46073,actual_Z,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_MOVE_5)
    {
        str_movel= movel(actual_X,actual_Y,-1.46073,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_MOVE_6)
    {
        str_movel= movel(actual_X,actual_Y,1.46073,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0.0,0.0);
    }
    else
    {
        str_movel=ActionStopJ();
    }
    return str_movel;
}
string UR10::ActionPose(string cmd)
{
    string str_movel_add="";
    string CMD=cmd;
    if(CMD==ACTION_POSE_1)
    {
        str_movel_add= movel_pose_add(0,0,0,-1.541592,0,0);
    }
    else if(CMD==ACTION_POSE_2)
    {
        str_movel_add= movel_pose_add(0,0,0,1.541592,0,0);
    }
    else if(CMD==ACTION_POSE_3)
    {
        str_movel_add= movel_pose_add(0,0,0,0,-1.541592,0);
    }
    else if(CMD==ACTION_POSE_4)
    {
        str_movel_add= movel_pose_add(0,0,0,0,1.541592,0);
    }
    else if(CMD==ACTION_POSE_5)
    {
        str_movel_add= movel_pose_add(0,0,0,0,0,-1.541592);
    }
    else if(CMD==ACTION_POSE_6)
    {
        str_movel_add= movel_pose_add(0,0,0,0,0,1.541592);
    }
    else
    {
        str_movel_add=ActionStopJ();
    }
    return str_movel_add;

}
string UR10::ActionJoint(string cmd)
{
    string str_movej="";
    string CMD=cmd;
    if(CMD==ACTION_J0_1)
    {
        str_movej= movej(-6.283185,actJoint_Shoulder,actJoint_Elbow,actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J0_2)
    {
        str_movej= movej(6.283185,actJoint_Shoulder,actJoint_Elbow,actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J1_1)
    {
        str_movej= movej(actJoint_Base,-6.283185,actJoint_Elbow,actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J1_2)
    {
        str_movej= movej(actJoint_Base,6.283185,actJoint_Elbow,actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J2_1)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,-6.283185,actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J2_2)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,6.283185,actJoint_Wrist1,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J3_1)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,actJoint_Elbow,-6.283185,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J3_2)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,actJoint_Elbow,6.283185,actJoint_Wrist2,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J4_1)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,actJoint_Elbow,actJoint_Wrist1,-6.283185,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J4_2)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,actJoint_Elbow,actJoint_Wrist1,6.283185,actJoint_Wrist3,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J5_1)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,actJoint_Elbow,actJoint_Wrist1,actJoint_Wrist2,-6.283185,move_a,move_v,0.0,0.0);
    }
    else if(CMD==ACTION_J5_2)
    {
        str_movej= movej(actJoint_Base,actJoint_Shoulder,actJoint_Elbow,actJoint_Wrist1,actJoint_Wrist2,6.283185,move_a,move_v,0.0,0.0);
    }
    else
    {
        str_movej=ActionStopJ();
    }
    return str_movej;

}
string UR10::ActionDash(string cmd)
{

    string str_dashboard="";
    string CMD=cmd;
    if(CMD==CMD_POWER_ON)
    {
        str_dashboard="power on\n";
    }
    else if(CMD==CMD_POWER_OFF)
    {
        str_dashboard="power off\n";
    }
    else if(CMD==CMD_SHUT_DOWN)
    {
        str_dashboard="shutdown\n";
    }
    else if(CMD==CMD_UNLOCK_STOP)
    {
        str_dashboard="unlock protective stop\n";
    }
    else
    {
        return str_dashboard;
    }
    return str_dashboard;

}
string UR10::ActionStopJ()
{
    string str="stopj(1.2)\n";
    return  str;
}
string UR10::ActionToHome()//暂时未定
{
    string str="";
    return str;
}



unsigned int UR10::getbitu(const unsigned char *buff, int pos, int len)
{
    unsigned int bits=0;
    int i;
    for (i=pos;i<pos+len;i++)
    {
        bits=(bits<<1)+((buff[i/8]>>(7-i%8))&1u);   //从高位到低位逐位计算
    }
    return bits;
}

string  UR10::doubleToString(double num)
{
    char str[256];
    sprintf(str, "%lf", num);
    string result = str;
    return result;
}


string UR10::doubleToString_4(const double &dbNum)
{
    char *chCode;
    chCode = new(std::nothrow)char[20];
    sprintf(chCode, "%.4lf", dbNum);  // .2 是控制输出精度的，两位小数
    string strCode(chCode);
    delete []chCode;
    return strCode;
}

double UR10::HexToDouble(const unsigned char* buf)
{
    double value = 0;
    unsigned int i = 0;
    unsigned int num,temp;
    int num2;
    bool flags1 = true;
    num = getbitu(buf,i,1); //标志位
    i += 1;
//double型规定偏移量为1023，其表示范围为-1024-1023
    num2 = getbitu(buf,i,11) - 1023;
    i += 11;
    while(1)
    {
        if(flags1)
        {
            flags1 = false;
            value += 1 * pow(2,num2); num2--;
        }
        temp = getbitu(buf,i,1);    i += 1;
        value += temp * pow(2,num2); num2--;
        if(i == 64)
            break;
    }
    if(num == 1)

        value *= -1;
    return value;

}
string UR10::movel(double x,double y,double z,double Rx,double Ry,double Rz,float a,float v,float t,float r)
{

    // QString str="movel(p[x,y,z,Rx,Ry,Rz],a=a,v=v,t=t,r=r)";
    string str="movel(p[";
    string str_x = doubleToString(x);
    str=str+str_x+",";
    string str_y = doubleToString(y);
    str=str+str_y+",";
    string str_z = doubleToString(z);
    str=str+str_z+",";
    string str_Rx = doubleToString(Rx);
    str=str+str_Rx+",";
    string str_Ry = doubleToString(Ry);
    str=str+str_Ry+",";
    string str_Rz = doubleToString(Rz);
    str=str+str_Rz+"],a=";
    string str_a= doubleToString(a);
    str=str+str_a+",v=";
    string str_v= doubleToString(v);
    str=str+str_v+",t=";
    string str_t= doubleToString(t);
    str=str+str_t+",r=";
    string str_r= doubleToString(r);
    str=str+str_r+")";
    str+="\n";
    return str;
}

string UR10::movel_pose_add(double x,double y,double z,double Rx,double Ry,double Rz)
{

    string str="movel(pose_add(p[";
    string str1=movel(actual_X,actual_Y,actual_Z,actual_Rx,actual_Ry,actual_Rz,move_a,move_v,0,0);
    str1.erase(0,6);
    string str_p_x = doubleToString(x);
    str=str+str_p_x+",";
    string str_p_y = doubleToString(y);
    str=str+str_p_y+",";
    string str_p_z = doubleToString(z);
    str=str+str_p_z+",";
    string str_p_Rx = doubleToString(Rx);
    str=str+str_p_Rx+",";
    string str_p_Ry = doubleToString(Ry);
    str=str+str_p_Ry+",";
    string str_p_Rz = doubleToString(Rz);
    str=str+str_p_Rz+"],";
    str+=str1;
    str.erase(str.length()-46,1);//去掉“，”
    str.insert(str.length()-45,"),");
    return str;
}

string UR10::movej(double j0,double j1,double j2,double j3,double j4,double j5,float a,float v,float t,float r)
{
    string str="movej([";
    string str_j0 = doubleToString(j0);
    str=str+str_j0+",";
    string str_j1 = doubleToString(j1);
    str=str+str_j1+",";
    string str_j2 = doubleToString(j2);
    str=str+str_j2+",";
    string str_j3 = doubleToString(j3);
    str=str+str_j3+",";
    string str_j4 = doubleToString(j4);
    str=str+str_j4+",";
    string str_j5 = doubleToString(j5);
    str=str+str_j5+"],a=";
    string str_a= doubleToString(a);
    str=str+str_a+",v=";
    string str_v= doubleToString(v);
    str=str+str_v+",t=";
    string str_t= doubleToString(t);
    str=str+str_t+",r=";
    string str_r= doubleToString(r);
    str=str+str_r+")";
    str+="\n";
    return str;
}
void UR10::SetMoveSpeed(float v)
{
    move_v=v;
}

void UR10::SetMoveAcc(float a)
{
    move_a=a;
}
