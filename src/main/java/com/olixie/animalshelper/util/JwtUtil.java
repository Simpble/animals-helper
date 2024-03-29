package com.olixie.animalshelper.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    /*jwt相关工具类，提供创建token及获取token等相关工作*/
    private static String secretKey = "ningshijie";
    /**
     * 加密token.
     */
    public static String getTokenOfUid(Integer uid) {
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.

        return JWT
                .create()
                .withClaim("uid" ,uid)
                .sign(Algorithm.HMAC256(secretKey));
    }
    /**
     * 解析token.
     * {
     * "userId": "weizhong",
     * "userRole": "ROLE_ADMIN",
     * "timeStamp": "134143214"
     * }
     */
    public static Map<String, Integer> parseToken(String token) {

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        Claim uid = decodedjwt.getClaim("uid");
        map.put("uid", uid.asInt());
        return map;
    }

    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        //默认7天过期
        instance.add(Calendar.DATE,7);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //将map中的数据写至载荷中
        map.forEach(builder::withClaim);
        //使用jwt相关构建器构建token,此时得到了一个Token
        return builder.withExpiresAt(instance.getTime())//有效期
                .sign(Algorithm.HMAC256(secretKey));
    }
    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token) {
        //返回验证结果（结果是内置的）
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
    }
}
