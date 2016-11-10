package com.abc.dto;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {

    private String retCode;
    private String retMsg;

    private T data;

    public ResponseData() {
    }

    public ResponseData(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public ResponseData(String retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", data=" + data +
                '}';
    }

}
