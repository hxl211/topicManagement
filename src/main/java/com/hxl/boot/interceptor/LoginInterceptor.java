package com.hxl.boot.interceptor;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.utils.JwtUtil;
import com.hxl.boot.utils.UserThreadLocal;
import com.hxl.boot.vo.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (token==null)  {
           response.setStatus(401);
            return  false;
        }
        //判断是否已过期
        boolean after = JwtUtil.verifierToken(token);
        if(after){
            response.setStatus(401);
            return false;
        }

        return true;
    }



}
