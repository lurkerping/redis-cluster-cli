package com.abc.web;

import com.abc.dto.MyRedisClusterNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * unit test for NodesController
 */
public class NodesControllerTest {

    @Test
    public void testParseSlotRange() {
        //simple
//        NodesController nodesController = new NodesController();
//        RedisClusterNode node = new RedisClusterNode("127.0.0.1", 7379, new RedisClusterNode.SlotRange(1, 200));
//        MyRedisClusterNode myNode = nodesController.parseSlotRange(node);
//        Assert.assertEquals(1, myNode.getMySlotRangeList().size());
//        Assert.assertEquals(new Integer(1), myNode.getMySlotRangeList().get(0).getStart());
//        Assert.assertEquals(new Integer(200), myNode.getMySlotRangeList().get(0).getEnd());
//
//        //logs of slot range
//        List<Integer> slotList = Arrays.asList(1, 2, 3, 8, 9, 10, 11, 12, 13, 20);
//        node = new RedisClusterNode("127.0.0.1", 7379, new RedisClusterNode.SlotRange(slotList));
//        myNode = nodesController.parseSlotRange(node);
//        Assert.assertEquals(3, myNode.getMySlotRangeList().size());
//        Assert.assertEquals(new Integer(1), myNode.getMySlotRangeList().get(0).getStart());
//        Assert.assertEquals(new Integer(3), myNode.getMySlotRangeList().get(0).getEnd());
//        Assert.assertEquals(new Integer(8), myNode.getMySlotRangeList().get(1).getStart());
//        Assert.assertEquals(new Integer(13), myNode.getMySlotRangeList().get(1).getEnd());
//        Assert.assertEquals(new Integer(20), myNode.getMySlotRangeList().get(2).getStart());
//        Assert.assertEquals(new Integer(20), myNode.getMySlotRangeList().get(2).getEnd());
    }

}
