package com.xinguan.core;

/** 接口调用返回
 * @author zhangzhan
 * @date 2019-03-26 09:10
 */
public class CustomResponse {

    private boolean result;
    private String message;
    private Object object;

    public CustomResponse(boolean result, String message, Object object) {
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
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
