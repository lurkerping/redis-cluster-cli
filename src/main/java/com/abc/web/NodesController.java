package com.abc.web;

import com.abc.dto.MyRedisClusterNode;
import com.abc.utils.JedisClusterHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * show redis cluster nodes
 */
@RestController
public class NodesController extends BaseController {

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public List<MyRedisClusterNode> nodes() {
        List<MyRedisClusterNode> nodes = JedisClusterHolder.getInstance().getNodes(hostInfo.getNode());
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        return nodes;
    }

//    /**
//     * sort by
//     * |master1
//     * |--slave1a
//     * |--slave1b
//     * |master2
//     * |--slave2
//     */
//    private List<RedisClusterNode> sort(Iterable<RedisClusterNode> nodeIterable) {
//        final Iterator<RedisClusterNode> nodeIterator = nodeIterable.iterator();
//        final List<RedisClusterNode> nodeList = new ArrayList<>();
//        while (nodeIterator.hasNext()) {
//            nodeList.add(nodeIterator.next());
//        }
//        Collections.sort(nodeList, new Comparator<RedisClusterNode>() {
//            @Override
//            public int compare(RedisClusterNode o1, RedisClusterNode o2) {
//                String key1 = StringUtils.trimToEmpty(o1.getMasterId()) + o1.getId();
//                String key2 = StringUtils.trimToEmpty(o2.getMasterId()) + o2.getId();
//                return key1.compareTo(key2);
//            }
//        });
//        return nodeList;
//    }

}
