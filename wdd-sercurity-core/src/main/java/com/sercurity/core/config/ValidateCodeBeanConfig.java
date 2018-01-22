package com.sercurity.core.config;

import com.sercurity.core.controller.ValidateCodeCreate;
import com.sercurity.core.properties.SecurityProperties;
import com.sercurity.core.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    //过滤条件如果存在name为imageCodeUtil的实现类name下面注入bean的操作不执行
    @ConditionalOnMissingBean(name="imageCodeUtil")
    public ValidateCodeCreate imageCodeCreate(){
        ImageCodeUtil imageCodeUtil=new ImageCodeUtil();
        imageCodeUtil.setSecurityProperties(securityProperties);
        return imageCodeUtil;
    }

}
