package com.hxl.boot.utils;

import com.hxl.boot.vo.UserDTO;

public class UserThreadLocal {
    private static final ThreadLocal<UserDTO> threadLocal=new ThreadLocal<>();
    public static void setUser(UserDTO userDTO){
        if (userDTO!=null) {
            threadLocal.set(userDTO);
        }
    }
    public static UserDTO getUser(){
        return threadLocal.get();
    }
}
