package com.file.upload.filedb;

import com.file.upload.commons.ImageResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/imagesdb")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageDbController {
    ImageDbService imageDbService;
    public ImageDbController(ImageDbService imageDbService) {
        this.imageDbService = imageDbService;
    }
    @PostMapping
    public ResponseEntity<ImageResponse> saveImage(@RequestParam("image") MultipartFile file){
        return imageDbService.saveImage(file);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("fileName") String fileName){
        return imageDbService.downloadImage(fileName);
    }
}
