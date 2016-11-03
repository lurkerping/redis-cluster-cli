package com.abc.utils;

import com.abc.common.MyRedisClusterNodeConverter;
import com.abc.dto.MyRedisClusterNode;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a map containing some Jedis Cluster Objects
 */
public final class JedisClusterHolder {

    public static final JedisClusterHolder instance = new JedisClusterHolder();

    private static final MyRedisClusterNodeConverter REDIS_CLUSTER_NODE_CONVERTER = new MyRedisClusterNodeConverter();

    private ConcurrentHashMap<String, JedisCluster> simpleMap = null;

    private ConcurrentHashMap<String, List<MyRedisClusterNode>> nodesMap = null;

    private JedisClusterHolder() {
        simpleMap = new ConcurrentHashMap<>();
        nodesMap = new ConcurrentHashMap<>();
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

    public List<MyRedisClusterNode> getNodes(String node) {
        return nodesMap.get(node);
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
                List<MyRedisClusterNode> nodes = new ArrayList<>();
                for (Map.Entry<String, JedisPool> entry : jc.getClusterNodes().entrySet()) {
                    String clusterNodes = entry.getValue().getResource().clusterNodes();
                    for (String clusterNode : clusterNodes.split("\n")) {
                        nodes.add(REDIS_CLUSTER_NODE_CONVERTER.convert(clusterNode));
                    }
                    nodesMap.put(node, nodes);
                    break;
                }

            } catch (Exception e) {
                throw new RuntimeException(" fail to connect redis cluster, node:" + node, e);
            }

        }
    }

}
