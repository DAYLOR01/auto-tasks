package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
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
    public ResponseDto.Auth login(@RequestBody RequestDto.Auth request) {
        User user = userService.findByUsernameAndPassword(request.username, request.password);
        String token = jwtProvider.generateToken(user.getUsername());
        ResponseDto.Auth response = new ResponseDto.Auth();
        response.token = token;
        return response;
    }

    @GetMapping("/login")
    public ResponseDto.UserShort getLogin(@RequestHeader("Authorization") String authHeader) {
        String username = jwtProvider.getLoginFromToken(JwtProvider.getTokenFromHeader(authHeader));
        User user = userService.findByUsername(username);
        ResponseDto.UserShort response = new ResponseDto.UserShort();
        response.username = user.getUsername();
        response.name = user.getName();
        response.isActive = user.isActive();
        return response;
    }



    static class AuthResponse {

    }
    static class AuthRequest {
        public String username, password;
    }
}
