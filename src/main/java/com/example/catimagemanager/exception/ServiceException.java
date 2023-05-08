package com.example.catimagemanager.exception;

import com.example.catimagemanager.constant.Constant;

public class ServiceException extends Exception {
    private String message;
    private Constant.EXCEPTION_CODE exceptionCode;

    public ServiceException(String message, Constant.EXCEPTION_CODE exceptionCode) {
        super(message);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public Constant.EXCEPTION_CODE getExceptionCode() {
        return this.exceptionCode;
    }
}
