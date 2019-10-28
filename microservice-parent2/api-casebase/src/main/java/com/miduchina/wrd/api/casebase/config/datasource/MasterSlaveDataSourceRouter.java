/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: MasterSlaveDataSourceRouter.java 
 * @Prject: bdm-db
 * @Package: com.midu.db.com.miduchina.wrd.com.miduchina.wrd.com.miduchina.wrd.api.rankinglist.config
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年8月25日 下午4:52:43 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.casebase.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: MasterSlaveDataSourceRouter 
 * @Description: 数据源路由器
 * @author: 许双龙
 * @date: 2017年8月25日 下午4:52:43  
 */
public class MasterSlaveDataSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return MasterSlaveDataSourceHolder.getDbType();
	}

	@Override
	protected DruidDataSource determineTargetDataSource() {
		DruidDataSource ds = (DruidDataSource) super.determineTargetDataSource();
		return ds;
	}
}
