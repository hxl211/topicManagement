package com.hxl.boot.utils;


import com.hxl.boot.vo.UserDTO;

public class ThreadLocalUtil {

    private static final ThreadLocal<UserDTO> USER_DTO_THREAD_LOCAL = new ThreadLocal<>();

    public static UserDTO getUser(){
        return  USER_DTO_THREAD_LOCAL.get();
    }
    public static void setUser(UserDTO user){
        USER_DTO_THREAD_LOCAL.set(user);
    }

    public static void removeUser(){
        USER_DTO_THREAD_LOCAL.remove();
    }



}
