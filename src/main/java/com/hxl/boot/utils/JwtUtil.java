package com.hxl.boot.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.pojo.Teacher;
import com.hxl.boot.vo.UserDTO;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
   private static final String SECRET=StaticEnum.SECRET_KEY.getValString1();
    //验证对象，参数为密钥
    private static final JWTVerifier JWT_VERIFIER = JWT.require(Algorithm.HMAC256(SECRET)).build();

    /**
     * 根据过期时间生产token ，保存用户信息
     * @param expireTime 过期时间，单位 s
     * @param map    用户信息
     * @return  token
     */
    public static String createToken(long expireTime, Map<String ,String> map){

        //设置失效时间 当前时间毫秒+10*1000毫秒（也就是十秒后）
        Date expireDate=new Date(System.currentTimeMillis()+1000*expireTime);

        String token=  JWT.create()    //创建JWT

                .withClaim("userId",map.get("userId"))
                .withClaim("identity",map.get("identity"))
                .withExpiresAt(expireDate)  //设置失效时间
                .sign(Algorithm.HMAC256(SECRET));  //设置签名  中间为 自己设置的 密钥
       return token;
    }

    /**
     * 根据token和当前时间判断是否过期
     * @param token  token
     * @return 是否过期 true为已过期 ，false为没过期
     */
    public static boolean verifierToken (String token){
        if (token==null) return true;
        //通过这个对象的verify 传入 token字符串进行解密
        try {
             JWT_VERIFIER.verify(token);
            return false;
        } catch (TokenExpiredException e){
                return true;
        }
    }

    /**
     * 根据token 获取当前用户id ，为空说明已过期
     * @param token token
     * @return 用户Id
     */
    public static Integer getUserId(String token){
        if (token==null) return null;
        boolean  expired= verifierToken(token);
        if(expired) return null;
        //
        Integer userId = Integer.parseInt(JWT_VERIFIER.verify(token).getClaim("userId").asString());
      return  userId;
    }

    /**
     * 获取身份 可能为空
     * @param token token
     * @return String
     */
    public static String getIdentity(String token){
        if (token==null) return null;
        boolean  expired= verifierToken(token);
        if(expired) return null;

        String identity = JWT_VERIFIER.verify(token).getClaim("identity").asString();
        return  identity;
    }

    /**
     * 通过token 获取UserDTO
     * @param token token
     * @return UserDTO
     */
    public static UserDTO getUserDTO(String token){
        if (token==null) return null;
        boolean  expired= verifierToken(token);
        if(expired) return null;
        //
        Integer userId = Integer.parseInt(JWT_VERIFIER.verify(token).getClaim("userId").asString());
        String identity = JWT_VERIFIER.verify(token).getClaim("identity").asString();
        if (StaticEnum.USER_IDENTITY.getValString1().equals(identity)) {
            return  new UserDTO(userId,StaticEnum.USER_IDENTITY.getValString1());
        }
        return  new UserDTO(userId,StaticEnum.USER_IDENTITY.getValString2());

    }

}
