package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.service.TagService;
import kz.autotask.web.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final TagService tagService;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UserService userService,
            TagService tagService,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.tagService = tagService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Iterable<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseDto.UserShort getUserByUsername(@PathVariable String username) {
        User userEntity = userService.findActiveUserByUsername(username);
        return ResponseDto.UserShort.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .isActive(userEntity.getIsActive())
                .build();
    }

    /*@PostMapping
    public ResponseDto.UserShort createUser(@RequestBody RequestDto.UserFull userInfo) {
        User userEntity = new User();
        userEntity.setUsername(userInfo.getUsername());
        userEntity.setName(userInfo.getName());
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userEntity.setIsActive(true);
        userEntity.getTags().add(tagService.findById(userInfo.getMainTag()));
        // TODO: userService.save
    }*/


}
