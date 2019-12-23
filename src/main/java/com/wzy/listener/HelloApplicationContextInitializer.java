package com.wzy.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 类功能说明:
 * 类修改者	创建日期2019/12/16
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
public class HelloApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("HelloApplicationContextInitializer...init...");
    }
}
