package com.miduchina.wrd.api.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: DNSAdapterFilter
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年9月3日 下午8:56:22
 */
@WebFilter(filterName = "dnsAdapterFilter", urlPatterns = "/*")
public class DNSAdapterFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(DNSAdapterFilter.class); 
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("过滤器初始化");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String urlAndParam = httpRequest.getRequestURL().toString();
		String params = httpRequest.getQueryString();
		if (StringUtils.isNotBlank(params)) {
			urlAndParam = urlAndParam + "?" + params;
		}
		Integer port = httpRequest.getServerPort();
		String urlHead = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":"
				+ port + httpRequest.getContextPath() + "/";
		
		if(port == 80) {
			urlHead = httpRequest.getScheme() + "://" + httpRequest.getServerName() +"/";
		}
		
		logger.info("过滤路径 urlAndParam ===>>> {}", urlAndParam);
		if (urlHead.equals(urlAndParam)) {
			httpResponse.sendRedirect(urlHead + "swagger-ui.html");
		} else if (urlAndParam.indexOf(urlHead) == -1) {
			httpResponse.sendRedirect(urlHead + "swagger-ui.html");
		} else {
			chain.doFilter(request, response);
		}
	}
	@Override
	public void destroy() {
		logger.info("过滤器销毁");
	}
}
