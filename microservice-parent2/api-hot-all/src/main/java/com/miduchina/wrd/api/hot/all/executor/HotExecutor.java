package com.miduchina.wrd.api.hot.all.executor;

import com.xd.tools.support.Init;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 热度后台线程执行器
 * 
 * @author lkc
 * @date 2017年4月12日 下午10:43:35
 * @version V1.0
 *
 */
@Component
public interface HotExecutor {

	/**
	 * 生成年度热度缓存
	 * 
	 * @Title: hotYearAllCache
	 * @param analysisTaskTicket
	 * @throws Throwable
	 * @author lkc
	 */
	public void doCreateHotYearAllCache(Init init, String analysisTaskTicket);

}
