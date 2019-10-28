package com.miduchina.wrd.monitor.warning.filter;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**使用注解标注过滤器
* @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
* 属性filterName声明过滤器的名称,可选
* 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
* */
@WebFilter(filterName="dnsAdapterFilter",urlPatterns="/*")
public class DNSAdapterFilter implements Filter {

	private static final Log logger = LogFactory.getLog(DNSAdapterFilter.class);

	/** * Default constructor. */
	public DNSAdapterFilter() {}

	/** * @see Filter#destroy() */
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = httpRequest.getRequestURL().toString();
		String goUrlHead = "http://";

		if (uri.endsWith(".dwr") || uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".pdf") || uri.endsWith(".bmp") || uri.endsWith(".gif") || uri.endsWith(".html") || uri.endsWith(".ico") || uri.endsWith("getchart.jsp") || uri.endsWith("validation.jsp") || uri.contains("doNotify.action") || uri.endsWith(".ttf") || uri.endsWith(".shtml") || uri.endsWith(".mp4")) {
			logger.info("uri endsWith=" + uri);
			chain.doFilter(request, response);
		} else {
			logger.info("whole uri =" + uri);
			String dnsurl = ""; // like: www.51yuqing.cn
			int k = uri.indexOf("http://");

			if (k >= 0) {

				dnsurl = uri.substring(7);
				k = dnsurl.indexOf("/");
				if (k >= 0) {
					dnsurl = dnsurl.substring(0, k);
				}
				goUrlHead = goUrlHead + dnsurl; // http://dy.wyq.cn
				logger.info("whole goUrlHead =" + goUrlHead);
				/*int localid = uri.indexOf("/localhost:8080/");
				if (localid > 0) {
					goUrlHead = goUrlHead + "/warningview"; // http://dy.wyq.cn/
					logger.info("whole2 goUrlHead =" + goUrlHead);
				}*/
				int wapPos = uri.lastIndexOf("/a/");
				int sharePos = uri.lastIndexOf("/c/");
				int warningWPos = uri.lastIndexOf("/w/");
				int	morePos = uri.lastIndexOf("/more.action");
				if (wapPos >= 0) {//邮件预警 
					goUrlHead = uri.substring(0,wapPos);
					String shortUrl = uri.substring(wapPos + 3);
					System.out.println("last 12 bit string=" + shortUrl);
					logger.info("last 12 bit string=" + shortUrl);

					if (shortUrl.length() == 12 && shortUrl.matches("[\\da-zA-Z]*$")) {
						System.out.println("find keyUrl=" + shortUrl);
						logger.info("find keyUrl=" + shortUrl);
						httpResponse.sendRedirect(goUrlHead + "/warningCenter/warningDetail?reviewCode=" + shortUrl+"&listFlag=0");
						logger.info(goUrlHead + "/warningCenter/warningDetail.action?reviewCode=" + shortUrl+"&listFlag=0");
						return;
					} else {
						logger.info("	chain.doFilter " + shortUrl);
						httpResponse.sendRedirect(goUrlHead + "/logon.action?dnsid=" + dnsurl);
						return;
					}
				}
				
				if (warningWPos >= 0) {//私信预警 
					goUrlHead = uri.substring(0,warningWPos);
					String shortUrl = uri.substring(warningWPos + 3);
					System.out.println("last 12 bit string=" + shortUrl);
					logger.info("last 12 bit string=" + shortUrl);

					if (shortUrl.length() == 12 && shortUrl.matches("[\\da-zA-Z]*$")) {
						System.out.println("find keyUrl=" + shortUrl);
						logger.info("find keyUrl=" + shortUrl);
						httpResponse.sendRedirect(goUrlHead + "/warningCenter/warningDetail?reviewCode=" + shortUrl+"&paltform=weibo&listFlag=0");
						logger.info(goUrlHead + "/warningCenter/warningDetail.action?reviewCode=" + shortUrl+"&paltform=weibo&listFlag=0");
						return;
					} else {
						logger.info("	chain.doFilter " + shortUrl);
						httpResponse.sendRedirect(goUrlHead + "/logon.action?dnsid=" + dnsurl);
						return;
					}
				}
				
				if (sharePos >= 0) {//分享
					goUrlHead = uri.substring(0,sharePos);
					String shortUrl = uri.substring(sharePos + 3);
					System.out.println("last 13 bit string=" + shortUrl);
					logger.info("last 12 bit string=" + shortUrl);

					if (shortUrl.length() == 13 && shortUrl.matches("[\\da-zA-Z]*$")) {
						System.out.println("find keyUrl=" + shortUrl);
						logger.info("find keyUrl=" + shortUrl);
						httpResponse.sendRedirect(goUrlHead + "/warningCenter/shareList?newsReviewCode=" + shortUrl);
						logger.info(goUrlHead + "/warningCenter/shareList.action?newsReviewCode=" + shortUrl);
						return;
					} else {
						logger.info("	chain.doFilter " + shortUrl);
						httpResponse.sendRedirect(goUrlHead + "/logon.action?dnsid=" + dnsurl);
						return;
					}
				}
				
				if(morePos >= 0){
					goUrlHead = uri.substring(0,morePos);
					String url = goUrlHead + "/warningCenter/warningDetail";
					url = url + "?" + httpRequest.getQueryString();
					httpResponse.sendRedirect(url);
					logger.info(url);
					return;
				}
			}
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}
}