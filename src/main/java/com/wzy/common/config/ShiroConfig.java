package com.wzy.common.config;

import com.wzy.common.shiro.SessionManager;
import com.wzy.common.shiro.ShiroRealm;
import com.wzy.common.shiro.ShiroSeesionGenerator;
import com.wzy.common.utill.SHA256Util;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类功能说明: Shiro配置类
 * 类修改者	创建日期2019/12/20
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    private final int EXPIRE = 1800;

    //redis配置
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database}")
    private int database;


    /**
     * @Description: 开启shiro-aop 注解
     * @Author: wzy
     * @Date: 2019/12/21 14:02
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //注意过滤器配置顺序不能颠倒
        //配置过滤：不会被拦截的连接
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/userLogin/**", "anon");
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/userLogin/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * @Description: 安全管理
     * @Author: wzy
     * @Date: 2019/12/21 14:15
     **/
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //自定义session管理
        securityManager.setSessionManager(sessionManager());
        //自定义实现cache实现
        securityManager.setCacheManager(redisCacheManager());
        //自定义实现Realm
        securityManager.setRealm(realm());
        return securityManager;
    }


    /**
     * @Description: 身份校验
     * @Author: wzy
     * @Date: 2019/12/21 14:23
     **/
    @Bean
    public ShiroRealm realm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        //配置身份验证方式
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }


    /**
     * 凭证匹配器
     * 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     *
     * @Author: wzy
     * @Date: 2019/12/21 14:26
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法：这里使用SHA256算法
        hashedCredentialsMatcher.setHashAlgorithmName(SHA256Util.HASH_ALGORITHM_NAME);
        //散列的次数，比如散列次数,相当于 mad5(mad5())
        hashedCredentialsMatcher.setHashIterations(SHA256Util.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }


    /**
     * @Description: 配置Redis管理器
     * @Attention 使用的是shiro-redis开源插件
     * @Author: wzy
     * @Date: 2019/12/21 14:30
     **/
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
//         redisManager.setPassword(password);
        return redisManager;
    }


    /**
     * 配置Cache管理器
     * 用于往Redis存储权限和角色标识
     *
     * @Attention 使用的是shiro-redis开源插件
     * @Author: wzy
     * @Date: 2019/12/21 14:33
     **/
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        //配置缓存的话要求放在session里面的实体类必须有个ID标识
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;
    }


    /**
     * @Description: sessionID 生成器
     * @Author: wzy
     * @Date: 2019/12/21 14:38
     **/
    @Bean
    public ShiroSeesionGenerator generator() {
        return new ShiroSeesionGenerator();
    }


    /**
     * @Description: 配置RedisSessionDao
     * @Author: wzy
     * @Date: 2019/12/21 14:40
     **/
    @Bean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(EXPIRE);
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(generator());
        return redisSessionDAO;
    }


    /**
     * @Description: 配置Session管理器
     * @Author: wzy
     * @Date: 2019/12/21 14:44
     **/
    @Bean
    public SessionManager sessionManager() {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }
}

