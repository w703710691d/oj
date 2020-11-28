package com.swustacm.poweroj.common.exception;

import com.swustacm.poweroj.common.CodeEnum;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {
    private String msg;
    private int code;


    public CustomException(){
    }
    public CustomException(CodeEnum codeEnum){
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public CustomException(String msg ,int code){
        super(msg);
        this.code = code ;
        this.msg  = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}