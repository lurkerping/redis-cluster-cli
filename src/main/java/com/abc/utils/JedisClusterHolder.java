package com.abc.utils;

import com.abc.common.MyRedisClusterNodeConverter;
import com.abc.dto.MyRedisClusterNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * a map containing some Jedis Cluster Objects
 */
public final class JedisClusterHolder {

    public static final JedisClusterHolder instance = new JedisClusterHolder();

    private static final MyRedisClusterNodeConverter REDIS_CLUSTER_NODE_CONVERTER = new MyRedisClusterNodeConverter();

    private Map<String, JedisCluster> clusterMap = null;

    private Map<String, List<MyRedisClusterNode>> clusterNodesMap = null;

    private JedisClusterHolder() {
        clusterMap = new ConcurrentSkipListMap<>();
        clusterNodesMap = new ConcurrentSkipListMap<>();
    }

    public static final JedisClusterHolder getInstance() {
        return instance;
    }

    public synchronized JedisCluster getCluster(String node, JedisCluster defaultJedisCluster) {
        if (StringUtils.isBlank(node)) {
            return defaultJedisCluster;
        }
        return clusterMap.containsKey(node) ? clusterMap.get(node) : defaultJedisCluster;
    }

    public Map<String, JedisCluster> getClusterMap() {
        return Collections.unmodifiableMap(clusterMap);
    }

    public Map<String, List<MyRedisClusterNode>> getClusterNodesMap() {
        return Collections.unmodifiableMap(clusterNodesMap);
    }

    public List<MyRedisClusterNode> getNodes(String node) {
        if (node != null) {
            return clusterNodesMap.get(node);
        } else {
            return null;
        }
    }

    public synchronized void register(String node, JedisCluster jc) {
        Assert.notNull(node);
        Assert.notNull(jc);
        if (clusterMap.containsKey(node)) {
            return;
        } else {
            List<MyRedisClusterNode> nodes = parseClusterNodes(jc);
            registerAll(jc, nodes);
        }
    }

    public synchronized void register(String node) {
        Assert.notNull(node);
        if (clusterMap.containsKey(node)) {
            return;
        } else {
            Set<HostAndPort> jedisClusterNodes = new HashSet<>();
            jedisClusterNodes.add(HostAndPort.parseString(node));
            try {
                JedisCluster jc = new JedisCluster(jedisClusterNodes);
                jc.get("foo52");
                List<MyRedisClusterNode> nodes = parseClusterNodes(jc);
                this.registerAll(jc, nodes);
            } catch (Exception e) {
                throw new RuntimeException(" fail to connect redis cluster, node:" + node, e);
            }

        }
    }

    private void registerAll(JedisCluster jc, List<MyRedisClusterNode> nodes) {
        for (MyRedisClusterNode clusterNode : nodes) {
            String curNode = clusterNode.getHost() + ":" + clusterNode.getPort();
            clusterMap.put(curNode, jc);
            clusterNodesMap.put(curNode, nodes);
        }
    }

    private List<MyRedisClusterNode> parseClusterNodes(JedisCluster jc) {
        List<MyRedisClusterNode> nodes = new ArrayList<>();
        for (Map.Entry<String, JedisPool> entry : jc.getClusterNodes().entrySet()) {
            String clusterNodes = entry.getValue().getResource().clusterNodes();
            for (String clusterNode : clusterNodes.split("\n")) {
                nodes.add(REDIS_CLUSTER_NODE_CONVERTER.convert(clusterNode));
            }
            return nodes;
        }
        return nodes;
    }

}
