package com.example.catimagemanager.constant;

public class Constant {
    public static final String IMAGE_FETCHED_SUCCESSFULLY = "Image fetched successfully";
    public static final String IMAGES_FETCHED_SUCCESSFULLY = "Images fetched successfully";
    public static final String IMAGE_CREATED_SUCCESSFULLY = "Image created successfully";
    public static final String IMAGE_UPDATED_SUCCESSFULLY = "Image updated successfully";
    public static final String IMAGE_DELETED_SUCCESSFULLY = "Image deleted successfully";


    public enum EXCEPTION_CODE {
        RESOURCE_NOT_FOUND,
        INVALID_REQUEST,
        INTERNAL_SERVER_ERROR,
        DOWNSTREAM_ERROR,
        AUTHENTICATION_ERROR,
        AUTHORIZATION_ERROR
    }
}
