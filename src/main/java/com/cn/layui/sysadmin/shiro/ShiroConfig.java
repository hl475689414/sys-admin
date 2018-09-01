package com.cn.layui.sysadmin.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * Created by 李怀鹏 on 2018/6/14.
 */
@Configuration
public class ShiroConfig {
    @Value("${shiro_session_redis_overdueTime}")
    private int shiro_session_redis_overdueTime;

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setUnauthorizedUrl("/");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/image/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/json/**", "anon");
        filterMap.put("/layui/**", "anon");
        filterMap.put("/vue/**", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    /**
     * 此config在启动加载完成前已开始执行，导致类中无法使用注解注入，尝试过使用上下文注入也失败，所以在此声明一个bean来使用
     * @return
     */
    @Bean
    public RedisSessionDAO sessionDAO() {
        return new RedisSessionDAO();
    }

    /**
     * 安全管理模块，所有的manager在此配置
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //自定义realm
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public ShiroRealm myShiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        //设置缓存管理器
        shiroRealm.setCacheManager(redisCacheManager());
        shiroRealm.setCachingEnabled(true);
        //认证
        shiroRealm.setAuthenticationCachingEnabled(false);
        //授权
        shiroRealm.setAuthorizationCachingEnabled(false);
        return shiroRealm;
    }

    /**
     * 缓存管理器的配置
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    /**
     *  配置sessionmanager，由redis存储数据
     *  登录成功后，sessionId已存入redis，可直接获取使用
     *  使用浏览器cookie形式时可以使用此方法
     */
    @Bean
//    public SessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        // 设置全局session超时时间，这里是毫秒数,如果跟session过期时间设置单位不统一
//        // 将会在会话过期引起org.apache.shiro.session.ExpiredSessionException异常，导致无法访问，无法登录
//        sessionManager.setGlobalSessionTimeout(shiro_session_redis_overdueTime*1000);
//        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionIdCookieEnabled(true);
//        sessionManager.setSessionIdCookie(getSessionIdCookie());
//        sessionManager.setSessionDAO(sessionDAO());
//        sessionManager.setCacheManager(redisCacheManager());
//        sessionManager.setDeleteInvalidSessions(true);// 删除过期的session
//        sessionManager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
//        return sessionManager;
//    }
    /**
     *  配置sessionmanager，由redis存储数据
     *  登录成功后，sessionId已存入redis，可直接获取使用
     *  SessionManager为重写的类
     *  使用统一TOKEN访问形式时可以使用此方法,重写DefaultWebSessionManager的获取sessionID方法，实现token操作会话
     */
    public SessionManager sessionManager() {
        SessionManager sessionManager = new SessionManager();
        //设置全局session超时时间，这里是毫秒数,如果跟session过期时间设置单位不统一
        //将会在会话过期引起org.apache.shiro.session.ExpiredSessionException异常，导致无法访问，无法登录
        sessionManager.setGlobalSessionTimeout(shiro_session_redis_overdueTime*1000);
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(getSessionIdCookie());
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setDeleteInvalidSessions(true);// 删除过期的session
        sessionManager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
        return sessionManager;
    }

//    /**
//     * 限制同一账号登录同时登录人数控制
//     *
//     * @return
//     */
//    @Bean
//    public KickoutSessionControlFilter kickoutSessionControlFilter() {
//        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//        kickoutSessionControlFilter.setCacheManager(redisCacheManager());
//        kickoutSessionControlFilter.setSessionManager(sessionManager());
//        kickoutSessionControlFilter.setKickoutAfter(false);
//        kickoutSessionControlFilter.setMaxSession(1);
//        kickoutSessionControlFilter.setKickoutUrl("/sys/kickout");
//        return kickoutSessionControlFilter;
//    }

    /**
     * shrio会话验证调度器，会定期检查会话是否过期，过期则删除会话，如果不想过期时删除会话
     * 可以设置SessionManager的deleteInvalidSessions属性为false ，默认是开启的
     * @return
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(900000); //设置调度时间间隔，单位毫秒，默认就是1小时
        return scheduler;
    }

    /**
     * 设置cookie
     * @return
     */
    @Bean
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("mysid"); //更换cookie名字，防止与tomcat等容器冲突
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1); //最大生存时间(毫秒,0代表删除,-1代表与浏览器会话一致)
        return cookie;
    }

    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    /**
     * shiro管理生命周期的东西
     * 如果不设置为静态方法会导致bean对象无法注入进来
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
