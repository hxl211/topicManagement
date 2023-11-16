package com.hxl.boot.interceptor;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.utils.JwtUtil;
import com.hxl.boot.utils.ThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginInterceptor implements HandlerInterceptor {


        @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {

        // 获取请求头中的token和refreshToken
        String accessToken = request.getHeader("token");
        String refreshToken=request.getHeader("refreshToken");

        // 如果refreshToken不合法或者已过期，返回401 Unauthorized
        if (!Boolean.TRUE.equals(JwtUtil.validToken(refreshToken))||JwtUtil.validToken(accessToken)==null)  {
            response.setStatus(401);
            return false;
        }

        // 判断accessToken是否有效，如果过期则刷新accessToken和refreshToken
        if (JwtUtil.validToken(accessToken)==false){
            // 刷新accessToken和refreshToken
            String newAccessToken = JwtUtil.createToken(StaticEnum.ACCESS_TOKEN_EXPIRE_TIME.getValInt(), JwtUtil.getUserDTO(refreshToken));
            String newRefreshToken=JwtUtil.createToken(StaticEnum.REFRESH_TOKEN_EXPIRE_TIME.getValInt(),JwtUtil.getUserDTO(refreshToken));
            // 返回json格式的新accessToken和refreshToken
            response.addHeader("accessToken",newAccessToken);
            response.addHeader("refreshToken",newRefreshToken);
        }

        // 将refreshToken存储到线程本地变量中
        ThreadLocalUtil.setUser(JwtUtil.getUserDTO(refreshToken));

        // 返回true表示处理继续
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.removeUser();

    }
}
