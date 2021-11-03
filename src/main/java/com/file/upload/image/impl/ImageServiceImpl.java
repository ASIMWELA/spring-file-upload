package com.file.upload.image.impl;

import com.file.upload.commons.ImageResponse;
import com.file.upload.image.Image;
import com.file.upload.image.ImageController;
import com.file.upload.image.ImageRepository;
import com.file.upload.image.ImageService;
import com.file.upload.user.User;
import com.file.upload.user.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    Path filePath;

    String fileStorageLocation;

    final
    UserRepository userRepository;

    final
    ImageRepository imageRepository;
    public ImageServiceImpl(@Value("${file.storage.location}") String fileUploadLocation, UserRepository userRepository, ImageRepository imageRepository){
        filePath = Paths.get(fileUploadLocation).toAbsolutePath().normalize();
        fileStorageLocation = fileUploadLocation;
        try {
            Files.createDirectories(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<ImageResponse> upload(int userId, MultipartFile imageFile, HttpServletRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No user found"));
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
        String contentType = imageFile.getContentType();


        if(imageRepository.existsByName(imageName)){
            throw new RuntimeException("Image already exists");
        }

        RepresentationModel imageLink = new RepresentationModel(WebMvcLinkBuilder.linkTo(methodOn(ImageController.class).downloadImage(user.getId(), imageName, request)).withRel("imageLink"));
        Path path = Paths.get(filePath + "\\" + imageName);
        Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        Image image = Image.builder()
                .imageUrl(imageLink.getLinks().toList().get(0).getHref())
                .user(user)
                .imageType(contentType)
                .onDisplay(true)
                .build();
        image.setName(imageName);
        user.getImages().forEach(img->{
            img.setOnDisplay(false);
            imageRepository.save(img);
        });

        imageRepository.save(image);

        ImageResponse response = ImageResponse.builder()
                        .url(imageLink.getLinks().toList().get(0).getHref())
                        .success(true)
                        .userId(user.getId())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @Override
    @SneakyThrows
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity.HeadersBuilder deleteImage(int userid, String imageName) {

        User user = userRepository.findById(userid).orElseThrow(
                ()->new RuntimeException("No use found")
        );

        Image image = imageRepository.findByName(imageName).orElseThrow(
                () -> new RuntimeException("No Image found with the name")
        );
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(image.getName());
        if (Files.deleteIfExists(path)) {
            imageRepository.delete(image);
            return ResponseEntity.noContent();
        }else{
            throw new RuntimeException("No file with name");
        }
    }

    @Override
    public ResponseEntity<ImageResponse> uploadImages(int userId, List<MultipartFile> files) {
        return null;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<Resource> downloadImage(int userId, String imageName, HttpServletRequest request) {

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("No user ")
        );

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(imageName);

        Resource resource = new UrlResource(path.toUri());


        String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());


        MediaType contentType = MediaType.parseMediaType(mimeType);

        if(resource.exists() && resource.isReadable()){
            return ResponseEntity.ok()
                    .contentType(contentType)
                    //for download
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+ resource.getFilename())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+ resource.getFilename())
                    .body(resource);
        }else {
            throw new RuntimeException("Cannot read");
        }

    }
}
