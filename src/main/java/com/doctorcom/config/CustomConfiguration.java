package com.doctorcom.config;

import com.doctorcom.bean.CustomErrorAttributes;
import com.doctorcom.service.CustomAuthenticationPreProcessor;
import com.doctorcom.service.GAuthMFAPreProcessor;
import com.doctorcom.service.RememberMeUsernamePasswordCaptchaAuthenticationHandler;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Configuration("customWebflowConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomConfiguration {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @ConditionalOnMissingBean(name = "customWebflowConfigurer")
    @Bean
    public CasWebflowConfigurer customWebflowConfigurer() {
        CustomWebflowConfigurer c = new CustomWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
        c.initialize();
        return c;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/random/captcha.jpg");//加载路径
        servlet.addInitParameter("kaptcha.border", "yes");
        servlet.addInitParameter("kaptcha.border.color", "206,212,218");
        servlet.addInitParameter("kaptcha.session.key", "captcha");// session key
        servlet.addInitParameter("kaptcha.textproducer.font.color", "blue");
        servlet.addInitParameter("kaptcha.textproducer.font.size", "28");
        servlet.addInitParameter("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        servlet.addInitParameter("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        servlet.addInitParameter("kaptcha.image.width", "120");
        servlet.addInitParameter("kaptcha.image.height", "38");
        servlet.addInitParameter("kaptcha.textproducer.char.length", "4");
        servlet.addInitParameter("kaptcha.textproducer.char.space", "5");
//        servlet.addInitParameter("kaptcha.background.clear.from", "247,247,247");
//        servlet.addInitParameter("kaptcha.background.clear.to", "247,247,247");
        servlet.addInitParameter("kaptcha.textproducer.font.names", "Helvetica Neue, Helvetica, Arial, sans-serif");
        return servlet;
    }

    @Bean
    public CustomAuthenticationPreProcessor customAuthenticationPreProcessor() {
        return new CustomAuthenticationPreProcessor();
    }

    @ConditionalOnMissingBean(name = "customAuthenticationEventExecutionPlanConfigurer")
    @Bean
    public CustomAuthenticationEventExecutionPlanConfigurer customAuthenticationEventExecutionPlanConfigurer() {
        return new CustomAuthenticationEventExecutionPlanConfigurer(customAuthenticationPreProcessor(), gAuthMFAPreProcessor(), rememberMeUsernamePasswordCaptchaAuthenticationHandler());
    }

    @Bean
    @RefreshScope
    public ErrorAttributes errorAttributes() {
        return new CustomErrorAttributes();
    }

    @Bean
    public GAuthMFAPreProcessor gAuthMFAPreProcessor() {
        return new GAuthMFAPreProcessor();
    }

    @Bean
    public RememberMeUsernamePasswordCaptchaAuthenticationHandler rememberMeUsernamePasswordCaptchaAuthenticationHandler() {
        RememberMeUsernamePasswordCaptchaAuthenticationHandler handler = new RememberMeUsernamePasswordCaptchaAuthenticationHandler(
                RememberMeUsernamePasswordCaptchaAuthenticationHandler.class.getSimpleName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                9);
        return handler;
    }

}
