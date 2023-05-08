package com.example.catimagemanager.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder

public class ImageRequest {
    private String name;
    private String description;
    private MultipartFile multipartFile;
}
