package com.example.catimagemanager.controller

import com.example.catimagemanager.model.Image
import com.example.catimagemanager.model.ImageRequest
import com.example.catimagemanager.model.ServiceResponse
import com.example.catimagemanager.service.ImageService
import com.example.catimagemanager.service.impl.ImageServiceImpl
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

@Slf4j
class ImageControllerSpec extends Specification {
    private static ImageService imageService
    private static ImageController imageController

    def setup() {
        log.info("Inside setup")
        imageService = Mock(ImageServiceImpl.class)
        imageController = new ImageController(imageService)
    }

    def setupSpec() {
        log.info("Inside setupSpec")

    }

    def cleanupSpec() {
        log.info("Inside cleanupSpec")

    }

    def cleanup() {
        log.info("Inside cleanup")

    }

    def "Testing getAllImages"() {
        given:
        imageService.getAllImages() >> new ArrayList<Image>()
        when:
        ResponseEntity<ServiceResponse> responseEntity = imageController.getAllImages()
        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        responseEntity.getBody().getPayload() == []
    }

    def "Testing getImage"() {
        given:
        imageService.getImage("123") >> Image.builder().build()
        when:
        ResponseEntity<ServiceResponse> responseEntity = imageController.getImage("123")
        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        responseEntity.getBody().getPayload() == Image.builder().build()
    }

    def "Testing deleteImage"() {
        when:
        ResponseEntity<ServiceResponse> responseEntity = imageController.deleteImage("123")
        then:
        responseEntity.statusCode == HttpStatus.ACCEPTED
        1 * (imageService.deleteImage("123"))

    }

    def "Testing createImage"() {
        when:
        ResponseEntity<ServiceResponse> responseEntity = imageController.createImage("description", "name", new MockMultipartFile("name", new byte[2]))
        then:
        responseEntity.statusCode == HttpStatus.ACCEPTED
        1 * imageService.createImage(_ as ImageRequest)
    }

    def "Testing updateImage"() {
        when:
        ResponseEntity<ServiceResponse> responseEntity = imageController.updateImage("123", "description", "name", new MockMultipartFile("name", new byte[2]))
        then:
        responseEntity.statusCode == HttpStatus.ACCEPTED
        1 * imageService.updateImage("123", _ as ImageRequest)
    }
}
