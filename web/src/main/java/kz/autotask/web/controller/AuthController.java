package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.facade.AuthFacade;
import kz.autotask.web.security.JwtProvider;
import kz.autotask.web.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin("http://localhost:8080")
public class AuthController {

    private final AuthFacade authFacade;

    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/login")
    public ResponseDto.Auth login(@RequestBody RequestDto.Auth request) {
        return authFacade.login(request);
    }

    @GetMapping("/login")
    public ResponseDto.UserShort getLogin(@RequestHeader("Authorization") String authHeader) {
        return authFacade.getLogin(authHeader);
    }
}
