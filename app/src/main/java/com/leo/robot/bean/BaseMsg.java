package com.leo.robot.bean;

/**
 * created by Leo on 2019/4/21 11 : 12
 */


public abstract class BaseMsg {
    private int code;
    private String msg;
    private boolean isHighlight;

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
