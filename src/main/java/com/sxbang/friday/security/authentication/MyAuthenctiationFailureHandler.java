package com.sxbang.friday.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author kaneki
 */
@Component
@Slf4j
public class MyAuthenctiationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登陆失败");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> map = new HashMap<>();
        if (exception instanceof LockedException) {
            map.put("message", "账户被锁定，登录失败!");
        } else if (exception instanceof BadCredentialsException) {
            map.put("message", "账户名或密码输入错误，登录失败!");
        } else if (exception instanceof DisabledException) {
            map.put("message", "账户被禁用，登录失败!");
        } else if (exception instanceof AccountExpiredException) {
            map.put("message", "账户已过期，登录失败!");
        } else if (exception instanceof CredentialsExpiredException) {
            map.put("message", "密码已过期，登录失败!");
        } else if (exception instanceof InternalAuthenticationServiceException) {
            map.put("message", "用户不存在!");
        } else {
            map.put("message", "登录失败!");
        }
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
