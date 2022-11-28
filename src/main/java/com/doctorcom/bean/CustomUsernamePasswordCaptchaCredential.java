package com.doctorcom.bean;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apereo.cas.authentication.UsernamePasswordCredential;

import javax.validation.constraints.Size;

/**
 * 继承默认登录表单，添加验证码字段
 */
public class CustomUsernamePasswordCaptchaCredential extends UsernamePasswordCredential {

    @Size(min = 4, max = 4, message = "captcha.required")
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public CustomUsernamePasswordCaptchaCredential setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.captcha)
                .toHashCode();
    }

}