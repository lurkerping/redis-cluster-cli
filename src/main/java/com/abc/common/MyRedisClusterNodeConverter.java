package com.abc.common;

import com.abc.dto.MyRedisClusterNode;
import com.abc.dto.RedisClusterNode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

/**
 * convert a jedis'clusterInfo line to MyRedisClusterNode
 */
public class MyRedisClusterNodeConverter implements Converter<String, MyRedisClusterNode> {

    private static final MySlotRangeListConverter SLOT_RANGE_CONVERTER = new MySlotRangeListConverter();

    /**
     * 07ef2c6fad1307390a7cadfaffa95684898d7abe 192.168.1.108:6379 master - 0 1478182911866 1 connected 0-4095
     * a43ffed8087f4189b59f7021c2aa01b29189d5cc 192.168.1.110:6379 master - 0 1478182907845 5 connected 8192-12287
     * eb0252209d642564462990b5325a8f5b759cad3c 192.168.1.111:6380 slave a43ffed8087f4189b59f7021c2aa01b29189d5cc 0 1478182908851 8 connected
     * eb3865b7489a0c00fb2e605479474295a04cb30d 192.168.1.109:6380 slave 07ef2c6fad1307390a7cadfaffa95684898d7abe 0 1478182912873 4 connected
     * 20fca39f3bd1bcdea2ce8ab1e76632460d4fdb69 192.168.1.109:6379 master - 0 1478182910862 3 connected 4096-8191
     * d7933e45260e8f4ed8c3823313436a0824504c79 192.168.1.108:6380 myself,slave 20fca39f3bd1bcdea2ce8ab1e76632460d4fdb69 0 0 2 connected
     * 8aec084d010f983180bc9d954a903cbb2a3cce92 192.168.1.111:6379 slave 4ffc02dff7a470aebccc824f692351f3bfd57030 0 1478182906840 10 connected
     * 4ffc02dff7a470aebccc824f692351f3bfd57030 192.168.1.110:6380 master - 0 1478182908149 10 connected 12288-16383
     *
     * @param source
     * @return
     */
    @Override
    public MyRedisClusterNode convert(String source) {
        Assert.notNull(source);
        String[] fields = source.split(" ");
        RedisClusterNode node = new RedisClusterNode();
        MyRedisClusterNode myNode = new MyRedisClusterNode(node);
        node.setId(fields[0]);
        node.setHost(fields[1].split(":")[0]);
        node.setPort(Integer.parseInt(fields[1].split(":")[1]));
        node.setFlags(fields[2]);
        node.setType(fields[2].indexOf("master") > -1 ? "master" : "slave");
        node.setLinkState(fields[7]);
        node.setMasterId(fields[3]);
        if (fields.length > 8) {
            myNode.getMySlotRangeList().addAll(SLOT_RANGE_CONVERTER.convert(fields[8]));
        }
        return myNode;
    }

}

