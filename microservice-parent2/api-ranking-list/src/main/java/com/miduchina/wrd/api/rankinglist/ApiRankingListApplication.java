package com.miduchina.wrd.api.rankinglist;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@RefreshScope //动态更新配置
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
@ServletComponentScan   //Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
public class ApiRankingListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRankingListApplication.class, args);
	}

//	@Value("${https.port}")
//	private Integer httpsPort;
//	@Value("${server.port}")
//	private Integer serverPort;
//
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//		tomcat.addAdditionalTomcatConnectors(createSslConnector());
//		return tomcat;
//	}
//
//	private Connector createSslConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		//Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//		//同时启用http（8080）、https（8443）两个端口
//		connector.setScheme("http");
//		connector.setSecure(false);
//		connector.setPort(httpsPort);
//		//protocol.setSSLEnabled(true);
//		//connector.setRedirectPort(serverPort);
//		return connector;
//	}

//	@Value("${https.port}")
//	private Integer port;
//
//	@Value("${https.ssl.key-store-type}")
//	private String keyStoreType;
//
//	@Value("${https.ssl.key-store-password}")
//	private String keyStorePassword;
//
//	// 这是spring boot 2.0.X版本的 添加这个，上一个就不用添加了
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//		tomcat.addAdditionalTomcatConnectors(createSslConnector()); // 添加http
//		return tomcat;
//	}
//
//	// 配置https
//	private Connector createSslConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//		try {
//			File keystore = new ClassPathResource("51wyq.cn.jks").getFile();
//			connector.setScheme("https");
//			connector.setSecure(true);
//			connector.setPort(port);
//			protocol.setSSLEnabled(true);
//			protocol.setKeystoreFile(keystore.getAbsolutePath());
//			protocol.setKeystoreType(keyStoreType);
//			protocol.setKeystorePass(keyStorePassword);
//			return connector;
//		}
//		catch (IOException ex) {
//			throw new IllegalStateException("can't access keystore: [" + "keystore"
//					+ "] or truststore: [" + "keystore" + "]", ex);
//		}
//	}
}

