package com.swustacm.poweroj.common.exception;

import com.swustacm.poweroj.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author xingzi
 * 自定义全局异常
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public GlobalExceptionHandler() {
        super();
    }

    /**
     * 404 异常
     *
     * @param ex      异常信息
     * @param body    请求体
     * @param headers 头
     * @param status  状态码
     * @param request 请求
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        return new ResponseEntity<>(CommonResult.error(status.value(),ex.getMessage()), headers, status);
    }

    /**
     * 参数异常
     *
     * @param ex      异常信息
     * @param headers 头
     * @param status  状态码
     * @param request 请求
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(CommonResult.error(buildParamsException(ex)), headers, status);
    }

    /**
     * 全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult<String> globalException(Exception e) {
        log.error("异常信息为：{}", e.getMessage());
        e.printStackTrace();
        return CommonResult.error(e.getMessage());
    }

    /**
     * 获取参数错误列表
     *
     * @param exception 异常
     * @return
     */
    private static String buildParamsException(MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            sb.append(error.getField())
                    .append(" : ")
                    .append(error.getDefaultMessage() == null ? "参数异常" : error.getDefaultMessage())
                    .append("\n");
        }
        return sb.toString();
    }
}
