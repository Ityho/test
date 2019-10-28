/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: Swagger2.java 
 * @Prject: wyq-enterprise-api
 * @Package: com.midu.wyq.enterprise.com.miduchina.wrd.com.miduchina.wrd.com.miduchina.wrd.api.rankinglist.config
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年3月19日 下午7:14:04 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;
/**
 * @ClassName: Swagger2
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年3月19日 下午7:14:04
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	//private static final String DEFAULT_PATH = "/ranklist";
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.miduchina.wrd.api.rankinglist.controller")).paths(PathSelectors.any()).build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("排行榜 RESTful APIs")
				.description("排行榜api接口文档")
				.version("1.0")
				.termsOfServiceUrl("")
				.build();
	}

//	@Bean
//	UiConfiguration uiConfig() {
//		return UiConfigurationBuilder.builder()
//				.deepLinking(true)
//				.displayOperationId(false)
//				.defaultModelsExpandDepth(1)
//				.defaultModelExpandDepth(1)
//				.defaultModelRendering(ModelRendering.EXAMPLE)
//				.displayRequestDuration(false)
//				.docExpansion(DocExpansion.NONE)
//				.filter(false)
//				.maxDisplayedTags(null)
//				.operationsSorter(OperationsSorter.ALPHA)
//				.showExtensions(false)
//				.tagsSorter(TagsSorter.ALPHA)
//				.validatorUrl(null)
//				.build();
//	}

//	/**
//	 * SwaggerUI资源访问
//	 * @param servletContext
//	 * @param order
//	 * @return
//	 * @throws Exception
//	 */
//	@Bean
//	public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext,
//			@Value("${swagger.mapping.order:10}") int order) throws Exception {
//		SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
//		Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
//		{
//			PathResourceResolver pathResourceResolver = new PathResourceResolver();
//			pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
//			pathResourceResolver.setUrlPathHelper(new UrlPathHelper());
//			ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
//			resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
//			resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
//			resourceHttpRequestHandler.setServletContext(servletContext);
//			resourceHttpRequestHandler.afterPropertiesSet();
//			// 设置新的路径
//			urlMap.put(DEFAULT_PATH + "/webjars/**", resourceHttpRequestHandler);
//		}
//		{
//			PathResourceResolver pathResourceResolver = new PathResourceResolver();
//			pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
//			pathResourceResolver.setUrlPathHelper(new UrlPathHelper());
//			ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
//			resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
//			resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
//			resourceHttpRequestHandler.setServletContext(servletContext);
//			resourceHttpRequestHandler.afterPropertiesSet();
//			// 设置新的路径
//			urlMap.put(DEFAULT_PATH + "/**", resourceHttpRequestHandler);
//		}
//		urlHandlerMapping.setUrlMap(urlMap);
//		// 调整DispatcherServlet关于SimpleUrlHandlerMapping的排序
//		urlHandlerMapping.setOrder(order);
//		return urlHandlerMapping;
//	}
//
//	/**
//	 * SwaggerUI接口访问
//	 */
//	@Controller
//	@ApiIgnore
//	@RequestMapping(DEFAULT_PATH)
//	public static class SwaggerResourceController implements InitializingBean {
//		@Autowired
//		private ApiResourceController apiResourceController;
//		@Autowired
//		private Environment environment;
//		@Autowired
//		private DocumentationCache documentationCache;
//		@Autowired
//		private ServiceModelToSwagger2Mapper mapper;
//		@Autowired
//		private JsonSerializer jsonSerializer;
//		private Swagger2Controller swagger2Controller;
//
//		@Override
//		public void afterPropertiesSet() {
//			swagger2Controller = new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
//		}
//		/**
//		 * 首页
//		 * @return
//		 */
//		@RequestMapping
//		public ModelAndView index() {
//			ModelAndView modelAndView = new ModelAndView("redirect:" + DEFAULT_PATH + "/swagger-ui.html");
//			return modelAndView;
//		}
//		@RequestMapping("/swagger-resources/configuration/security")
//		@ResponseBody
//		public ResponseEntity<SecurityConfiguration> securityConfiguration() {
//			return apiResourceController.securityConfiguration();
//		}
//		@RequestMapping("/swagger-resources/configuration/ui")
//		@ResponseBody
//		public ResponseEntity<UiConfiguration> uiConfiguration() {
//			return apiResourceController.uiConfiguration();
//		}
//		@RequestMapping("/swagger-resources")
//		@ResponseBody
//		public ResponseEntity<List<SwaggerResource>> swaggerResources() {
//			return apiResourceController.swaggerResources();
//		}
//		@RequestMapping(value = "/v2/api-docs", method = RequestMethod.GET, produces = { "application/json",
//				"application/hal+json" })
//		@ResponseBody
//		public ResponseEntity<Json> getDocumentation(
//				@RequestParam(value = "group", required = false) String swaggerGroup,
//				HttpServletRequest servletRequest) {
//			return swagger2Controller.getDocumentation(swaggerGroup, servletRequest);
//		}
//	}
}