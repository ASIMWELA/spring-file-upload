package com.file.upload.user.impl;

import com.file.upload.commons.ApiResponse;
import com.file.upload.commons.UserImagesResponse;
import com.file.upload.user.User;
import com.file.upload.user.UserController;
import com.file.upload.user.UserRepository;
import com.file.upload.user.UserService;
import com.file.upload.user.hateos.UserAssembler;
import com.file.upload.user.hateos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final UserAssembler userAssembler;

    @Override
    public ResponseEntity<ApiResponse> save(User user) {

//
//        List<EntityModel<Employee>> employees = StreamSupport.stream(repository.findAll().spliterator(), false)
//                .map(employee -> EntityModel.of(employee,
//                        linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(
//                CollectionModel.of(employees,
//                        linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel()));
//


//        return repository.findById(id)
//                .map(employee -> EntityModel.of(employee,
//                        linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")))
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());

        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "user saved"),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(int id) {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> getUser(int id) {
        UserDto userDto = userAssembler.toModel(userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user with id " + id)));
        userDto.add(linkTo(methodOn(UserController.class).getAllUsers(0, 20, null)).withRel("users"));

        return ResponseEntity.ok(userDto);

    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<UserImagesResponse> getUserImages(int userId) {
        return null;
    }

    @Override
    public ResponseEntity<PagedModel<UserDto>> getPagedUsers(int page, int size, PagedResourcesAssembler<User> pagedResourcesAssembler) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users  = userRepository.findAll(pageRequest);
        PagedModel<UserDto> userPagedModel = pagedResourcesAssembler.toModel(users,userAssembler);
//        userPagedModel.add(linkTo(methodOn(UserController.class).getAllUsers(0, 20, null)).withSelfRel()
//        .andAffordance(afford(methodOn(UserController.class).saveUser(null))).withRel("create_user")
//        );
        userPagedModel.getContent().forEach(userDto -> userDto.add(linkTo(methodOn(UserController.class).getUser(userDto.getId())).withSelfRel()));
        return ResponseEntity.ok().body(userPagedModel);
    }


}
