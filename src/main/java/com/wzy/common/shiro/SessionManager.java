package com.wzy.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 类功能说明: 自定义获取Token
 * 类修改者	创建日期2019/12/20
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
public class SessionManager extends DefaultWebSessionManager {
    //定义常量
    private static final String AUTHORIZATION = "Authorization";
    private static final String  REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    //重构构造器
    public SessionManager(){
        super();
        this.setDeleteInvalidSessions(true);
    }
    /**
     * @Description: 重写方法请求头获取Token便于接口统一
     *  每次请求进来，Shiro会去请求头找Authorization这个key对应的Value(Token)
     * @Author:   wzy
     * @Date:      2019/12/20 17:23
     * @Param:
     * @return:
     **/
     @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response){
         String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
         //如果请求中存在token 则从请求中获取token
         if(!StringUtils.isEmpty(token)){
             request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
             request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,token);
             request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
             return token;
         }else{
             // 这里禁用掉Cookie获取方式
             // 按默认规则从Cookie取Token
             // return super.getSessionId(request, response);
             return null;
         }
     }
}
