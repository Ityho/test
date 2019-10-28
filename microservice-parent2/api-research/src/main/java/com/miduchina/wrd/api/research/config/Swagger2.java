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
package com.miduchina.wrd.api.research.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
				.apis(RequestHandlerSelectors.basePackage("com.miduchina.wrd.api")).paths(PathSelectors.any()).build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("info-monitor RESTful APIs").description("新版微热点api接口文档").version("1.0").build();
	}

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
