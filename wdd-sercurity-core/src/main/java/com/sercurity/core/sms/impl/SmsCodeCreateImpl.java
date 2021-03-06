package com.sercurity.core.sms.impl;

import com.sercurity.core.bean.SmsCode;
import com.sercurity.core.controller.ValidateCodeCreate;
import com.sercurity.core.properties.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SmsCodeCreateImpl implements ValidateCodeCreate {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public SmsCode createImageCode(HttpServletRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new SmsCode(code, securityProperties.getCode().getSms().getExpireIn());
    }
}
