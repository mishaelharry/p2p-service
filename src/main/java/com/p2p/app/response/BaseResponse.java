package com.p2p.app.response;

import lombok.Data;

@Data
public class BaseResponse <T>{

    private boolean status;

    private String message;

    private T data;

    public BaseResponse() {
    }

    public BaseResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
