/**
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: BriefService.java
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.service
 * @Description: 热门现象业务接口
 * @author: 许双龙   
 * @date: 2018年8月17日 下午1:12:54 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.service.infomonitor;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: BriefService
 * @Description: 简报业务接口
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:12:54  
 */
public interface BriefService {

	/**
	 * 查询用户素材总量
	 * @see #getSourceMaterialCount(JSONObject)
	 * @param params 条件
	 * @return Boolean
	 * */
	Integer getSourceMaterialCount(JSONObject params);
}
