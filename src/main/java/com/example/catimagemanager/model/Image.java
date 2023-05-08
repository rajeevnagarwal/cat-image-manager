package com.example.catimagemanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiResponse
public class Image {
    @ApiParam
    private String id;
    @ApiParam
    private String name;
    @ApiParam
    private String description;
    @ApiParam
    private Date createdTimestamp;
    @ApiParam
    private Date updatedTimestamp;
    private String storageSystem;
    @ApiParam
    private ImageDetails imageDetails;
}
