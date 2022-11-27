package com.mystery.chat.exceptions;

import com.mystery.chat.vos.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shouchen
 * @date 2022/11/25
 */
@RestControllerAdvice
public class SystemExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResultVO<?> businessExceptionHandle(BusinessException exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception);
        return ResultVO.error(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResultVO<?> accessDeniedExceptionHandle(AccessDeniedException exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception.getMessage());
        return ResultVO.error(HttpStatus.FORBIDDEN.toString());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void httpRequestMethodNotSupportedExceptionHandle(HttpServletRequest request) {
        LOGGER.warn("Not support " + request.getMethod() + " " + request.getRequestURI());
    }

    @ExceptionHandler(RuntimeException.class)
    public void runtimeExceptionHandle(RuntimeException exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception);
    }
}
