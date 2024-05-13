package com.samjo.app.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Value("${file.upload.path}")
	private String uploadPath;

	// 리소스 핸들링(프로젝트 밖의 파일경로를 매핑할수 있음.)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {								//─┬─>2개가 한세트
		registry.addResourceHandler("/files/**").addResourceLocations("file:///" + uploadPath,"");	//─┘
//									가상경로명								└>복수 경로 등록가능. "","",...
	}
	// 인터셉터 등록
	// 뷰리졸버 등록
	
}
