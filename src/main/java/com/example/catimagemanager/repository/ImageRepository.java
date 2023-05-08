package com.example.catimagemanager.repository;

import com.example.catimagemanager.model.dto.ImageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageDTO,Long> {
}
