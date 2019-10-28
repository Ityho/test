package com.miduchina.wrd.eventanalysis.base;

import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.constant.SystemConstants;

public abstract class BaseService {
    protected String getSessionName(String name) {
        StringBuilder sessionName = new StringBuilder(SystemConstants.JEDIS_KEYS_HOT_RANK);
        sessionName.append(name);
        return RedisUtils.generateJedisKey(sessionName.toString());
    }
}
