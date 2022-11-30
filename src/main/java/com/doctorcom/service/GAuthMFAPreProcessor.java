package com.doctorcom.service;

import com.doctorcom.bean.CustomUsernamePasswordCaptchaCredential;
import com.doctorcom.exceptions.GAuthMfaException;
import org.apereo.cas.authentication.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Google Authenticator 身份验证器预处理器
 * 二次验证码连续 5 次输入错误暂时锁定
 * 不管第六次是否正确，都是会提示超限
 */
public class GAuthMFAPreProcessor implements AuthenticationPreProcessor {

    /**
     * 存在多个预处理器时执行顺序
     *
     * @return 执行顺序
     */
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean process(AuthenticationTransaction transaction) throws AuthenticationException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object mfaRetryTimes = attributes.getRequest().getSession().getAttribute("mfa-retry-times");
        if (mfaRetryTimes == null) {
            attributes.getRequest().getSession().setAttribute("mfa-retry-times", 1);
        } else {
            int mfaRetryTimesInt = (Integer) mfaRetryTimes;
            if (mfaRetryTimesInt < 5) {
                attributes.getRequest().getSession().setAttribute("mfa-retry-times", mfaRetryTimesInt + 1);
            } else {
                attributes.getRequest().getSession().setAttribute("mfa-retry-block", true);
                final Map<String, Throwable> map = new HashMap<>();
                map.put("GAuthMfaException", new GAuthMfaException());
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
