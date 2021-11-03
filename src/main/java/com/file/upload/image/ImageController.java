package com.file.upload.image;

import com.file.upload.commons.ImageResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {

    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable("id") int userId, @RequestParam("image") MultipartFile file, HttpServletRequest request){
        return imageService.upload(userId, file, request);
    }

    @GetMapping("/{id}/images/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("id") int userId, @PathVariable("fileName") String fileName, HttpServletRequest request){
        return imageService.downloadImage(userId, fileName, request);
    }

    @DeleteMapping("/{id}/images/{fileName}")
    public ResponseEntity.HeadersBuilder deleteImage(@PathVariable("id") int userId, @PathVariable("fileName") String fileName){
        return imageService.deleteImage(userId, fileName);
    }
}
