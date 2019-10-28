package com.miduchina.wrd.api.rankinglist.aop;

import com.miduchina.wrd.api.rankinglist.config.datasource.MasterSlaveDataSourceHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * 在service层觉得数据源 必须在事务AOP之前执行，所以实现Ordered,order的值越小，越先执行 如果一旦开始切换到写库，则之后的读都会走写库
 * @author xsl
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class DataSourceAopInService implements PriorityOrdered {
	
	@Before("execution(* com.miduchina.wrd.api.rankinglist.service..*.find*(..))" +
	" || execution(* com.miduchina.wrd.api.rankinglist.service..*.get*(..))" +
	" || execution(* com.miduchina.wrd.api.rankinglist.service..*.query*(..))" +
	" || execution(* com.miduchina.wrd.api.rankinglist.service..*.select*(..))")
	public void setReadDataSourceType() { 
		//如果已经开启写事务了，那之后的所有读都从写库读
		MasterSlaveDataSourceHolder.setDbType(MasterSlaveDataSourceHolder.RANKLIST);
	}
	@Before("execution(* com.miduchina.wrd.api.rankinglist.mediasel.service..*.find*(..))" +
			" || execution(* com.miduchina.wrd.api.rankinglist.mediasel.service..*.get*(..))" +
			" || execution(* com.miduchina.wrd.api.rankinglist.mediasel.service..*.query*(..))")
	public void setMediaselDataSourceType() { 
		//如果已经开启写事务了，那之后的所有读都从写库读
		MasterSlaveDataSourceHolder.setDbType(MasterSlaveDataSourceHolder.MEDIASEL);
	}
	
	@Before("execution(* com.miduchina.wrd.api.rankinglist.service..*.insert*(..))" +
	" || execution(* com.miduchina.wrd.api.rankinglist.service..*.update*(..))" +
	" || execution(* com.miduchina.wrd.api.rankinglist.service..*.add*(..))")
	public void setWriteDataSourceType() { 
		MasterSlaveDataSourceHolder.setDbType(MasterSlaveDataSourceHolder.RANKLIST);
	}

	@Override
	public int getOrder() {
		/**
		 * 值越小，越优先执行 要优于事务的执行 在启动类中加上了@EnableTransactionManagement(order = 10)
		 */
		return 1;
	}
}
