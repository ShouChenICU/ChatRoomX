package com.mystery.chat.exceptions;

import com.mystery.chat.vos.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统异常处理
 *
 * @author shouchen
 * @date 2022/11/25
 */
@RestControllerAdvice
public class SystemExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemExceptionHandler.class);

    /**
     * 业务异常处理
     *
     * @param exception 异常
     * @return 响应体
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVO<?> businessExceptionHandle(BusinessException exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception);
        return ResultVO.error(exception.getMessage());
    }

    /**
     * 无权访问处理
     *
     * @param exception 异常
     * @param request   请求
     * @return 响应体
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResultVO<?> accessDeniedExceptionHandle(AccessDeniedException exception, HttpServletRequest request) {
        LOGGER.warn(exception.getClass().getSimpleName() + " {} {} UID {}",
                request.getMethod(),
                request.getRequestURI(),
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResultVO.error(HttpStatus.FORBIDDEN.toString()).setCode(HttpStatus.FORBIDDEN.value());
    }

    /**
     * 不允许的请求方法
     *
     * @param request 请求
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void httpRequestMethodNotSupportedExceptionHandle(HttpServletRequest request) {
        LOGGER.warn("Not support " + request.getMethod() + " " + request.getRequestURI());
    }

    /**
     * 其他异常处理
     *
     * @param exception 异常
     * @return 响应体
     */
    @ExceptionHandler(Exception.class)
    public ResultVO<?> exceptionHandle(Exception exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception);
        return ResultVO.error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
