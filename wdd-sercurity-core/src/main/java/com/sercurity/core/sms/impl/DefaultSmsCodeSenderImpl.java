package com.sercurity.core.sms.impl;

import com.sercurity.core.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSmsCodeSenderImpl implements SmsCodeSender {
    @Override
    public void send(String mobile, String smsCode) {
        log.info("发送的验证码===================》"+smsCode);
        System.out.println("成功");
    }
}
