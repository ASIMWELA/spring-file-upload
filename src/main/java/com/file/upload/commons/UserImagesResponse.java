package com.file.upload.commons;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserImagesResponse {
    List<String> imageUrl;
}
