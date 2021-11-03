package com.file.upload.user.hateos;

import com.file.upload.image.Image;
import com.file.upload.image.ImageController;
import com.file.upload.user.User;
import com.file.upload.user.UserRepository;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserProcessor implements RepresentationModelProcessor<UserDto> {
    private final UserRepository userRepository;

    public UserProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto process(UserDto model) {
        User user = userRepository.findById(model.getId()).orElseThrow(()->new RuntimeException("No user with id "+model.getId()));
        Set<Image> userImages = user.getImages();
        if(!userImages.isEmpty()){
            userImages.forEach(image -> {
                if(image.isOnDisplay()){
                    model.add(linkTo(methodOn(ImageController.class).downloadImage(user.getId(), image.getName(), null)).withRel("profileImage"));
                    model.setProfileImageUrl(image.getImageUrl());
                }

            });
        }
        return model;
    }
}
