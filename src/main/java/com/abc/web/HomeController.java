package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
import com.abc.utils.JedisClusterHolder;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * for index.html
 */
@RestController
@RequestMapping("/home")
@Validated
public class HomeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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

}
