package com.ybc.shiro.Configration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybc.shiro.Entity.BaseResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@org.springframework.web.bind.annotation.ControllerAdvice(basePackages = "com.ybc.shiro.Controller")
public class ControllerAdvice implements ResponseBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = AuthorizationException.class)
    public String handleAuthorizationException() {
        return "403";
    }


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        logger.info("拦截成功");
        ObjectMapper objectMapper = new ObjectMapper();
        if (body instanceof String){
            try{
                return objectMapper.writeValueAsString(BaseResponse.ok(body));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        logger.info("body not instanceof String");
        if(body instanceof BaseResponse){
            return body;
        }else if(body == null){
            return BaseResponse.ok();
        }else {
            return BaseResponse.ok(body);
        }

    }
}
