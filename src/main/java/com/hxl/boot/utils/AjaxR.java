package com.hxl.boot.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxR {
    private int code; //状态码
    private String msg; //错误信息
    private Object data; //数据
    //单个对象
    public static AjaxR success(Object o){
        return new AjaxR(200,"success",o);
    }
    //数组的时候 与前者不可重合
    public static AjaxR success(Object ...o){
        return new AjaxR(200,"success",o);
    }

    public static AjaxR success(int code,Object o){
        return new AjaxR(code,"success",o);
    }
    public static AjaxR success(){
        return new AjaxR(200,"success",null);
    }
    public static AjaxR error(int code, String msg){
        return new AjaxR(code,msg,null);
    }
}
