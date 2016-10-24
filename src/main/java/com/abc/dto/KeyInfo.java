package com.abc.dto;

import java.io.Serializable;

public class KeyInfo implements Serializable {

    private String key = null;

    private String dataType = null;

    private Long ttl = null;

    public KeyInfo() {
    }

    public KeyInfo(String key, String dataType, Long ttl) {
        this.key = key;
        this.dataType = dataType;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
