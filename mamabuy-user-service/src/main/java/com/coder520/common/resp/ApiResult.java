package com.coder520.common.resp;

import com.coder520.common.constants.Constants;
import lombok.Data;

/**
 * Created by JackWangon[www.coder520.com] 2018/1/9.
 */
@Data
public class ApiResult<T> {

    private int code = Constants.RESP_STATUS_OK;

    private String message;

    private T data;

    public ApiResult() {
    }

    public ApiResult(String message) {
        this.message = message;
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
