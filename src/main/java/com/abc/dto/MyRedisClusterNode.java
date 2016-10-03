package com.abc.dto;

import org.springframework.data.redis.connection.RedisClusterNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaopengyu on 2016/10/3.
 */
public class MyRedisClusterNode {

    private RedisClusterNode node = null;

    protected MyRedisClusterNode() {
    }

    public MyRedisClusterNode(RedisClusterNode node) {
        this.node = node;
    }

    private List<MySlotRange> mySlotRangeList = new ArrayList<>();

    public void addMySlotRange(MySlotRange mySlotRange) {
        mySlotRangeList.add(mySlotRange);
    }

    public RedisClusterNode getNode() {
        return node;
    }

    public List<MySlotRange> getMySlotRangeList() {
        return mySlotRangeList;
    }

}
