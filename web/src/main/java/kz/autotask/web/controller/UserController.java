package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.security.JwtProvider;
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
    private final JwtProvider jwtProvider;

    public UserController(
            UserService userService,
            TagService tagService,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider
    ) {
        this.userService = userService;
        this.tagService = tagService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    public Iterable<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseDto.UserShort getUserByUsername(@PathVariable String username) {
        User userEntity = userService.findActiveUserByUsername(username);
        ResponseDto.UserShort response = new ResponseDto.UserShort();
        response.username = userEntity.getUsername();
        response.name = userEntity.getName();
        response.isActive = userEntity.isActive();
        return response;
    }

    @PostMapping
    public ResponseDto.UserShort createUser(@RequestBody RequestDto.UserFull userInfo) {
        User userEntity = new User();
        userEntity.setUsername(userInfo.username);
        userEntity.setName(userInfo.name);
        userEntity.setPassword(passwordEncoder.encode(userInfo.password));
        userEntity.setActive(true);
        userEntity.getTags().add(tagService.findById(userInfo.mainTag));
        // TODO: userService.save
    }


}
