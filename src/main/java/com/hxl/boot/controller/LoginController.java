package com.hxl.boot.controller;

import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.service.UserLoginService;
import com.hxl.boot.utils.AjaxR;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    UserLoginService userLoginService;
    @GetMapping("/registry")
    public AjaxR registry(){
        return null;
    }

    @PostMapping("/login/{identity}")
    public AjaxR login( @PathVariable String identity, String username , String password){
        if (identity==null) {
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }

        return userLoginService.login(username,password,identity);
    }

}
