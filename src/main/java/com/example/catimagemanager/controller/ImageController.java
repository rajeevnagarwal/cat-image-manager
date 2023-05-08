package com.example.catimagemanager.controller;

import com.example.catimagemanager.exception.ServiceException;
import com.example.catimagemanager.model.ImageRequest;
import com.example.catimagemanager.model.ServiceResponse;
import com.example.catimagemanager.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.catimagemanager.constant.Constant.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/image-manager/v1")
public class ImageController {

    private final ImageService imageService;

    @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ServiceResponse> getImage(@PathVariable("id") String id) throws ServiceException {
        ServiceResponse serviceResponse = ServiceResponse.builder().statusCode(HttpStatus.OK.toString()).statusMessage(IMAGE_FETCHED_SUCCESSFULLY).payload(imageService.getImage(id)).build();
        return ResponseEntity.ok(serviceResponse);
    }

    @RequestMapping(value = "/images", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ServiceResponse> getAllImages() throws ServiceException {
        ServiceResponse serviceResponse = ServiceResponse.builder().statusCode(HttpStatus.OK.toString()).statusMessage(IMAGES_FETCHED_SUCCESSFULLY).payload(imageService.getAllImages()).build();
        return ResponseEntity.ok(serviceResponse);
    }

    @RequestMapping(value = "/images", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ServiceResponse> createImage(@RequestPart(value = "description",required = false) String description,@RequestPart("name") String name, @RequestPart("file") MultipartFile multipartFile) throws ServiceException {
        ServiceResponse serviceResponse = ServiceResponse.builder().statusCode(HttpStatus.ACCEPTED.toString()).statusMessage(IMAGE_CREATED_SUCCESSFULLY).payload(imageService.createImage(ImageRequest.builder().description(description).name(name).multipartFile(multipartFile).build())).build();
        return ResponseEntity.accepted().body(serviceResponse);
    }

    @RequestMapping(value = "/images/{id}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ServiceResponse> updateImage(@PathVariable("id") String id, @RequestPart(value = "description",required = false) String description,@RequestPart("name") String name, @RequestPart("file") MultipartFile multipartFile) throws ServiceException {
        ServiceResponse serviceResponse = ServiceResponse.builder().statusCode(HttpStatus.ACCEPTED.toString()).statusMessage(IMAGE_UPDATED_SUCCESSFULLY).payload(imageService.updateImage(id, ImageRequest.builder().description(description).name(name).multipartFile(multipartFile).build())).build();
        return ResponseEntity.accepted().body(serviceResponse);
    }

    @RequestMapping(value = "images/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ServiceResponse> deleteImage(@PathVariable("id") String id) throws ServiceException {
        imageService.deleteImage(id);
        ServiceResponse serviceResponse = ServiceResponse.builder().statusCode(HttpStatus.ACCEPTED.toString()).statusMessage(IMAGE_DELETED_SUCCESSFULLY).build();
        return ResponseEntity.accepted().body(serviceResponse);
    }
}
