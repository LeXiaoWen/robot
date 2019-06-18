#include "ur.h"
#include <jni.h>
#include <string>

using namespace std;


void UR::GetDataPort29999(string str_29999, string name) {
    if (name == "Marm") {
        UR_M.GetDataPort29999(str_29999);
    } else if (name == "Farm") {
        UR_F.GetDataPort29999(str_29999);
    } else
        return;
}

void UR::GetDataPort30003(string str_30003, string name) {
    char str[2216] = "";

//    strcpy(str, str_30003.c_str());
    if (name == "Marm") {
        StrToHex(str, UR_M.Recv_buf, sizeof(UR_M.Recv_buf));
        UR_M.GetDataPort30003();
    } else if (name == "Farm") {
        StrToHex(str, UR_F.Recv_buf, sizeof(UR_F.Recv_buf));
        UR_F.GetDataPort30003();
    } else
        return;
}


string UR::ActionJoint(string cmd, string name) {
    if (name == "Marm") {
        return UR_M.ActionJoint(cmd);
    } else if (name == "Farm") {
        return UR_F.ActionJoint(cmd);
    } else
        return NULL;

}

string UR::ActionMove(string cmd, string name) {
    if (name == "Marm") {
        return UR_M.ActionMove(cmd);
    } else if (name == "Farm") {
        return UR_F.ActionMove(cmd);
    } else
        return NULL;

}

string UR::ActionPose(string cmd, string name) {
    if (name == "Marm") {
        return UR_M.ActionPose(cmd);
    } else if (name == "Farm") {
        return UR_F.ActionPose(cmd);
    } else
        return NULL;

}

string UR::ActionDash(string cmd, string name) {
    if (name == "Marm") {
        return UR_M.ActionDash(cmd);
    } else if (name == "Farm") {
        return UR_F.ActionDash(cmd);
    } else
        return NULL;

}

string UR::ActionStopJ(string name) {
    if (name == "Marm") {
        return UR_M.ActionStopJ();
    } else if (name == "Farm") {
        return UR_F.ActionStopJ();
    } else
        return NULL;
}

string UR::ActionToHome(string name) {

    if (name == "Marm") {
        return NULL;
    } else if (name == "Farm") {
        return NULL;
    } else
        return NULL;

}

void UR::DealArmFeedbackMsg(string name) {
    if (name == "Marm") {

    } else if (name == "Farm") {

    } else
        return;
}

void UR::SetMoveSpeed(float v, string name) {
    if (name == "Marm") {
        UR_M.SetMoveSpeed(v);
    } else if (name == "Farm") {
        UR_F.SetMoveSpeed(v);
    } else
        return;
}

void UR::SetMoveAcc(float a, string name) {
    if (name == "Marm") {
        UR_M.SetMoveAcc(a);
    } else if (name == "Farm") {
        UR_F.SetMoveAcc(a);
    } else
        return;

}

void UR::StrToHex(const char str[], unsigned char buf[], int len) {


    int i = 0;
    int n = 0;
    while (*str != 0 && (n = ((i++) >> 1)) < len) {
        buf[n] <<= 4;
        if (*str >= '0' && *str <= '9') {
            buf[n] |= *str - '0';
        } else if (*str >= 'a' && *str <= 'f') {
            buf[n] |= *str - 'a' + 10;
        } else if (*str >= 'A' && *str <= 'F') {
            buf[n] |= *str - 'A' + 10;
        }
        str++;
    }
    len = n;


}

