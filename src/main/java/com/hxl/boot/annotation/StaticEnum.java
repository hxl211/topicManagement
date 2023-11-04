package com.hxl.boot.annotation;

public enum StaticEnum {
    PAGE_SIZE(10),
    PASSWORD_REG("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,18}$"),
    EMAIL_REG("\\w+@\\w+(\\.\\w+)+"),
    TOKEN_EXPIRE_TIME(60 * 60 * 24),
    SECRET_KEY("FDGDNFY@1&^%$"),
    USER_IDENTITY("adm","user"),
    WEEKLY_REPORT_FILE_PATH("D:\\HTML\\后台管理系统\\file\\");


    private int valInt;
    private String valString1;

    private String valString2;
    StaticEnum(int valInt) {
        this.valInt = valInt;
    }

    StaticEnum(String valString1) {
        this.valString1 = valString1;
    }

    StaticEnum(String valString1, String valString2) {
        this.valString1 = valString1;
        this.valString2 = valString2;
    }

    public int getValInt() {
        return valInt;
    }

    public String getValString1() {
        return valString1;
    }
    public String getValString2() {
        return valString2;
    }
}
