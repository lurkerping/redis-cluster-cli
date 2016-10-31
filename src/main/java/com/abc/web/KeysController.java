package com.abc.web;

import com.abc.dto.KeyInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class KeysController extends BaseController {

    public static final int SUGGEST_SIZE = 10;

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
            keyInfo.setDataType(getJedisCluster().type(key));
            keyInfo.setTtl(getJedisCluster().ttl(key));
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

        List<String> keysList = new ArrayList<>();

        Map<String, JedisPool> nodes = getJedisCluster().getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : nodes.entrySet()) {
            ScanResult<String> scanResult = entry.getValue().getResource().scan("0", scanParams);
            keysList.addAll(scanResult.getResult());
        }
        Collections.sort(keysList);

        if (keysList.size() > SUGGEST_SIZE) {
            return keysList.subList(0, 10);
        } else {
            return keysList;
        }
    }

}
