package com.doctorcom.config;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
public class KaptchaConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws ServletException {
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
}
