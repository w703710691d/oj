package com.swustacm.poweroj.common;

/**
 * 返回码
 */
public enum CodeEnum {
    /*
    操作
     */
    SUCCESS(200, "请求成功"),
    REQUEST_EROOR(201, "请求方式错误"),

    /*
    系统
     */
    SYSTEM_ERROR(500, "系统故障！请联系管理员！"),
    SYSTEM_BUSY(501, "系统繁忙！"),

    /*
    认证
     */
    AUTHORIZATION_FAIL(2000, "没有权限"),
    AUTHENTICATION_FAIL(2001, "认证失败"),
    NO_LOGIN(2002, "请登录"),
    LOGIN_EXPIRE(2003, "登陆过期，请重新登陆"),
    USER_OR_PWD_ERROR(2004, "账号或密码错误"),
    USER_NOT_EXIST(2005, "用户不存在"),
    USER_FORBID(2006, "账号被禁用"),
    NO_PERMISSION(2007, "权限不足"),
    REPEAD_LOG_IN(2008,"同一账号，重复登陆"),

    /*
    文件
     */
    FILE_NOT_EXIST(2000, "文件不存在"),
    FILE_UPLOAD_FAIL(2001, "文件上传失败"),
    FILE_OUT_OF_LIMIT(2002, "文件超过限制"),
    FILE_TYPE_ERROR(2003, "文件类型错误"),

    /*
    参数
     */
    PARAM_ERROR(3000, "参数校验失败"),
    PARAM_MISS(3001, "参数不全"),
    PARAM_WAY_ERROR(3002, "传参方式错误"),
    PARAM_TYPE_ERROR(3003, "参数类型错误"),
    CODE_ERROR(3004, "验证码错误"),
    CODE_EXPIRE(3005, "验证码过期"),
    SEND_MAIL_ERROR(3006, "邮件发送失败"),

    OTHER_ERROR(777, "操作失败，请重试");

    int code;
    String msg;


    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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