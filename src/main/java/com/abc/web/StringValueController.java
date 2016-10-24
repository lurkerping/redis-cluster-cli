package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/string")
public class StringValueController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getString(@RequestParam String keyName) {
        return stringRedisTemplate.opsForValue().get(keyName);
    }

    @RequestMapping(value = "/getTtl", method = RequestMethod.GET)
    public Long getTtl(@RequestParam String keyName) {
        return stringRedisTemplate.getExpire(keyName, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setString(String keyName, String keyValue, Long ttl) {
        if (ttl != -1 && ttl > 0) {
            stringRedisTemplate.opsForValue().set(keyName, keyValue, ttl, TimeUnit.MILLISECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(keyName, keyValue);
        }
        return new ResponseData(MyConstants.CODE_SUCC, "更新成功");
    }

}
