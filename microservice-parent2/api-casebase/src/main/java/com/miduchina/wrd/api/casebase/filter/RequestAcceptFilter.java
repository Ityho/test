/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: RequestAcceptFilter.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.filter
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年9月3日 下午8:56:22 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.casebase.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * @ClassName: RequestAcceptFilter
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年9月3日 下午8:56:22
 */
@WebFilter(filterName = "requestAcceptFilter", urlPatterns = "/*")
public class RequestAcceptFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new CustomeizedRequest((HttpServletRequest)request), response);
	}
	@Override
	public void destroy() {
	}

	private class CustomeizedRequest extends HttpServletRequestWrapper {
		public CustomeizedRequest(HttpServletRequest request) {
			super(request);
		}
		@Override
		public Enumeration<String> getHeaders(String name) {
			String format = this.getParameter("format");
			if (HttpHeaders.ACCEPT.equals(name) && (StringUtils.isBlank(format) || "json".equals(format))) {
				return new Enumeration<String>() {
					private boolean hasGetted = false;

					@Override
					public String nextElement() {
						if (hasGetted) {
							throw new NoSuchElementException();
						} else {
							hasGetted = true;
							return MediaType.APPLICATION_JSON_UTF8_VALUE;
						}
					}
					@Override
					public boolean hasMoreElements() {
						return !hasGetted;
					}
				};
			}
			return super.getHeaders(name);
		}
	}
}
