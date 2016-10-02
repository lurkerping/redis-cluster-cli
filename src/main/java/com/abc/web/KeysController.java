package com.abc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by xiaopengyu on 2016/10/2.
 */
@RestController
public class KeysController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/keys")
    public Set<String> keys(){
        return stringRedisTemplate.keys("*");
    }

}
