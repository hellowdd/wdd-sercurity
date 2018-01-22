package com.sercurity.controller;

import com.sercurity.core.properties.SecurityProperties;
import com.sercurity.support.ResponseVo;
import com.sercurity.support.ResponseVoUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class BrowerSecurityController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 请求的缓存，判断跳转的是不是html
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份验证时，跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code= HttpStatus.UNAUTHORIZED)
    public ResponseVo requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SavedRequest savedRequest=requestCache.getRequest(request,response);
        if(savedRequest!=null){
            String targetUrl=savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是："+targetUrl);
            //开始判断是否为html，如结尾为html就是html请求
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                //跳转到配置的登录页中
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrower().getLoginPage());
            }
        }
        return ResponseVoUtil.fail("未登录请先登录","401");
    }
}
