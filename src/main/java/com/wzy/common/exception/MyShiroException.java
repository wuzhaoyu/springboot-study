package com.wzy.common.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 类功能说明: 自定义异常
 * 类修改者	创建日期2019/12/19
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
@ControllerAdvice
public class MyShiroException {

    /**
     * 处理Shiro权限拦截异常
     * 如果返回JSON数据格式 @ResponseBady注解
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Map<String, Object> defalutErrorHandle() {
        Map<String, Object> map = new HashMap<>();
        map.put("403", "权限不足");
        return map;

    }
}
