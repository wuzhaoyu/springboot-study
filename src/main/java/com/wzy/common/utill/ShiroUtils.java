package com.wzy.common.utill;


import com.wzy.entities.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description Shiro工具类
 * @Author Sans
 * @CreateTime 2019/6/15 16:11
 */
public class ShiroUtils {

    /**
     * 私有化构造器
     */
    private ShiroUtils() {
    }

    private static RedisSessionDAO redisSessionDAO = SpringUtil.getBean(RedisSessionDAO.class);

    /**
     * 获取当前用户Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户登出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static SysUserEntity getUserInfo(){
      return  (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     *  删除用户缓存信息
     * @param username 用户名称
     * @param isRemoveSeesion 是否删除session
     */
    public static void deleteCache(String username,boolean isRemoveSeesion){
        //从缓存中获取session
        Session session = null;
        Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
        SysUserEntity sysUserEntity;
        Object attribute = null;
        for(Session sessioninfo : activeSessions){
            //遍历session，找到用户名称对应的session
            attribute = sessioninfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(attribute == null){
                continue;
            }
            sysUserEntity =(SysUserEntity)((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if(sysUserEntity == null){
                continue;
            }
            //缓存中的session,与当前用户比较，相同时session 作为删除缓存session的条件
            if(Objects.equals(sysUserEntity.getUsername(),username)){
                session = sessioninfo;
                break;
            }
        }
        if(session == null || attribute == null){
            return;
        }

        //删除session
        if(isRemoveSeesion){
            redisSessionDAO.delete(session);
        }
        //删除Cache，在访问受限接口时会重新授权 ,刷新redis缓存
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authenticator = securityManager.getAuthenticator();
        ((LogoutAware) authenticator).onLogout((SimplePrincipalCollection) attribute);
    }
}