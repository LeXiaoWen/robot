package com.leo.robot.bean;

/**
 * 主控服务器向pad端发送消息，是否能一键收回机械臂
 * created by Leo on 2019/5/4 21 : 11
 */


public class TakeBackBean {

    /**
     * version : 1.0
     * msgType : InfoArm
     * params : {"status":"0","msg":"无法一键收回，请手动调整主臂和从臂关节角。"}
     */

    private String version;
    private String msgType;
    private ParamsBean params;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * status : 0
         * msg : 无法一键收回，请手动调整主臂和从臂关节角。
         */

        private String status;
        private String msg;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
