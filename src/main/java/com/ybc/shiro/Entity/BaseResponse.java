package com.ybc.shiro.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

    private T data;
    private int status = 200;
    private String message;
    private long srvTime = System.currentTimeMillis();

    public BaseResponse(String message) {
        this.message = message;
    }

    public BaseResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public BaseResponse<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public BaseResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>("操作成功");
    }


    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<T>("操作成功").setData(data);
    }

    public static <T> BaseResponse<T> wrong(T data, Integer status, String message) {
        return new BaseResponse<T>("操作失败").setData(data).setStatus(status).setMessage(message);
    }

}

