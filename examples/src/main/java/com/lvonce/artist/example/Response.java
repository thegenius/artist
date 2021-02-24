package com.lvonce.artist;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Response<T> {
    boolean success;
    int errorCode;   // business error and program error(500)
    String errorMsg;
    T data;

    private Response(boolean success, int errorCode, String errorMsg, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> Response<T> of(T data) {
        return new Response<>(true, 0, "", data);
    }

    public static <T> Response<T> ofError(int errorCode, String errorMsg) {
        return new Response<>(false, errorCode, errorMsg, null);
    }

}
