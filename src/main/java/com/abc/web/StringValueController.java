package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.HostInfo;
import com.abc.dto.ResponseData;
import com.abc.utils.JedisClusterHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/string")
public class StringValueController {

    @Autowired
    private HostInfo hostInfo;

    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getString(@RequestParam String keyName) {
        return JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).get(keyName);
    }

    @RequestMapping(value = "/getTtl", method = RequestMethod.GET)
    public Long getTtl(@RequestParam String keyName) {
        return JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).ttl(keyName);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setString(String keyName, String keyValue, Long ttl) {
        JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).set(keyName, keyValue);
        if (ttl != -1 && ttl > 0) {
            JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).pexpire(keyName, ttl);
        }
        return new ResponseData(MyConstants.CODE_SUCC, "update success!");
    }

}
