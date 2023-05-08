package com.example.catimagemanager.service.impl;

import com.example.catimagemanager.constant.Constant;
import com.example.catimagemanager.exception.ServiceException;
import com.example.catimagemanager.helper.ImageServiceHelper;
import com.example.catimagemanager.model.Image;
import com.example.catimagemanager.model.ImageDetails;
import com.example.catimagemanager.model.ImageRequest;
import com.example.catimagemanager.model.dto.ImageDTO;
import com.example.catimagemanager.repository.ImageRepository;
import com.example.catimagemanager.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageServiceHelper imageServiceHelper;

    @Override
    public List<Image> getAllImages() throws ServiceException {
        log.info("Request received for fetching all images");
        List<ImageDTO> imageDTOs = imageRepository.findAll();
        return imageServiceHelper.convertToObject(imageDTOs);
    }

    @Override
    public Image getImage(String id) throws ServiceException {
        log.info("Request received for fetching image for id : {}",id);
        if (!StringUtils.hasLength(id))
            throw new ServiceException("ID cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        Optional<ImageDTO> imageDTOOptional = imageRepository.findById(Long.valueOf(id));
        if (!imageDTOOptional.isPresent())
            throw new ServiceException(String.format("Image with id : %s not found in our system", id), Constant.EXCEPTION_CODE.RESOURCE_NOT_FOUND);
        return imageServiceHelper.convertToObject(imageDTOOptional.get());
    }

    @Override
    public Image createImage(ImageRequest imageRequest) throws ServiceException {
        log.info("Request received for creating image with name : {}",imageRequest.getName());
        if (ObjectUtils.isEmpty(imageRequest))
            throw new ServiceException("Empty payload", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        if (!StringUtils.hasLength(imageRequest.getName()))
            throw new ServiceException("Name cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        if (ObjectUtils.isEmpty(imageRequest.getMultipartFile()))
            throw new ServiceException("File cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        try {
            Image image = getImageFromRequest(imageRequest);
            ImageDTO imageDTO = imageRepository.save(imageServiceHelper.convertToDTO(image));
            image.setId(String.valueOf(imageDTO.getId()));
            image.setCreatedTimestamp(imageDTO.getCreatedDate());
            image.setUpdatedTimestamp(imageDTO.getUpdatedDate());
            return image;
        } catch (IOException e) {
            log.error("Exception while converting to string : {}", e);
            throw new ServiceException("Invalid file", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        }
    }

    @Override
    public Image updateImage(String id, ImageRequest imageRequest) throws ServiceException {
        log.info("Request received for updating image with id : {}",id);
        if (!StringUtils.hasLength(id))
            throw new ServiceException("ID cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        if (ObjectUtils.isEmpty(imageRequest))
            throw new ServiceException("Empty payload", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        if (!StringUtils.hasLength(imageRequest.getName()))
            throw new ServiceException("Name cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        if (ObjectUtils.isEmpty(imageRequest.getMultipartFile()))
            throw new ServiceException("File cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        Optional<ImageDTO> imageDTOOptional = imageRepository.findById(Long.valueOf(id));
        if (!imageDTOOptional.isPresent())
            throw new ServiceException(String.format("Image with id : %s not found in our system", id), Constant.EXCEPTION_CODE.RESOURCE_NOT_FOUND);
        Image image = imageServiceHelper.convertToObject(imageDTOOptional.get());
        try {
            Image newImage = getImageFromRequest(imageRequest);
            newImage.setId(image.getId());
            newImage.setUpdatedTimestamp(image.getUpdatedTimestamp());
            newImage.setCreatedTimestamp(image.getCreatedTimestamp());
            imageRepository.save(imageServiceHelper.convertToDTO(newImage));
            return newImage;
        } catch (IOException e) {
            log.error("Exception while converting to string : {}", e);
            throw new ServiceException("Invalid file", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        }
    }

    @Override
    public void deleteImage(String id) throws ServiceException {
        log.info("Request received for deleting image with id : {}",id);
        if (!StringUtils.hasLength(id))
            throw new ServiceException("ID cannot be empty", Constant.EXCEPTION_CODE.INVALID_REQUEST);
        try {
            imageRepository.deleteById(Long.parseLong(id));
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException(String.format("Image with id : %s not found in our system", id),Constant.EXCEPTION_CODE.RESOURCE_NOT_FOUND);
        } catch (Exception e) {
            log.error("Exception while deleting : {}",e);
            throw new ServiceException(String.format("Error while deleting image with id : %s", id),Constant.EXCEPTION_CODE.INVALID_REQUEST);
        }
    }

    private Image getImageFromRequest(ImageRequest imageRequest) throws IOException {
        return Image.builder().name(imageRequest.getName()).description(imageRequest.getDescription()).imageDetails(ImageDetails.builder()
                .imagePayload(new String(Base64.getEncoder().encode(imageRequest.getMultipartFile().getBytes())))
                .contentType(imageRequest.getMultipartFile().getContentType())
                .size(String.valueOf(imageRequest.getMultipartFile().getSize()))
                .originalFileName(imageRequest.getMultipartFile().getOriginalFilename())
                .build()).build();
    }

}
