package com.abc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by xiaopengyu on 2016/10/2.
 */
@RestController
public class KeysController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public Set<String> keys(@RequestParam(required = false, defaultValue = "*", name = "pattern") String keyPattern) {
        return stringRedisTemplate.keys(keyPattern);
    }

}
