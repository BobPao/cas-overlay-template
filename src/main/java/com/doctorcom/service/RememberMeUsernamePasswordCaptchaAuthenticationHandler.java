package com.doctorcom.service;


import com.doctorcom.bean.CustomUsernamePasswordCaptchaCredential;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.webflow.execution.RequestContext;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class RememberMeUsernamePasswordCaptchaAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {




    public RememberMeUsernamePasswordCaptchaAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        CustomUsernamePasswordCaptchaCredential myCredential = (CustomUsernamePasswordCaptchaCredential) credential;
        String requestCaptcha = myCredential.getCaptcha();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object attribute = attributes.getRequest().getSession().getAttribute("captcha");
        String realCaptcha = attribute == null ? null : attribute.toString();

        String username = myCredential.getUsername();
        Map<String, Object> user = new HashMap();
        user.put("username","test");
        user.put("password","202cb962ac59075b964b07152d234b70");
        user.put("email","123@qq.com");
        HashSet<String> ssoAttr = new HashSet();
        ssoAttr.add("client1-enable");
        //ssoAttr.add("client2-enable");
        user.put("ssoAttr", ssoAttr);

        //未认证授权的服务会提前拦截
        // 获取Service信息
        RequestContext requestContext = org.springframework.webflow.execution.RequestContextHolder.getRequestContext();
        RegisteredService service = WebUtils.getRegisteredService(requestContext);
        //service为空为cas自己登录
        if (service != null) {
            System.out.println(service.getServiceId());
            System.out.println(service.getName());
        }
        //可以在这里直接对用户名校验,或者调用 CredentialsMatcher 校验
//        if (!user.get("password").equals(myCredential.getPassword())) {
//            throw new CredentialExpiredException("用户名或密码错误！");
//        }
/*        //这里将 密码对比 注销掉,否则 无法锁定  要将密码对比 交给 密码比较器 在这里可以添加自己的密码比较器等
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }*/
//        if ("1".equals(user.get("state"))) {
//            throw new AccountLockedException("账号已被锁定,请联系管理员！");
//        }
        return createHandlerResult(credential, this.principalFactory.createPrincipal(username,user));
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof CustomUsernamePasswordCaptchaCredential;
    }
}
