package com.abc.web;

import com.abc.dto.KeyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class KeysController {

    public static final int SUGGEST_SIZE = 10;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> keyList(@RequestParam(required = false, defaultValue = "*", name = "term") String keyPattern) {
        return this.getKeyList(keyPattern);
    }

    @RequestMapping(value = "/keyInfoList", method = RequestMethod.GET)
    public List<KeyInfo> keyInfoList(@RequestParam(required = false, defaultValue = "*", name = "term") String keyPattern) {
        List<String> keyList = this.getKeyList(keyPattern);
        List<KeyInfo> keyInfoList = new ArrayList<>();
        for (String key : keyList) {
            KeyInfo keyInfo = new KeyInfo();
            keyInfo.setKey(key);
            keyInfo.setDataType(stringRedisTemplate.type(key).code());
            keyInfo.setTtl(stringRedisTemplate.getExpire(key));
            keyInfoList.add(keyInfo);
        }
        return keyInfoList;
    }

    /**
     * 根据keyPattern返回部分key
     *
     * @param keyPattern
     * @return
     */
    private List<String> getKeyList(String keyPattern) {
        //后置模糊查询
        if (!keyPattern.endsWith("*")) {
            keyPattern += "*";
        }

        Set<String> allKeys = stringRedisTemplate.keys(keyPattern);

        List<String> keysList = new ArrayList<>();
        Iterator<String> keyIterator = allKeys.iterator();
        while (keyIterator.hasNext()) {
            keysList.add(keyIterator.next());
        }
        //排序
        Collections.sort(keysList);

        //只返回SUGGEST_SIZE定义的数量
        if (keysList.size() > SUGGEST_SIZE) {
            return keysList.subList(0, 10);
        } else {
            return keysList;
        }
    }

}
