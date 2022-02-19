package kz.autotask.web.controller.rest;

import kz.autotask.web.data.entity.User;
import kz.autotask.web.security.JwtProvider;
import kz.autotask.web.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userService.findByUsernameAndPassword(request.username, request.password);
        String token = jwtProvider.generateToken(user.getUsername());
        AuthResponse response = new AuthResponse();
        response.token = token;
        return response;
    }

    @GetMapping("/login")
    public UserShortResponse getLogin(@RequestHeader("Authorization") String authHeader) {
        String username = jwtProvider.getLoginFromToken(JwtProvider.getTokenFromHeader(authHeader));
        User user = userService.findByUsername(username);
        UserShortResponse response = new UserShortResponse();
        response.username = user.getUsername();
        response.name = user.getName();
        response.isActive = user.isActive();
        return response;
    }



    static class AuthResponse {
        public String token;
    }
    static class AuthRequest {
        public String username, password;
    }
    static class UserShortResponse {
        public String username, name;
        public boolean isActive;
        // TODO
    }
}
