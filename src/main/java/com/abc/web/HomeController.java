package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.HostInfo;
import com.abc.dto.ResponseData;
import com.abc.utils.StringRedisTemplateHolder;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HostInfo hostInfo;

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public ResponseData connect(@NotBlank String node) {
        try {
            StringRedisTemplateHolder.getInstance().register(node);
            hostInfo.setNode(node);
        } catch (Exception e) {
            logger.error("连接redis cluster失败,hostInfo:" + node, e);
            return new ResponseData(MyConstants.CODE_ERR, "连接失败");
        }
        return new ResponseData(MyConstants.CODE_SUCC, "连接成功");
    }

}
