package com.ybc.shiro.Configration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ybc.shiro.Shiro.ShiroRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfigration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        LinkedHashMap<String, String> filterChain = new LinkedHashMap<>();

        filterChain.put("/css/**", "anon");
        filterChain.put("/js/**", "anon");
        filterChain.put("/image/**", "anon");
        filterChain.put("/fonts/**", "anon");

        // druid数据源监控页面不拦截
        filterChain.put("/druid/**", "anon");

        filterChain.put("/logout","logout");
        filterChain.put("/", "anon");
        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
        filterChain.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(MyRealm());
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager());
        return defaultWebSecurityManager;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager);
        return attributeSourceAdvisor;
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public Realm MyRealm(){
        return new ShiroRealm();
    }


    public SimpleCookie rememberCookie(){
        //  设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(86400);
        return simpleCookie;
    }

    public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

//     使用shiro标签配置
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
