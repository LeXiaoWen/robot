package com.leo.robot.bean.arm.main_arm.angle;

/**
 * 主臂 模式信息
 * created by Leo on 2019/5/22 21 : 46
 */


public class MAModBean {

    /**
     * msgId : MA_Mod
     * params : {"RobotMod":"NORMAL","safetyMod":"FAULT"}
     */

    private String msgId;
    private ParamsBean params;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * RobotMod : NORMAL
         * safetyMod : FAULT
         */

        private String RobotMod;
        private String safetyMod;

        public String getRobotMod() {
            return RobotMod;
        }

        public void setRobotMod(String RobotMod) {
            this.RobotMod = RobotMod;
        }

        public String getSafetyMod() {
            return safetyMod;
        }

        public void setSafetyMod(String safetyMod) {
            this.safetyMod = safetyMod;
        }
    }
}
