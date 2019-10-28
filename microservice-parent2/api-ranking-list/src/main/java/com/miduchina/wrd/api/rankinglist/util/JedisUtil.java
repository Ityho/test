package com.miduchina.wrd.api.rankinglist.util;

import com.alibaba.fastjson.JSON;
import com.xd.tools.method.wyq.web.redis.WyqWebRedisMethodV1;
import com.xd.tools.pojo.RedisObject;
import com.xd.tools.pojo.RedisParams;
import com.xd.tools.view.BaseView;
import com.xd.tools.view.ObjectView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JedisUtil {

	public static void setAttribute(String key, String value,int second,String userId) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {

			RedisParams redisParams = new RedisParams();
			List<RedisObject> redisObjects = new ArrayList<>();
			RedisObject redisObject = new RedisObject();
			//设置redis的key、
			redisObject.setRedisKey(key);
			//redis db库   默认是0
//			redisParams.setRedisDb(0);
			//写入类型   value = "写入类型(1:lpush 2:rpush 4:set 6:incr 7:setex 8:expire 9:hdel 10:sadd 11:hincrBy)",
			redisObject.setRedisTypeWrite(RedisObject.REDIS_TYPE_WRITE_SET);
			//value值
			redisObject.setRedisValue(value);
			//存活时间
			redisObject.setRedisTimeOut(String.valueOf(second));
			redisObjects.add(redisObject);
			redisParams.setRedisObjectWriteList(redisObjects);
			redisParams.setRedisIdentification(RedisParams.REDIS_OPEN_COMMON);
			BaseView  objectView = null;
			try {
				objectView= WyqWebRedisMethodV1.redisCommonInsert(userId, redisParams);
				log.info("setAttribute = {}",JSON.toJSONString(objectView));
			} catch (Exception e) {
				log.error("setAttribute = {}",JSON.toJSONString(e));
			}
		}
	}
	public static String getAttribute(String key,String userId) {
		RedisParams redisParam = new RedisParams();
		redisParam.setRedisIdentification(RedisParams.REDIS_OPEN_COMMON);
		redisParam.setRedisKey(key);
		// 查询条数
		redisParam.setRedisNum(10);
		redisParam.setRedisTypeRead(RedisParams.REDIS_TYPE_READ_GET);
		ObjectView objectView = null;
		try {
			objectView = WyqWebRedisMethodV1.redisCommonSelect(userId, redisParam);
			log.info("getAttribute = {}",JSON.toJSONString(objectView));
		} catch (Exception e) {
			log.error("getAttribute = {}",JSON.toJSONString(e));
		}
		List<Object> objects = objectView.getoList();
		if(CollectionUtils.isNotEmpty(objects)){
			return objects.get(0).toString();
		}else{
			return null;
		}

	}
	public static void removeAttribute(String key,String userId) {
		RedisParams redisParams = new RedisParams();
		RedisObject redisObject = new RedisObject();
		redisObject.setRedisKey(key);
		redisObject.setRedisTypeWrite(RedisObject.REDIS_TYPE_WRITE_DEL);
		redisParams.setRedisIdentification(RedisParams.REDIS_OPEN_COMMON);
		List<RedisObject> redisObjectWriteList = new ArrayList<>();
		redisObjectWriteList.add(redisObject);
		redisParams.setRedisObjectWriteList(redisObjectWriteList);
		BaseView baseView1 = null;
		try {
			baseView1 = WyqWebRedisMethodV1 .redisCommonInsert(userId, redisParams);
			log.info("removeAttribute = {}",JSON.toJSONString(baseView1));
		} catch (Exception e) {
			log.error("removeAttribute = {}",JSON.toJSONString(e));
		}
	}
}
