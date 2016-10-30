package com.abc.utils;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a map containing some Jedis Cluster Objects
 */
public class JedisClusterHolder {

    public static final JedisClusterHolder instance = new JedisClusterHolder();

    private ConcurrentHashMap<String, JedisCluster> simpleMap = null;

    private JedisClusterHolder() {
        simpleMap = new ConcurrentHashMap<>();
    }

    public static final JedisClusterHolder getInstance() {
        return instance;
    }

    public synchronized JedisCluster getCluster(String node, JedisCluster defaultJedisCluster) {
        if (StringUtils.isBlank(node)) {
            return defaultJedisCluster;
        }
        return simpleMap.contains(node) ? simpleMap.get(node) : defaultJedisCluster;
    }

    public synchronized void register(String node) {
        if (simpleMap.contains(node)) {
            return;
        } else {
            Set<HostAndPort> jedisClusterNodes = new HashSet<>();
            jedisClusterNodes.add(HostAndPort.parseString(node));
            try {
                JedisCluster jc = new JedisCluster(jedisClusterNodes);
                jc.get("foo52");
                simpleMap.put(node, jc);
            } catch (Exception e) {
                throw new RuntimeException(" fail to connect redis cluster, node:" + node, e);
            }

        }
    }

}
