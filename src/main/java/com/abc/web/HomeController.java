package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.HostInfo;
import com.abc.dto.ResponseData;
import com.abc.utils.JedisClusterHolder;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * for index.html
 */
@RestController
@RequestMapping("/home")
@Validated
public class HomeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    protected HostInfo hostInfo;

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public ResponseData connect(@NotBlank String node) {
        try {
            JedisClusterHolder.getInstance().register(node);
            hostInfo.setNode(node);
        } catch (Exception e) {
            logger.error("fail to connect redis cluster, hostInfo:" + node, e);
            return new ResponseData(MyConstants.CODE_ERR, "connect fail!");
        }
        return new ResponseData(MyConstants.CODE_SUCC, "connect success!");
    }

    @RequestMapping(value = "/connectedClusters", method = RequestMethod.GET)
    public ResponseData connectedClusters() {
        List<Map<String, String>> clusterBaseInfoList = new ArrayList<>();
        Map<String, JedisCluster> simpleMap = JedisClusterHolder.getInstance().getSimpleMap();
        for (String key : simpleMap.keySet()) {
            Map<String, String> clusterBaseInfo = new HashMap<>();
            clusterBaseInfo.put("host", key.split(":")[0]);
            clusterBaseInfo.put("port", key.split(":")[1]);
            if (key.equals(hostInfo.getNode())) {
                clusterBaseInfo.put("current", "true");
            } else {
                clusterBaseInfo.put("current", "false");
            }
            clusterBaseInfoList.add(clusterBaseInfo);
        }
        ResponseData responseData = new ResponseData(MyConstants.CODE_SUCC, "yeah", clusterBaseInfoList);
        return responseData;
    }

}
