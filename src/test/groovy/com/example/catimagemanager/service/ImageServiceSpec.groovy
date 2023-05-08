package com.example.catimagemanager.service

import com.example.catimagemanager.constant.Constant
import com.example.catimagemanager.exception.ServiceException
import com.example.catimagemanager.helper.ImageServiceHelper
import com.example.catimagemanager.model.Image
import com.example.catimagemanager.model.ImageDetails
import com.example.catimagemanager.model.ImageRequest
import com.example.catimagemanager.model.dto.ImageDTO
import com.example.catimagemanager.repository.ImageRepository
import com.example.catimagemanager.service.impl.ImageServiceImpl
import groovy.util.logging.Slf4j
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class ImageServiceSpec extends Specification {
    private static ImageService imageService
    private static ImageRepository imageRepository
    private static ImageServiceHelper imageServiceHelper
    private static ImageDTO imageDTO
    private static Image image

    def setup() {
        log.info("Inside setup")
        imageRepository = Mock(ImageRepository.class)
        imageServiceHelper = Spy(ImageServiceHelper.class)
        imageService = new ImageServiceImpl(imageRepository, imageServiceHelper)
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

    @Unroll
    def "Test getAllImages for empty list"() {
        given:
        imageRepository.findAll() >> mockedOutput
        expect:
        result == imageService.getAllImages()
        where:
        result          | mockedOutput
        new ArrayList() | new ArrayList()
        new ArrayList() | null
    }

    def "Test getAllImages for non empty list"() {
        when:
        imageDTO = new ImageDTO()
        imageDTO.setId(1L)
        imageDTO.setName("fileName")
        imageDTO.setOriginalFileName("fileName")

        image = Image.builder().id("1").name("fileName").imageDetails(ImageDetails.builder().originalFileName("fileName").build()).build()
        List<Image> images = new ArrayList<>();
        images.add(image)
        imageRepository.findAll() >> Arrays.asList(imageDTO)
        then:
        images.toString().equals(imageService.getAllImages().toString())
    }

    @Unroll
    def "Test getImage for invalid scenarios"() {
        when:
        Image image = imageService.getImage(input)
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.INVALID_REQUEST)
        where:
        input << [null, ""]
    }

    @Unroll
    def "Test getImage for not found scenarios"() {
        given:
        imageRepository.findById(Long.valueOf(input)) >> Optional.empty()
        when:
        Image image = imageService.getImage(input)
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.RESOURCE_NOT_FOUND)
        where:
        input << ["123", "1234"]
    }

    @Unroll
    def "Test getImage for happy scenarios"() {
        given:
        ImageDTO imageDTO = new ImageDTO()
        imageDTO.setId(Long.valueOf(input))
        imageDTO.setName(filename)
        imageRepository.findById(Long.valueOf(input)) >> Optional.ofNullable(imageDTO)
        expect:
        imageService.getImage(input).toString() == Image.builder().id(input).name(filename).imageDetails(ImageDetails.builder().build()).build().toString()
        where:
        input | filename
        "123" | "filename1"
        "456" | "filename2"
    }

    @Unroll
    def "Test deleteImage for invalid scenarios"() {
        when:
        Image image = imageService.deleteImage(input)
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.INVALID_REQUEST)
        where:
        input << [null, ""]
    }

    @Unroll
    def "Test deleteImage for not found scenarios"() {
        given:
        imageRepository.deleteById(Long.valueOf(input)) >> { throw new EmptyResultDataAccessException("Empty", 1) }
        when:
        Image image = imageService.deleteImage(input)
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.RESOURCE_NOT_FOUND)
        where:
        input << ["123", "1234"]
    }

    @Unroll
    def "Test deleteImage for happy scenarios"() {
        when:
        imageService.deleteImage(input)
        then:
        1 * imageRepository.deleteById(Long.valueOf(input))
        where:
        input << [
                "123",
                "456"]
    }

    @Unroll
    def "Test createImage for invalid scenarios"() {
        when:
        Image image = imageService.createImage(ImageRequest.builder().name(name).description(description).multipartFile(multipartFile).build())
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.INVALID_REQUEST)
        where:
        name   | description | multipartFile
        null   | null        | null
        " "    | " "         | null
        "name" | ""          | null
        "name" | "filename"  | null
    }


    @Unroll
    def "Test createImage for happy scenarios"() {
        given:
        ImageDTO imageDTO = new ImageDTO()
        imageDTO.setId(123)
        imageRepository.save(_ as ImageDTO) >> imageDTO
        ImageRequest imageRequest = ImageRequest.builder().name("name").description("description").multipartFile(new MockMultipartFile("name", "originalFileName", "png", new byte[2])).build()
        when:
        Image image = imageService.createImage(imageRequest)
        then:
        image.getId().equals("123")
        image.getName().equals("name")
        image.getDescription().equals("description")
        image.getImageDetails().getContentType().equals("png")
    }

    @Unroll
    def "Test updateImage for invalid scenarios"() {
        when:
        Image image = imageService.updateImage(id, ImageRequest.builder().name(name).description(description).multipartFile(multipartFile).build())
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.INVALID_REQUEST)
        where:
        id    | name   | description | multipartFile
        null  | null   | null        | null
        null  | " "    | " "         | null
        null  | "name" | ""          | null
        null  | "name" | "filename"  | null
        "123" | null   | null        | null
        "123" | " "    | " "         | null
        "123" | "name" | ""          | null
        "123" | "name" | "filename"  | null
    }

    @Unroll
    def "Test updateImage for not found scenarios"() {
        given:
        ImageRequest imageRequest = ImageRequest.builder().name("name").description("description").multipartFile(new MockMultipartFile("name", "originalFileName", "png", new byte[2])).build()
        imageRepository.findById(123) >> Optional.empty()
        when:
        Image image = imageService.updateImage("123", imageRequest)
        then:
        def exception = thrown(ServiceException)
        exception.getExceptionCode().equals(Constant.EXCEPTION_CODE.RESOURCE_NOT_FOUND)
    }

    @Unroll
    def "Test updateImage for happy scenarios"() {
        given:
        ImageDTO imageDTO = new ImageDTO()
        imageDTO.setId(123)
        imageDTO.setName("name1")
        imageDTO.setDescription("description1")
        ImageRequest imageRequest = ImageRequest.builder().name("name").description("description").multipartFile(new MockMultipartFile("name", "originalFileName", "png", new byte[2])).build()
        imageRepository.findById(123) >> Optional.ofNullable(imageDTO)
        when:
        Image image = imageService.updateImage("123",imageRequest)
        then:
        image.getId().equals("123")
        image.getName().equals("name")
        image.getDescription().equals("description")
        image.getImageDetails().getContentType().equals("png")
    }
}
