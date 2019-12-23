package com.wzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.TomcatServletWebServerFactoryCustomizer;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
@MapperScan(basePackages = "com.wzy.dao")
public class SpringbootStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootStudyApplication.class, args);
	}
	/*@Bean
	public myViewResvoler getMyViewResvoler(){
		return new myViewResvoler();
	}

	public class myViewResvoler implements ViewResolver{
		@Override
		public View resolveViewName(String s, Locale locale) throws Exception {
			return null;
		}
	}*/
	/**
	 * 修改Tomcat配置
	 */
	/*@Bean
	public WebServerFactoryCustomizer webServerFactoryCustomizer(ServerProperties serverProperties){
		serverProperties.setPort(8087);
		return new TomcatServletWebServerFactoryCustomizer(serverProperties) ;
	}*/

}
