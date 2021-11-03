package com.file.upload.image;

import com.file.upload.commons.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ImageService {
    ResponseEntity<ImageResponse> upload(int userId, MultipartFile imageFile, HttpServletRequest request);
    ResponseEntity.HeadersBuilder deleteImage(int userId, String imageName);
    ResponseEntity<ImageResponse> uploadImages(int userId, List<MultipartFile> files);
    ResponseEntity<Resource> downloadImage(int userId, String imageName, HttpServletRequest request);
}
