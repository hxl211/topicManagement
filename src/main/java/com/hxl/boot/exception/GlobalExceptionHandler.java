package com.hxl.boot.exception;
import com.hxl.boot.utils.AjaxR;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice

public class GlobalExceptionHandler {
    //自定义状态码的异常
    @ExceptionHandler({StateException.class})
    public AjaxR exceptionHandler(StateException se){

        return AjaxR.error(se.getCode(),se.getMsg());
    }

    //真正的服务器异常
    @ExceptionHandler({RuntimeException.class})
    public void exceptionHandler(HttpServletResponse response){
        response.setStatus(500);
    }
}
