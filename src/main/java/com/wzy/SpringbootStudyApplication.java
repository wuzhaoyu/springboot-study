package com.wzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
