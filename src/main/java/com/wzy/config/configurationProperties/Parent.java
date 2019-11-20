package com.wzy.config.configurationProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 类功能说明:
 * 类修改者	创建日期2019/11/20
 * 修改说明
 *   组件必须在容器中
 *   1.:ConfigurationProperties : 将配置文件中的属性映射到javaBean中；支持赋值数据类型 ；支持valid检验
 *   2.使用@Value ${}配置文件或者环境变量中获取值 #{} spring的计算表达式；不支持复杂数据类型；不支持valid检验
 * @author wzy
 * @version V1.0
 **/
@Component
//@ConfigurationProperties(prefix = "parent")
public class Parent {

    @Value("${parent.name}")
    private String name;
    @Value("#{10*2}")
    private Integer age;
    @Value("${parent.date}")
    private Date date;
//    @Value("${parent.args}")
    private String[] args;
    private Map<String,Object> maps;
    private Childern childern;

    @Override
    public String toString() {
        return "Parent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", args=" + Arrays.toString(args) +
                ", maps=" + maps +
                ", childern=" + childern +
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public Childern getChildern() {
        return childern;
    }

    public void setChildern(Childern childern) {
        this.childern = childern;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
