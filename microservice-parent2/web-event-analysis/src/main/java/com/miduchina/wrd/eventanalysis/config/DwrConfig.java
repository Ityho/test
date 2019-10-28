package com.miduchina.wrd.eventanalysis.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ltd.getahead.dwr.DWRServlet;

@Configuration
public class DwrConfig {

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {

		DWRServlet servlet  = new DWRServlet();
//		DwrSpringServlet servlet = new DwrSpringServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/dwr/*");
		//设置成true使DWR能够debug和进入测试页面。
		registrationBean.addInitParameter("debug", "true");
		//pollAndCometEnabled 设置成true能增加服务器的加载能力，尽管DWR有保护服务器过载的机制。
		registrationBean.addInitParameter("pollAndCometEnabled", "true");

		registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");
		registrationBean.addInitParameter("maxWaitAfterWrite", "60");
		return registrationBean;
	}


}
