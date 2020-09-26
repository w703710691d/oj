package com.swustacm.poweroj.common;

/**
 * 统一返回
 * @author xingzi
 * @param <T>
 */
public class CommonResult<T> {

    protected CommonResult() {
    }

    private CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private CommonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<T>(200, "操作成功", data);
    }

    public static <T> CommonResult<T> ok() {
        return new CommonResult<T>(200, "操作成功");
    }

    public static <T> CommonResult<T> error() {
        return new CommonResult<T>(500, "操作失败");
    }


    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<T>(600, msg);
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<T>(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
