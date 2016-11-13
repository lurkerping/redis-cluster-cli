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
    public List<MyRedisClusterNode> nodes(String node) {
        List<MyRedisClusterNode> nodes = JedisClusterHolder.getInstance().getNodes(node);
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        return nodes;
    }

}
