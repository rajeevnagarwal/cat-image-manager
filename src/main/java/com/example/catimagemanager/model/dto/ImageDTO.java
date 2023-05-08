package com.example.catimagemanager.model.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "image")
@Data
public class ImageDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image_payload")
    private String imagePayload;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;

    @PrePersist
    private void prePersist() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedDate = new Date();
    }

}


