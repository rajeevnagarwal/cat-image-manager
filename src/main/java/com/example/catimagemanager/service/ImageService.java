package com.example.catimagemanager.service;

import com.example.catimagemanager.exception.ServiceException;
import com.example.catimagemanager.model.Image;
import com.example.catimagemanager.model.ImageRequest;

import java.util.List;

public interface ImageService {
    List<Image> getAllImages() throws ServiceException;
    Image getImage(String id) throws ServiceException;
    Image createImage(ImageRequest imageRequest) throws ServiceException;
    Image updateImage(String id,ImageRequest imageRequest) throws ServiceException;
    void deleteImage(String id) throws ServiceException;

}
