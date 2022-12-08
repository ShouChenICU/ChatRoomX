package com.mystery.chat.vos;

/**
 * @author shouchen
 * @date 2022/11/25
 */
public class ResultVO<T> {
    private static final int OK = 200;
    private static final int ERR = 500;
    private int code;
    private String type;
    private String error;
    private T result;

    private ResultVO() {
    }

    public static <T> ResultVO<T> of(T result) {
        return new ResultVO<T>().setResult(result).setCode(OK);
    }

    public static ResultVO<String> success() {
        return ResultVO.of("success");
    }

    public static <T> ResultVO<T> error(String msg) {
        return new ResultVO<T>().setError(msg).setCode(ERR);
    }

    public int getCode() {
        return code;
    }

    public ResultVO<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public ResultVO<T> setType(String type) {
        this.type = type;
        return this;
    }

    public String getError() {
        return error;
    }

    public ResultVO<T> setError(String error) {
        this.error = error;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ResultVO<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
