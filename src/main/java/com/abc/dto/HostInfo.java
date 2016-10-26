package com.abc.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class HostInfo implements Serializable {

    private String node = null;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
