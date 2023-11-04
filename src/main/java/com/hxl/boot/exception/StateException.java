package com.hxl.boot.exception;

import com.hxl.boot.annotation.StateEnum;

public class StateException extends RuntimeException{

        private int code;
        private String msg;


        public StateException(StateEnum statusEnum) {

            super();
            this.code =statusEnum.getCode();
            this.msg = statusEnum.getMsg();
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

}
