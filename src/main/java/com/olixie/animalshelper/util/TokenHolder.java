package com.olixie.animalshelper.util;


import com.olixie.animalshelper.entity.User;

public class TokenHolder {


    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void saveToken(String token) {
        tl.set(token);
    }

    public static String getToken() {
        return tl.get();
    }

    public static Integer parseToken() {
        return JwtUtil.parseToken(tl.get()).get("uid");
    }

    public static void removeToken() {
        tl.remove();
    }
}