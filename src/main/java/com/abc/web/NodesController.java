package com.abc.web;

import org.springframework.web.bind.annotation.RestController;

/**
 * show redis cluster nodes
 */
@RestController
public class NodesController extends BaseController {

//    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
//    public List<MyRedisClusterNode> nodes() {
//        Iterable<RedisClusterNode> nodeIterable = JedisConnectionFactoryHolder.getInstance().getJedisConnectionFactory(hostInfo.getNode(),
//                jedisConnectionFactory).getClusterConnection().clusterGetNodes();
//        List<RedisClusterNode> nodeList = this.sort(nodeIterable);
//        return this.toMyRedisClusterNode(nodeList);
//    }
//
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
//
//    /**
//     * transform the origin RedisClusterNode to MyRedisClusterNode
//     */
//    private List<MyRedisClusterNode> toMyRedisClusterNode(List<RedisClusterNode> nodeList) {
//        List<MyRedisClusterNode> myNodeList = new ArrayList<>();
//        for (RedisClusterNode node : nodeList) {
//            myNodeList.add(this.parseSlotRange(node));
//        }
//        return myNodeList;
//    }
//
//    MyRedisClusterNode parseSlotRange(RedisClusterNode node) {
//        MyRedisClusterNode myNode = new MyRedisClusterNode(node);
//        int[] slotsArray = node.getSlotRange().getSlotsArray();
//        int len = slotsArray.length;
//        if (len == 0) {
//            return myNode;
//        }
//        if (len == 1) {
//            myNode.addMySlotRange(new MySlotRange(slotsArray[0], slotsArray[0]));
//        }
//        int index = 0;
//        int pre = slotsArray[index];
//        for (int i = 1; i < len; i++) {
//            int cur = slotsArray[i];
//            // another slot
//            if (pre + 1 != cur) {
//                myNode.addMySlotRange(new MySlotRange(slotsArray[index], pre));
//                index = i;
//            }
//            pre = cur;
//            // the end
//            if (i == len - 1) {
//                myNode.addMySlotRange(new MySlotRange(slotsArray[index], pre));
//            }
//        }
//        return myNode;
//    }

}
