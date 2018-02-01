package com.sercurity.core.controller;

import com.sercurity.core.bean.ImageCode;
import com.sercurity.core.bean.SmsCode;
import com.sercurity.core.bean.ValidateCode;
import com.sercurity.core.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class ValidateCodeController {

    public static final String sessionKey="SESSION_KEY_IMAGE_CODE";
    //spring的工具类
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ValidateCodeCreate imageCodeCreate;

    @Autowired
    private ValidateCodeCreate smsCodeCreateImpl;

    @Autowired
    private SmsCodeSender smsCodeSender;
    /**
     * 图形验证码
     */
    @GetMapping("/code/image")
    public void createCode() throws IOException {
        ImageCode imageCode = (ImageCode)imageCodeCreate.createImageCode(request);
        //将生成的code放到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),sessionKey,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }


    /**
     * 发送短信验证码
     */
    @GetMapping("/code/sms")
    public void createSmsCode() throws IOException, ServletRequestBindingException {
        ValidateCode smsCode = smsCodeCreateImpl.createImageCode(request);
        //将生成的code放到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),sessionKey,smsCode);
        String mobile= ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        smsCodeSender.send(mobile,smsCode.getCode());
    }


}
