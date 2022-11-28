package com.doctorcom.bean;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义错误信息
 * 根据"等保评测"要求修改，在后端程序中应对用户提交的数据进行严格检查并限制或转义；对程序产生的异常错误进行敏感信息过滤处理再返回给客户端。
 */
public class CustomErrorAttributes implements ErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        this.addStatus(errorAttributes, requestAttributes);
        this.addPath(errorAttributes, requestAttributes);
        errorAttributes.put("Server", "Dr.COM Server");
        return errorAttributes;
    }

    @Override
    public Throwable getError(RequestAttributes requestAttributes) {
        return null;
    }

    private void addPath(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        try {
            String path = (String)requestAttributes.getAttribute("javax.servlet.error.request_uri", 0);
            errorAttributes.put("path", path);
        } catch (Exception ignored) {
        }
    }

    private void addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        try {
            Integer status = (Integer) requestAttributes.getAttribute("javax.servlet.error.status_code", 0);
            errorAttributes.put("status", status);
            try {
                errorAttributes.put("error", HttpStatus.valueOf(status).getReasonPhrase());
            } catch (Exception var5) {
                errorAttributes.put("error", "Http Status " + status);
            }
        } catch (Exception ignored) {
            errorAttributes.put("status", 999);
            errorAttributes.put("error", "None");
        }
    }
}
