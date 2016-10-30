package com.abc.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * a map contains some StringRedisTemplate Objects
 */
public class StringRedisTemplateHolder {

    private static final StringRedisTemplateHolder instance = new StringRedisTemplateHolder();

    private ConcurrentHashMap<String, StringRedisTemplate> simpleMap = null;

    private StringRedisTemplateHolder() {
        simpleMap = new ConcurrentHashMap<>();
    }

    public static StringRedisTemplateHolder getInstance() {
        return instance;
    }

    public StringRedisTemplate getStringRedisTemplate(String node, StringRedisTemplate defaultTemplate) {
        StringRedisTemplate stringRedisTemplate = StringUtils.isBlank(node) ? defaultTemplate : simpleMap.get(node);
        return stringRedisTemplate == null ? defaultTemplate : stringRedisTemplate;
    }

    public synchronized void register(String node) {
        if (simpleMap.contains(node)) {
            return;
        } else {
            JedisConnectionFactoryHolder.getInstance().register(node);
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(JedisConnectionFactoryHolder.getInstance().getJedisConnectionFactory(node, null));
            simpleMap.put(node, stringRedisTemplate);
        }
    }

}

