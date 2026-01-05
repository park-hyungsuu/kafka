package com.hyungsuu.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hyungsuu.common.interceptor.AuthenticInterceptor;

@Configuration

public class WebConfig implements WebMvcConfigurer {
	// ---중략---/

	private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticInterceptor())
			.order(0)
			.addPathPatterns("/api/**") // 모든 URL에 적용
			.excludePathPatterns("/css/**", "/js/**", "/images/**"); // 특정 URL 제외
	}
}