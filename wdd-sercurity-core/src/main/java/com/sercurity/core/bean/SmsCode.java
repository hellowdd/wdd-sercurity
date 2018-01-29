package com.sercurity.core.bean;

import java.time.LocalDateTime;

public class SmsCode extends ValidateCode{
    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }
}
