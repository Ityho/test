/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: MasterSlaveDataSourceHolder.java 
 * @Prject: bdm-db
 * @Package: com.midu.db.com.miduchina.wrd.com.miduchina.wrd.com.miduchina.wrd.api.rankinglist.config
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年8月27日 下午5:32:25 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.casebase.config.datasource;

/**
 * @ClassName: MasterSlaveDataSourceHolder
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年8月27日 下午5:32:25
 */
public class MasterSlaveDataSourceHolder {
	public final static String MASTER = "master";
	public final static String SLAVE = "slave";
	public static final ThreadLocal<String> holder = new ThreadLocal<String>();

	/**
	 * 设置数据源
	 * @param dbType
	 */
	public static void setDbType(String dbType) {
		holder.set(dbType);
	}
	/**
	 * 获取数据源
	 * @return String
	 */
	public static String getDbType() {
		return holder.get();
	}
	/**
	 * 清除数据源
	 * @return String
	 */
	public static void clearDbType() {
		holder.remove();
	}
}
