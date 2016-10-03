package com.abc.web;

import com.abc.dto.MyRedisClusterNode;
import com.abc.dto.MySlotRange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by xiaopengyu on 2016/10/2.
 */
@RestController
public class NodesController {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public List<MyRedisClusterNode> nodes() {
        Iterable<RedisClusterNode> nodeIterable = jedisConnectionFactory.getClusterConnection().clusterGetNodes();
        List<RedisClusterNode> nodeList = this.sort(nodeIterable);
        List<MyRedisClusterNode> myNodeList = this.toMyRedisClusterNode(nodeList);
        return myNodeList;
    }

    /**
     * 根据主-从，主-从的顺序排列
     *
     * @param nodeIterable
     * @return
     */
    private List<RedisClusterNode> sort(Iterable<RedisClusterNode> nodeIterable) {
        final Iterator<RedisClusterNode> nodeIterator = nodeIterable.iterator();
        final List<RedisClusterNode> nodeList = new ArrayList<>();
        while (nodeIterator.hasNext()) {
            nodeList.add(nodeIterator.next());
        }
        //根据masterId+Id的自然顺序排列即可
        Collections.sort(nodeList, new Comparator<RedisClusterNode>() {
            @Override
            public int compare(RedisClusterNode o1, RedisClusterNode o2) {
                String key1 = StringUtils.trimToEmpty(o1.getMasterId()) + o1.getId();
                String key2 = StringUtils.trimToEmpty(o2.getMasterId()) + o2.getId();
                return key1.compareTo(key2);
            }
        });
        return nodeList;
    }

    /**
     * 原生的RedisClusterNode之后成MyRedisClusterNode
     *
     * @param nodeList
     * @return
     */
    private List<MyRedisClusterNode> toMyRedisClusterNode(List<RedisClusterNode> nodeList) {
        List<MyRedisClusterNode> myNodeList = new ArrayList<>();
        for (RedisClusterNode node : nodeList) {
            MyRedisClusterNode myNode = new MyRedisClusterNode(node);
            int[] slotsArray = node.getSlotRange().getSlotsArray();
            int len = slotsArray.length;
            int start = 0;
            int end = 0;
            for (int i = 0; i < len; i++) {
                if (start == 0) {
                    start = slotsArray[i];
                    end = slotsArray[i];
                    continue;
                }
                int cur = slotsArray[i];
                //another slot range
                if (cur - end > 1) {
                    myNode.addMySlotRange(new MySlotRange(start, end));
                    start = 0;
                } else {
                    end = cur;
                    if (i == len - 1) {
                        myNode.addMySlotRange(new MySlotRange(start, end));
                    }
                }
            }
            myNodeList.add(myNode);
        }
        return myNodeList;
    }

}
