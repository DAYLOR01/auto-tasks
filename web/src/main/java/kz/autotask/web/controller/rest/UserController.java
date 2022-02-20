package kz.autotask.web.controller.rest;

import kz.autotask.web.data.entity.User;
import kz.autotask.web.security.AuthRequest;
import kz.autotask.web.security.AuthResponse;
import kz.autotask.web.security.JwtProvider;
import kz.autotask.web.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public UserController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    public Iterable<User> getUsers(){
        return userService.findAll();
    }

}
