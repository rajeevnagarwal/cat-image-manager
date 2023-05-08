package com.example.catimagemanager.exception;

import com.example.catimagemanager.constant.Constant;
import com.example.catimagemanager.model.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceResponse> handleException(ServiceException serviceException) {
        ServiceResponse serviceResponse = ServiceResponse.builder().statusMessage(serviceException.getMessage()).exceptionCode(serviceException.getExceptionCode().name()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);

    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse> handleGenericException(Exception exception) {
        ServiceResponse serviceResponse = ServiceResponse.builder().statusMessage(exception.getMessage()).exceptionCode(Constant.EXCEPTION_CODE.INTERNAL_SERVER_ERROR.name()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serviceResponse);

    }

}
