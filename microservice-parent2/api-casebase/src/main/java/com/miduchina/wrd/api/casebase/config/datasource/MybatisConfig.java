package com.miduchina.wrd.api.casebase.config.datasource;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by sty on 2018/1/14.
 */
@Slf4j
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan(basePackages = "com.miduchina.wrd.api.casebase.mapper")
public class MybatisConfig {
	
	@Autowired
	@Qualifier("routerDS")
	private DataSource routerDS;

	@Bean
	@ConfigurationProperties(prefix = "mybatis")
	public SqlSessionFactory sqlSessionFactorys() throws Exception {
		log.info("--------------------  sqlSessionFactory init ---------------------");
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(routerDS);
		// 读取配置
		sessionFactoryBean.setTypeAliasesPackage("com.miduchina.wrd.po.casebase");
		// 设置mapper.xml文件所在位置
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml");
		sessionFactoryBean.setMapperLocations(resources);
		// 添加分页插件、打印sql插件
		Interceptor[] plugins = new Interceptor[] { pageHelper(), new SqlPrintInterceptor() };
		sessionFactoryBean.setPlugins(plugins);
		return sessionFactoryBean.getObject();
	}
	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		p.setProperty("returnPageInfo", "check");
		p.setProperty("params", "count=countSql");
		pageHelper.setProperties(p);
		return pageHelper;
	}
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	// 事务管理
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(routerDS);
	}
}
