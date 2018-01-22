package com.sercurity.core.controller;

import com.sercurity.core.bean.ImageCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeCreate {
    ImageCode createImageCode(HttpServletRequest request);
}
