package com.leo.robot.bean;

/**
 * created by Leo on 2019/4/21 11 : 12
 */


public abstract class BaseMsg {
    private String code;
    private String msg;
    private boolean isHighlight;

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
