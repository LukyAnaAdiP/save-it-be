package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    Optional<Image> findByFileId(String fileId);
}
