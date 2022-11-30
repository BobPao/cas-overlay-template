package com.doctorcom.service;

import com.doctorcom.bean.CustomUsernamePasswordCaptchaCredential;
import com.doctorcom.exceptions.CaptchaMismatchException;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证预处理器
 * 在认证账号密码之前执行
 * 验证用户提交的验证码是否匹配
 */
public class CustomAuthenticationPreProcessor implements AuthenticationPreProcessor {

    /**
     * 存在多个预处理器时执行顺序
     * @return 执行顺序
     */
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean process(AuthenticationTransaction transaction) throws AuthenticationException {
        CustomUsernamePasswordCaptchaCredential credential = (CustomUsernamePasswordCaptchaCredential) transaction.getPrimaryCredential().orElse(null);
        if (credential != null && StringUtils.hasLength(credential.getCaptcha())) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Object sessionCaptcha = attributes.getRequest().getSession().getAttribute("captcha");
            if (sessionCaptcha != null && !credential.getCaptcha().equalsIgnoreCase(sessionCaptcha.toString())) {
                final Map<String, Throwable> map = new HashMap<>();
                map.put("CustomAuthenticationPreProcessor", new CaptchaMismatchException());
                credential.setCaptcha("");
                throw new AuthenticationException(map);
            }
        }
        return true;
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof CustomUsernamePasswordCaptchaCredential;
    }
}
