package com.sercurity.core.controller;

import com.sercurity.core.bean.ValidateCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeCreate {
    ValidateCode createImageCode(HttpServletRequest request);
}
