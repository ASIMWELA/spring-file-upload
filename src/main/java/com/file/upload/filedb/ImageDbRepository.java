package com.file.upload.filedb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface ImageDbRepository extends RevisionRepository<ImageDb, Integer, Integer>, JpaRepository<ImageDb, Integer> {
    Optional<ImageDb> findByFileName(String fileName);
}
