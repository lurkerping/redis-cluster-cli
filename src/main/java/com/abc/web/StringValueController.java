package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/string")
public class StringValueController extends BaseController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getString(@RequestParam String keyName) {
        return getJedisCluster().get(keyName);
    }

    @RequestMapping(value = "/getTtl", method = RequestMethod.GET)
    public Long getTtl(@RequestParam String keyName) {
        return getJedisCluster().ttl(keyName);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setString(String keyName, String keyValue, Long ttl) {
        getJedisCluster().set(keyName, keyValue);
        if (ttl != -1 && ttl > 0) {
            getJedisCluster().pexpire(keyName, ttl);
        }
        return new ResponseData(MyConstants.CODE_SUCC, "update success!");
    }

}
