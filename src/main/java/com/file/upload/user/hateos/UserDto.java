package com.file.upload.user.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.file.upload.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Relation(itemRelation = "user", collectionRelation = "users")
@JsonPropertyOrder({"id", "name", "firstName", "lastName", "profileImageUrl", "images"})
public class UserDto extends RepresentationModel<UserDto> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String firstName, lastName, name, profileImageUrl;
    int id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<String> images;


    public static UserDto build(User user){
        return UserDto.builder()
                .name(user.getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getId())
                .build();

    }
}
