package com.doctorcom.service;

import com.doctorcom.exceptions.GAuthMfaException;
import org.apereo.cas.authentication.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Google Authenticator 身份验证器预处理器
 * 二次验证码连续 6 次输入错误暂时锁定
 */
public class GAuthMFAPreProcessor implements AuthenticationPreProcessor {

    /**
     * 存在多个预处理器时执行顺序
     *
     * @return 执行顺序
     */
    @Override
    public int getOrder() {
        return 2;
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
                map.put("GAuthMfaException", new GAuthMfaException("重试次数超限"));
                throw new AuthenticationException(map);
            }
        }
        return true;
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof OneTimeTokenCredential;
    }
}
