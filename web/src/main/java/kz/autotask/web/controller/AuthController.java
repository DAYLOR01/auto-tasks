package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.AuthFacade;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/login")
public class AuthController {

    private final AuthFacade authFacade;

    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping
    public ResponseDto.Auth login(@RequestBody RequestDto.Auth request) {
        return authFacade.login(request);
    }

    @GetMapping
    public ResponseDto.UserShort getLogin(@RequestHeader("Authorization") String authHeader) {
        return authFacade.getLogin(authHeader);
    }
}
