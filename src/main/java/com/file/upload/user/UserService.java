package com.file.upload.user;

import com.file.upload.commons.ApiResponse;
import com.file.upload.commons.UserImagesResponse;
import com.file.upload.user.hateos.UserDto;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<ApiResponse> save(User user);
    ResponseEntity<?> deleteUser(int id);
    ResponseEntity<UserDto> getUser(int id);
    List<User> getUserList();
    ResponseEntity<UserImagesResponse> getUserImages(int userId);
    ResponseEntity<PagedModel<UserDto>> getPagedUsers(int page, int size, PagedResourcesAssembler<User> pagedResourcesAssembler);

 }
