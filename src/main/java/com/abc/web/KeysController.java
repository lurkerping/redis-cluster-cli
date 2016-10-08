package com.abc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by xiaopengyu on 2016/10/2.
 */
@RestController
public class KeysController {

    public static final int SUGGEST_SIZE = 10;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> keys(@RequestParam(required = false, defaultValue = "*", name = "term") String keyPattern) {
        //后置模糊查询
        if (!keyPattern.endsWith("*")) {
            keyPattern += "*";
        }
        Set<String> allKeys = stringRedisTemplate.keys(keyPattern);

        //先排序
        List<String> keysList = new ArrayList<>();
        Iterator<String> keyIterator = allKeys.iterator();
        while (keyIterator.hasNext()) {
            keysList.add(keyIterator.next());
        }
        Collections.sort(keysList);

        //只返回SUGGEST_SIZE定义的数量
        if (keysList.size() > SUGGEST_SIZE) {
            return keysList.subList(0, 10);
        } else {
            return keysList;
        }
    }

    @RequestMapping(value = "key", method = RequestMethod.GET)
    public String getKey(@RequestParam String keyName) {
        return stringRedisTemplate.opsForValue().get(keyName);
    }

}
