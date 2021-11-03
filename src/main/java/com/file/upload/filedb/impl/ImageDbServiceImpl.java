package com.file.upload.filedb.impl;

import com.file.upload.commons.ImageResponse;
import com.file.upload.filedb.ImageDb;
import com.file.upload.filedb.ImageDbController;
import com.file.upload.filedb.ImageDbRepository;
import com.file.upload.filedb.ImageDbService;
import com.file.upload.image.Image;
import com.file.upload.image.ImageController;
import com.file.upload.user.User;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageDbServiceImpl implements ImageDbService {

    ImageDbRepository imageDbRepository;

    public ImageDbServiceImpl(ImageDbRepository imageDbRepository) {
        this.imageDbRepository = imageDbRepository;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<ImageResponse> saveImage(MultipartFile file) {

         String imageName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
         String contentType = file.getContentType();
         ImageDb imageDb = ImageDb.builder()
                 .fileName(imageName)
                 .type(contentType)
                 .file(file.getBytes())
                 .build();
         imageDbRepository.save(imageDb);
        RepresentationModel imageLink = new RepresentationModel(WebMvcLinkBuilder.linkTo(methodOn(ImageDbController.class).downloadImage(imageDb.getFileName())).withRel("imageLink"));
         return new ResponseEntity<>(ImageResponse.builder().success(true).url(imageLink.getLinks().toList().get(0).getHref()).build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> downloadImage(String fileName) {

        ImageDb image = imageDbRepository.findByFileName(fileName).orElseThrow(
                ()->new RuntimeException("No image with name " + fileName)
        );

        MediaType contentType = MediaType.parseMediaType(image.getType());

            return ResponseEntity.ok()
                    .contentType(contentType)
                    //
                    //for download
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+ resource.getFilename())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+ image.getFileName())
                    .body(image.getFile());

    }


}
