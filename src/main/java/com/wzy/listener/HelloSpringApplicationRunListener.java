package com.wzy.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 类功能说明:
 * 类修改者	创建日期2019/12/16
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {
    public HelloSpringApplicationRunListener(SpringApplication app,String[] args){

    }
    @Override
    public void starting() {
        System.out.println("HelloSpringApplicationRunListener...starting...");
    }
    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringApplicationRunListener...started...");
    }
    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("HelloSpringApplicationRunListener...running...");
    }
    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("容器初始化...出错");
    }
}
