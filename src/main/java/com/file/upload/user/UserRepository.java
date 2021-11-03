package com.file.upload.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RevisionRepository<User, Integer, Integer>, JpaRepository<User, Integer> {
}
