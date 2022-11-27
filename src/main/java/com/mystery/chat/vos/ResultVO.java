package com.mystery.chat.vos;

/**
 * @author shouchen
 * @date 2022/11/25
 */
public class ResultVO<T> {
    private int status;
    private String type;
    private String error;
    private T result;

    private ResultVO() {
    }

    public static <T> ResultVO<T> of(T result) {
        return new ResultVO<T>().setResult(result).setStatus(200);
    }

    public static <T> ResultVO<T> empty() {
        return new ResultVO<T>().setStatus(200);
    }

    public static <T> ResultVO<T> error(String msg) {
        return new ResultVO<T>().setError(msg).setStatus(500);
    }

    public int getStatus() {
        return status;
    }

    public ResultVO<T> setStatus(int status) {
        this.status = status;
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
