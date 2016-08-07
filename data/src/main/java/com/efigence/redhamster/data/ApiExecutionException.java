package com.efigence.redhamster.data;

public class ApiExecutionException extends RuntimeException {

    private final int code;

    public ApiExecutionException(String detailMessage, int code) {
        super(code + ": " + detailMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
