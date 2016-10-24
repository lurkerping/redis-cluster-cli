package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hash")
@Validated
public class HashValueController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, String> setHash(@NotBlank String key, String hashKey) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        if (StringUtils.isNotBlank(hashKey)) {
            Map<String, String> result = new HashMap<>();
            if(hashOperations.hasKey(key, hashKey)){
                result.put(hashKey, hashOperations.get(key, hashKey));
            }
            return result;
        }else{
            return hashOperations.entries(key);
        }
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ResponseData setHash(@NotBlank String key, @NotBlank String hashKey, @NotBlank String value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
        return new ResponseData(MyConstants.CODE_SUCC, "更新成功");
    }

}
