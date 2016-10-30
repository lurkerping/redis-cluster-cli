package com.abc.web;

import com.abc.dto.HostInfo;
import com.abc.dto.KeyInfo;
import com.abc.utils.StringRedisTemplateHolder;
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

    @Autowired
    private HostInfo hostInfo;

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
            keyInfo.setDataType(StringRedisTemplateHolder.getInstance().getStringRedisTemplate(hostInfo.getNode(), stringRedisTemplate).type(key).code());
            keyInfo.setTtl(StringRedisTemplateHolder.getInstance().getStringRedisTemplate(hostInfo.getNode(), stringRedisTemplate).getExpire(key));
            keyInfoList.add(keyInfo);
        }
        return keyInfoList;
    }

    /**
     * get a key list by keyPattern
     */
    private List<String> getKeyList(String keyPattern) {
        if (!keyPattern.endsWith("*")) {
            keyPattern += "*";
        }

        Set<String> allKeys = StringRedisTemplateHolder.getInstance().getStringRedisTemplate(hostInfo.getNode(), stringRedisTemplate).keys(keyPattern);

        List<String> keysList = new ArrayList<>();
        Iterator<String> keyIterator = allKeys.iterator();
        while (keyIterator.hasNext()) {
            keysList.add(keyIterator.next());
        }
        Collections.sort(keysList);

        if (keysList.size() > SUGGEST_SIZE) {
            return keysList.subList(0, 10);
        } else {
            return keysList;
        }
    }

}
