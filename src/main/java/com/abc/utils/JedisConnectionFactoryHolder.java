package com.abc.utils;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a map containing some JedisConnectionFactory Object
 */
public class JedisConnectionFactoryHolder {

    private static final JedisConnectionFactoryHolder instance = new JedisConnectionFactoryHolder();

    private ConcurrentHashMap<String, JedisConnectionFactory> simpleMap = null;

    private JedisConnectionFactoryHolder() {
        simpleMap = new ConcurrentHashMap<>();
    }

    public static JedisConnectionFactoryHolder getInstance() {
        return instance;
    }

    public synchronized JedisConnectionFactory getJedisConnectionFactory(String node, JedisConnectionFactory defaultFactory) {
        JedisConnectionFactory jedisConnectionFactory = simpleMap.get(node);
        return jedisConnectionFactory == null ? defaultFactory : jedisConnectionFactory;
    }

    public synchronized void register(String node) {
        if (simpleMap.contains(node)) {
            return;
        } else {
            List<String> nodes = new ArrayList<>();
            nodes.add(node);
            RedisClusterConfiguration config = new RedisClusterConfiguration(nodes);
            config.setMaxRedirects(3);
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config, new JedisPoolConfig());
            jedisConnectionFactory.afterPropertiesSet();
            if (jedisConnectionFactory.getClusterConnection() != null && "PONG".equalsIgnoreCase(jedisConnectionFactory.getClusterConnection().ping())) {
                simpleMap.put(node, jedisConnectionFactory);
            } else {
                throw new RuntimeException("连接redis cluster失败,node:" + node);
            }
        }
    }

}
