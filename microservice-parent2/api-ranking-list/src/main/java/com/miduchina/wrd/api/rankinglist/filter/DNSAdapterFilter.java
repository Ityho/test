package com.miduchina.wrd.api.rankinglist.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DNSAdapterFilter
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年9月3日 下午8:56:22
 */
@Slf4j
@WebFilter(filterName = "dnsAdapterFilter", urlPatterns = "/*")
public class DNSAdapterFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("过滤器初始化");
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

		log.info("过滤路径 urlAndParam ===>>> {}", urlAndParam);
		if (urlHead.equals(urlAndParam)) {
			httpResponse.sendRedirect("/swagger-ui.html");
		} else if (urlAndParam.indexOf(urlHead) == -1) {
			httpResponse.sendRedirect("/swagger-ui.html");
		} else {
			chain.doFilter(request, response);
		}
	}
	@Override
	public void destroy() {
		log.info("过滤器销毁");
	}
}
