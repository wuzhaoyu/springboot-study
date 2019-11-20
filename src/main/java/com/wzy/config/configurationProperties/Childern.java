package com.wzy.config.configurationProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类功能说明:
 * 类修改者	创建日期2019/11/20
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
@Component
@ConfigurationProperties(prefix = "childern")
public class Childern {

    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Childern{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
