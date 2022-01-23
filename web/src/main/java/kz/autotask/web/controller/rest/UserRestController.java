package kz.autotask.web.controller.rest;

import kz.autotask.web.data.entity.User;
import kz.autotask.web.data.repository.UserRepository;
import kz.autotask.web.security.AuthRequest;
import kz.autotask.web.security.AuthResponse;
import kz.autotask.web.security.JwtProvider;
import kz.autotask.web.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserRestController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public UserRestController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/all-users")
    public Iterable<User> getUsers(){
        return userService.findAll();
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

}
