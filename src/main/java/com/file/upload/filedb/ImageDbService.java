package com.file.upload.filedb;

import com.file.upload.commons.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


public interface ImageDbService {
    ResponseEntity<ImageResponse> saveImage(MultipartFile file);
    ResponseEntity<byte[]> downloadImage(String fileName);
}
