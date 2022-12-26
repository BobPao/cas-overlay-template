package com.doctorcom.config;

import com.doctorcom.service.CustomAuthenticationPreProcessor;
import com.doctorcom.service.GAuthMFAPreProcessor;
import com.doctorcom.service.RememberMeUsernamePasswordCaptchaAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;

/**
 * 配置登录页，增加验证码字段
 */
public class CustomAuthenticationEventExecutionPlanConfigurer implements AuthenticationEventExecutionPlanConfigurer {

    private final CustomAuthenticationPreProcessor customAuthenticationPreProcessor;
    private final GAuthMFAPreProcessor gAuthMFAPreProcessor;
    private final RememberMeUsernamePasswordCaptchaAuthenticationHandler rememberMeUsernamePasswordCaptchaAuthenticationHandler;

    public CustomAuthenticationEventExecutionPlanConfigurer(CustomAuthenticationPreProcessor customAuthenticationPreProcessor,
                                                            GAuthMFAPreProcessor gAuthMFAPreProcessor,
                                                            RememberMeUsernamePasswordCaptchaAuthenticationHandler myAuthenticationHandler) {
        this.customAuthenticationPreProcessor = customAuthenticationPreProcessor;
        this.gAuthMFAPreProcessor = gAuthMFAPreProcessor;
        this.rememberMeUsernamePasswordCaptchaAuthenticationHandler = myAuthenticationHandler;
    }


    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationPreProcessor(customAuthenticationPreProcessor);
        plan.registerAuthenticationPreProcessor(gAuthMFAPreProcessor);
        plan.registerAuthenticationHandler(rememberMeUsernamePasswordCaptchaAuthenticationHandler);
    }
}
