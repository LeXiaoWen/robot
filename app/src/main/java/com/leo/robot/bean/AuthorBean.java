package com.leo.robot.bean;

/**
 * created by Leo on 2019/4/27 21 : 34
 */


public class AuthorBean {


    /**
     * Version : 1.0
     * msgType : AuthorInfo
     * params : {"msg":"hello"}
     */

    private String Version;
    private String msgType;
    private ParamsBean params;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
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
         * msg : hello
         */

        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
