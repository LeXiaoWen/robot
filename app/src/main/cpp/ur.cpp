#include "ur.h"


void UR::GetDataPort29999(string str,string name)
{
  if(name=="Marm")
  {
     UR_M.GetDataPort29999(str);
  }
  else if(name=="Farm")
  {
     UR_F.GetDataPort29999(str);
  }
  else
      return;
}
void UR::GetDataPort30003(string name)
{
    if(name=="Marm")
    {
       UR_M.GetDataPort30003();
    }
    else if(name=="Farm")
    {
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
        return NULL;

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
        return NULL;

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
        return NULL;

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
        return NULL;

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
        return NULL;
}
string UR::ActionToHome(string name)
{

    if(name=="Marm")
    {
          return NULL;
    }
    else if(name=="Farm")
    {
          return NULL;
    }
    else
        return NULL;

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
