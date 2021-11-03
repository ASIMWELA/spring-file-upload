package com.file.upload.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends RevisionRepository<Image, Integer, Integer>,JpaRepository<Image, Integer> {
    Optional<Image> findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);

}
