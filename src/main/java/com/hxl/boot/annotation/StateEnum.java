package com.hxl.boot.annotation;


public enum StateEnum {
    REFRESH(1000,"refresh"),
    PASSWORD_ERROR(1001,"密码错误！！！") ,
    USER_NOT_FOUND(1002,"用户不存在！！！"),
    USER_EXIST(1003,"用户已存在！！！"),
    RESULT_EMPTY(1004,"查询结果为空！！！"),
    RESULT_NOT_EMPTY(1005,"查询结果不为空！！！"),
    REQUIRE_NOT_NULL(1006,"数据不能为空！！！"),
    PASSWORD_ILLEGAL(1007,"密码不合法！！！"),
    EMAIL_ILLEGAL(1008,"邮箱不合法！！！"),
    USERNAME_ILLEGAL(1009,"用户名不合法！！！"),
    UNKNOWN_ERROR(10010,"未知错误！！！"),
    USER_NOT_LOGIN(1011,"用户未登录！！！"),
    PARAMETER_ERROR(1012,"参数错误！！！"),
    TOPIC_NOT_FOUND(1013,"课题不存在！！！"),
    ALREADY_ADD_TOPIC(1014,"已添加过课题！！！"),
    NOT_ADD_TOPIC(1014,"未添加过课题！！！"),
    AGAIN_ADD(1015,"请勿重复添加！！！");
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    StateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
