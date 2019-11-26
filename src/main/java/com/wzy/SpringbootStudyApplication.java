package com.wzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 *  @SpringBootConfiguration
 *  @EnableAutoConfiguration 开启自动配置
 *   @AutoConfigurationPackage 扫描配置包
 *   @Import 组件导入容器
 */
@SpringBootApplication
public class SpringbootStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootStudyApplication.class, args);
	}
	@Bean
	public myViewResvoler getMyViewResvoler(){
		return new myViewResvoler();
	}

	public class myViewResvoler implements ViewResolver{
		@Override
		public View resolveViewName(String s, Locale locale) throws Exception {
			return null;
		}
	}
}
