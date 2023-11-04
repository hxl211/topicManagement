package com.hxl.boot.service;

import com.hxl.boot.utils.AjaxR;

public interface UserLoginService {
    AjaxR login(String username, String password, String identity);
}
