package com.example.catimagemanager.helper;

import com.example.catimagemanager.model.Image;
import com.example.catimagemanager.model.ImageDetails;
import com.example.catimagemanager.model.dto.ImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ImageServiceHelper {

    public ImageDTO convertToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setName(image.getName());
        imageDTO.setDescription(image.getDescription());
        imageDTO.setCreatedDate(image.getCreatedTimestamp());
        imageDTO.setUpdatedDate(image.getUpdatedTimestamp());
        imageDTO.setId(Optional.ofNullable(image.getId()).map(s -> Long.parseLong(image.getId())).orElse(null));
        if (!ObjectUtils.isEmpty(image.getImageDetails())) {
            imageDTO.setImagePayload(image.getImageDetails().getImagePayload());
            imageDTO.setContentType(image.getImageDetails().getContentType());
            imageDTO.setSize(Optional.ofNullable(image.getImageDetails().getSize()).map(s -> Long.parseLong(image.getImageDetails().getSize())).orElse(null));
            imageDTO.setOriginalFileName(image.getImageDetails().getOriginalFileName());
        }
        return imageDTO;
    }

    public List<ImageDTO> convertToDTO(List<Image> images) {
        return Optional.ofNullable(images).map(imgs -> imgs.stream().map(this::convertToDTO).collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    public Image convertToObject(ImageDTO imageDTO) {
        return Image.builder()
                .id(String.valueOf(imageDTO.getId()))
                .name(imageDTO.getName())
                .description(imageDTO.getDescription())
                .createdTimestamp(imageDTO.getCreatedDate())
                .updatedTimestamp(imageDTO.getUpdatedDate())
                .imageDetails(ImageDetails.builder()
                        .originalFileName(imageDTO.getOriginalFileName())
                        .imagePayload(imageDTO.getImagePayload())
                        .size(String.valueOf(imageDTO.getSize()))
                        .contentType(imageDTO.getContentType())
                        .build())
                .build();
    }

    public List<Image> convertToObject(List<ImageDTO> imageDTOs) {
        return Optional.ofNullable(imageDTOs).map(imgs -> imgs.stream().map(this::convertToObject).collect(Collectors.toList())).orElse(new ArrayList<>());

    }


}
