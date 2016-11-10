package com.abc.dto;


import java.util.ArrayList;
import java.util.List;

public class MyRedisClusterNode {

    private String id;
    private String type;
    private String name;
    private String host;
    private int port;
    private String masterId;
    private String flags;
    private String linkState;
    private List<MySlotRange> mySlotRangeList = new ArrayList<>();

    public MyRedisClusterNode() {
    }

    public String getLinkState() {
        return linkState;
    }

    public void setLinkState(String linkState) {
        this.linkState = linkState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public void addMySlotRange(MySlotRange mySlotRange) {
        mySlotRangeList.add(mySlotRange);
    }

    public List<MySlotRange> getMySlotRangeList() {
        return mySlotRangeList;
    }

}
