package org.yzh.web.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.config.RedisKeyConfig;
import org.yzh.web.mapper.CzPlatConfigTimeChecksMapper;
import org.yzh.web.model.entity.CzPlatConfigTimeChecks;
import org.yzh.web.service.CzPlatConfigTimeChecksService;

import java.util.concurrent.TimeUnit;

/**
 * @author Star
 * @description: TODO
 * @date 2022/12/7 14:33
 */
@Service
public class CzPlatConfigTimeChecksServiceImpl implements CzPlatConfigTimeChecksService {
    @Autowired
    private CzPlatConfigTimeChecksMapper czPlatConfigTimeChecksMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public float getConfigValueById(int id) {
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        String key = RedisKeyConfig.configTimeCheck+id;
        String value = operations.get(key);
        if(StringUtils.isEmpty(value)){
            CzPlatConfigTimeChecks model = czPlatConfigTimeChecksMapper.selectByPrimaryKey(id);
            if(model != null){
                value =model.getConfigValue();

            }else{
                value = "0";
            }
            operations.set(key,value,24L, TimeUnit.HOURS);
        }

        return Float.parseFloat(value);

    }
}
