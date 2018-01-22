package com.sercurity.core.controller;

import com.sercurity.core.bean.ImageCode;
import com.sercurity.core.properties.SecurityProperties;
import com.sercurity.core.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    public static final String sessionKey="SESSION_KEY_IMAGE_CODE";
    //spring的工具类
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Resource
    private ValidateCodeCreate imageCodeUtil;
    /**
     *
     */
    @GetMapping("/code/image")
    public void createCode() throws IOException {
        ImageCode imageCode = imageCodeUtil.createImageCode(request);
        //将生成的code放到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),sessionKey,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }


}
