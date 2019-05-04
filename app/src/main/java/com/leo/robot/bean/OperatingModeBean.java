package com.leo.robot.bean;

/**
 * 当前机械臂模式查询
 * created by Leo on 2019/5/4 21 : 12
 */


public class OperatingModeBean {

    /**
     * version : 1.0
     * msgType : InfoOperatingMode
     * params : {"status":"0","msg":"正处于自动作业模式。"}
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
         * msg : 正处于自动作业模式。
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
