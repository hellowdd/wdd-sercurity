package com.sercurity.core.sms;

import com.sercurity.core.bean.SmsCode;

import javax.servlet.http.HttpServletRequest;

public interface SmsCodeCreate {
    SmsCode createImageCode(HttpServletRequest request);
}
