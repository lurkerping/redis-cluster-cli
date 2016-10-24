package com.abc.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class ResponseData implements Serializable {

    private String retCode;
    private String retMsg;

    private Map<String, String> data;

    public ResponseData() {
    }

    public ResponseData(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public ResponseData(String retCode, String retMsg, Map<String, String> data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data == null ? null : Collections.unmodifiableMap(data);
    }

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public Map<String, String> getData() {
        return data == null ? null : Collections.unmodifiableMap(data);
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
