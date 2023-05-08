package com.example.catimagemanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    private String statusCode;
    private String exceptionCode;
    private String statusMessage;
    private Object payload;
}
