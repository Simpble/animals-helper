package com.olixie.animalshelper.jwt;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olixie.animalshelper.util.TokenHolder;
import com.olixie.animalshelper.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class jwtInterceptor implements HandlerInterceptor {

    //在当前拦截器中需要验证是否已经登录，若已经则登录放行.需要token存放至ThreadLocal中
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("url: {}", request.getRequestURL());
        String token = request.getHeader("Authorization");
        Map<String, Object> map = new HashMap<>();
        log.info("请求头中的token是:{}", token);
        try {
            if (token == null) {
                throw new Exception();
            }
            //验证令牌
            JwtUtil.verify(token);
            //验证令牌后将令牌保存至ThreadLocal中
            TokenHolder.saveToken(token);
            return true;
        } catch (SignatureVerificationException e) {
            map.put("msg", "无效签名");
        } catch (TokenExpiredException e) {
            map.put("msg", "token过期");
        } catch (AlgorithmMismatchException e) {
            map.put("msg", "token算法不一致");
        } catch (Exception e) {
            map.put("msg", "token无效");
        }
        map.put("state", "false");
        //将map转为json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

        return false;
    }

    @Override
    //在请求完成之后完成ThreadLocal的删除操作。
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenHolder.removeToken();
    }
}
