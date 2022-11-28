package com.doctorcom.config;

import com.doctorcom.service.CustomAuthenticationPreProcessor;
import com.doctorcom.service.GAuthMFAPreProcessor;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;

/**
 * 配置登录页，增加验证码字段
 */
public class CustomAuthenticationEventExecutionPlanConfigurer implements AuthenticationEventExecutionPlanConfigurer {

    private final CustomAuthenticationPreProcessor customAuthenticationPreProcessor;
    private final GAuthMFAPreProcessor gAuthMFAPreProcessor;

    public CustomAuthenticationEventExecutionPlanConfigurer(CustomAuthenticationPreProcessor customAuthenticationPreProcessor,
                                                            GAuthMFAPreProcessor gAuthMFAPreProcessor) {
        this.customAuthenticationPreProcessor = customAuthenticationPreProcessor;
        this.gAuthMFAPreProcessor = gAuthMFAPreProcessor;
    }


    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationPreProcessor(customAuthenticationPreProcessor);
        plan.registerAuthenticationPreProcessor(gAuthMFAPreProcessor);
    }
}
