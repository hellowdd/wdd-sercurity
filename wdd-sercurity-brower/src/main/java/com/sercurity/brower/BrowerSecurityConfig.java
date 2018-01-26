package com.sercurity.brower;

import com.sercurity.core.filter.ValidateCodeFilter;
import com.sercurity.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created by Mr-Wei on 2018-1-1.
 */
@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler wddAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler wddAuthenticationFailHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetailService;


    //采用springsercurity的加密方法，如果采用自己的加密，可以自己实现PPasswordEncoder接口,返回自己的实现类型
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //记住我的功能配置
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //自动建立记住我的数据库表
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(wddAuthenticationFailHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();
        logger.info(securityProperties.getBrower().getLoginPage());
        //通过表单登录,将验证码过滤器放在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //配置的controller，跳转
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(wddAuthenticationSuccessHandler)
                .failureHandler(wddAuthenticationFailHandler)
                .and()
                //记住我配置
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrower().getRememberMeSeconds())
                .userDetailsService(myUserDetailService)
                .and()
                //对请求授权
                .authorizeRequests()
                //那些请求不需要验证
                .antMatchers("/authentication/require", securityProperties.getBrower().getLoginPage(), "/code/image")
                .permitAll()
                //任何请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                .and()
                // 关闭csrf保护功能（跨域访问）
                .csrf().disable();
    }
}
