package com.abc.web;

import com.abc.common.MyExecutors;
import com.abc.dto.KeyInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class KeysController extends BaseController {

    public static final int SUGGEST_SIZE = 10;

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> keyList(@RequestParam(required = false, defaultValue = "*", name = "term") String keyPattern) {
        List<String> keysList = this.getKeyList(keyPattern);
        if (keysList.size() > SUGGEST_SIZE) {
            return keysList.subList(0, SUGGEST_SIZE);
        } else {
            return keysList;
        }
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
        final ScanParams scanParams = new ScanParams();
        scanParams.count(SUGGEST_SIZE);
        scanParams.match(keyPattern);

        final List<String> keysList = new ArrayList<>();
        final List<Future<List<String>>> futureList = new ArrayList<>();

        Map<String, JedisPool> nodes = getJedisCluster().getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : nodes.entrySet()) {
            try (Jedis jedis = entry.getValue().getResource()) {
                if (isMaster(jedis.clusterNodes(), jedis.getClient().getHost() + ":" + jedis.getClient().getPort())) {
                    futureList.add(MyExecutors.FIXED.submit(new Callable<List<String>>() {
                        @Override
                        public List<String> call() throws Exception {
                            //scan MATCH过滤器是在scan cursor之后才调用的，也就是说
                            //scan 0 MATCH abc*可能没有返回元素，但是scan 1 MATCH abc*会返回元素
                            List<String> resultList = new ArrayList<>();
                            ScanResult<String> scanResult = jedis.scan("0", scanParams);
                            resultList.addAll(scanResult.getResult());
                            while (resultList.size() < SUGGEST_SIZE && !"0".equals(scanResult.getStringCursor())) {
                                scanResult = jedis.scan(scanResult.getStringCursor(), scanParams);
                                resultList.addAll(scanResult.getResult());
                            }
                            return resultList;
                        }
                    }));
                }
            }
        }
        for (Future<List<String>> future : futureList) {
            try {
                keysList.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(keysList);
        return keysList;
    }

    private boolean isMaster(String clusterNodes, String hostAndPort) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(clusterNodes.getBytes(StandardCharsets.UTF_8))))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] clusterNode = line.split(" ");
                if (hostAndPort.equals(clusterNode[1]) && clusterNode[2].indexOf("master") > 0) {
                    return true;
                }
            }
        } catch (IOException e) {
            //not gonna happen
        }
        return false;
    }

}
