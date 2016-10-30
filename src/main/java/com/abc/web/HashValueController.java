package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.HostInfo;
import com.abc.dto.ResponseData;
import com.abc.utils.StringRedisTemplateHolder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/hash")
@Validated
public class HashValueController {

    @Autowired
    private HostInfo hostInfo;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, String> setHash(@NotBlank String key, String hashKey) {
        HashOperations<String, String, String> hashOperations = StringRedisTemplateHolder.getInstance().getStringRedisTemplate(hostInfo.getNode(), stringRedisTemplate).opsForHash();
        if (StringUtils.isNotBlank(hashKey)) {
            Map<String, String> result = new HashMap<>();
            if (hashOperations.hasKey(key, hashKey)) {
                result.put(hashKey, hashOperations.get(key, hashKey));
            }
            return result;
        } else {
            return hashOperations.entries(key);
        }
    }

    @RequestMapping(value = "/getTtl", method = RequestMethod.GET)
    public Long getTtl(@RequestParam String keyName) {
        return StringRedisTemplateHolder.getInstance().getStringRedisTemplate(hostInfo.getNode(), stringRedisTemplate).getExpire(keyName, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setHash(@NotBlank String key, @NotBlank String hashKey, @NotBlank String value) {
        StringRedisTemplateHolder.getInstance().getStringRedisTemplate(hostInfo.getNode(), stringRedisTemplate).opsForHash().put(key, hashKey, value);
        return new ResponseData(MyConstants.CODE_SUCC, "update success!");
    }

}
