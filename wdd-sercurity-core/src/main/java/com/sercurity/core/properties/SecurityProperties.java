package com.sercurity.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * 读取前缀为wdd.security的配置项,关于brower的配置读取到BrowerProperties中
 */
@Component
@ConfigurationProperties(prefix = "wdd.security")
public class SecurityProperties {

    private BrowerProperties brower = new BrowerProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    public BrowerProperties getBrower() {
        return brower;
    }

    public void setBrower(BrowerProperties brower) {
        this.brower = brower;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}
