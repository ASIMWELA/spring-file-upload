package com.file.upload.user;

import com.file.upload.commons.ApiResponse;
import com.file.upload.user.hateos.UserDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class UserController {


    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PagedModel<UserDto>> getAllUsers(@PositiveOrZero(message = "page number cannot be negative") @RequestParam(defaultValue = "0") Integer page, @PositiveOrZero(message = "page number cannot be negative") @RequestParam(defaultValue = "20") Integer size, PagedResourcesAssembler<User> pagedResourcesAssembler){
        return userService.getPagedUsers(page, size, pagedResourcesAssembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int userId){
        return userService.getUser(userId);
    }


    @GetMapping("/list")
    public List<User> getUsrs(){
        return userService.getUserList();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveUser(@RequestBody User user){
        return userService.save(user);
    }

}
