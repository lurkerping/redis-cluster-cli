package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
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
@RequestMapping("/string")
public class StringValueController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getString(@RequestParam String keyName) {
        return stringRedisTemplate.opsForValue().get(keyName);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setString(String keyName, String keyValue) {
        stringRedisTemplate.opsForValue().set(keyName, keyValue);
        return new ResponseData(MyConstants.CODE_SUCC, "更新成功");
    }

}
