package com.mystery.chat.exceptions;

import com.mystery.chat.vos.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResultVO<?> exceptionHandle(BusinessException exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception);
        return ResultVO.error(exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void exceptionHandle(HttpServletRequest request) {
        LOGGER.warn("Not support " + request.getMethod() + " " + request.getRequestURI());
    }

    @ExceptionHandler(RuntimeException.class)
    public void exceptionHandle(RuntimeException exception) {
        LOGGER.warn(exception.getClass().getSimpleName(), exception);
    }
}
