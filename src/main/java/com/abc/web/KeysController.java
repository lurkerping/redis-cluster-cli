package com.abc.web;

import com.abc.dto.HostInfo;
import com.abc.dto.KeyInfo;
import com.abc.utils.JedisClusterHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class KeysController {

    public static final int SUGGEST_SIZE = 10;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private HostInfo hostInfo;

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> keyList(@RequestParam(required = false, defaultValue = "*", name = "term") String keyPattern) {
        return this.getKeyList(keyPattern);
    }

    @RequestMapping(value = "/keyInfoList", method = RequestMethod.GET)
    public List<KeyInfo> keyInfoList(@RequestParam(required = false, defaultValue = "*", name = "term") String keyPattern) {
        List<String> keyList = this.getKeyList(keyPattern);
        List<KeyInfo> keyInfoList = new ArrayList<>();
        for (String key : keyList) {
            KeyInfo keyInfo = new KeyInfo();
            keyInfo.setKey(key);
            keyInfo.setDataType(JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).type(key));
            keyInfo.setTtl(JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).ttl(key));
            keyInfoList.add(keyInfo);
        }
        return keyInfoList;
    }

    /**
     * get a key list by keyPattern
     */
    private List<String> getKeyList(String keyPattern) {
        if (!keyPattern.endsWith("*")) {
            keyPattern += "*";
        }

        ScanParams scanParams = new ScanParams();
        scanParams.count(SUGGEST_SIZE);
        scanParams.match(keyPattern);
        ScanResult<String> scanResult = JedisClusterHolder.getInstance().getCluster(hostInfo.getNode(), jedisCluster).scan("0", scanParams);

        List<String> keysList = scanResult.getResult();
        Collections.sort(keysList);

        if (keysList.size() > SUGGEST_SIZE) {
            return keysList.subList(0, 10);
        } else {
            return keysList;
        }
    }

}
