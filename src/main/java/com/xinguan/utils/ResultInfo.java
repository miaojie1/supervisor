package com.xinguan.utils;

import java.io.Serializable;

/**
 * @author zhangzhan
 * @date 2019-04-09 20:39
 */
public class ResultInfo implements Serializable {
    private boolean status;
    private String message;
    private Object object;

    public ResultInfo(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultInfo(boolean status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public ResultInfo() {
    }

    public ResultInfo(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
