package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hash")
@Validated
public class HashValueController extends BaseController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, String> setHash(@NotBlank String key, String hashKey) {
        if (StringUtils.isNotBlank(hashKey)) {
            Map<String, String> result = new HashMap<>();
            if (getJedisCluster().hexists(key, hashKey)) {
                result.put(hashKey, getJedisCluster().hget(key, hashKey));
            }
            return result;
        } else {
            return getJedisCluster().hgetAll(key);
        }
    }

    @RequestMapping(value = "/getTtl", method = RequestMethod.GET)
    public Long getTtl(@RequestParam String keyName) {
        return getJedisCluster().ttl(keyName);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setHash(@NotBlank String key, @NotBlank String hashKey, @NotBlank String value) {
        getJedisCluster().hset(key, hashKey, value);
        return new ResponseData(MyConstants.CODE_SUCC, "update success!");
    }

}
