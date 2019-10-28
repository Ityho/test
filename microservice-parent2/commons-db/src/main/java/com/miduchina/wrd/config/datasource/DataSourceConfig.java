/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: DataSourceConfig.java 
 * @Prject: wyq-star-db
 * @Package: com.xd.stardb.com.miduchina.wrd.com.miduchina.wrd.com.miduchina.wrd.api.rankinglist.config
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年7月12日 下午2:54:08 
 * @version: V1.0   
 */
package com.miduchina.wrd.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DataSourceConfig
 * @Description: 数据源管理
 * @author: 许双龙
 * @date: 2017年7月12日 下午2:54:08
 */
@Configuration
public class DataSourceConfig {
	@Bean(name = "masterDS")
	@Qualifier("masterDS")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
	}
	@Bean(name = "slaveDS")
	@Qualifier("slaveDS")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
	}
	@Bean(name = "routerDS")
	@Qualifier("routerDS")
	public MasterSlaveDataSourceRouter masterSlaveDataSourceRouter() {
		MasterSlaveDataSourceRouter masterSlaveDataSourceRouter = new MasterSlaveDataSourceRouter();
		masterSlaveDataSourceRouter.setDefaultTargetDataSource(masterDataSource());
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(MasterSlaveDataSourceHolder.MASTER, masterDataSource());
		targetDataSources.put(MasterSlaveDataSourceHolder.SLAVE, slaveDataSource());
		masterSlaveDataSourceRouter.setTargetDataSources(targetDataSources);
		return masterSlaveDataSourceRouter;
	}
}
