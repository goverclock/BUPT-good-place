package org.example.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.units.webJwt;
import javax.swing.*;

@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 在这里处理OPTIONS请求，可以根据需要进行逻辑处理
        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("---OPTIONS------------");
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }


        String jwt=request.getHeader("token");

        if(!StringUtils.hasLength(jwt)){
            return false;
        }
        try{
            Claims claims=webJwt.parserJwt(jwt);
            System.out.println(StringUtils.hasLength(jwt));
        }
        catch (Exception e){
            return false;
        }
        response.addHeader("token",jwt);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
