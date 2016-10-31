package com.abc.web;

import com.abc.dto.HostInfo;
import com.abc.utils.JedisClusterHolder;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public abstract class BaseController {

    @Autowired
    protected HostInfo hostInfo;

    @Autowired
    protected JedisCluster jedisCluster;

    protected JedisCluster getJedisCluster(){
        return JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster);
    }

}
