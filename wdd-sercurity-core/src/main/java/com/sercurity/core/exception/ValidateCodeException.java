package com.sercurity.core.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * AuthenticationException是身份认证过程中抛出的异常的基类
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -3878497629904568096L;

    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
