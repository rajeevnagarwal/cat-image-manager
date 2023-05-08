package com.example.catimagemanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDetails {
    private String s3Link;
    private String imagePayload;
    private String size;
    private String originalFileName;
    private String contentType;

}
