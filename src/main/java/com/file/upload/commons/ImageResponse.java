package com.file.upload.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse {
    boolean success;
    int userId;
    String url;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<String> imageUrls;
}
