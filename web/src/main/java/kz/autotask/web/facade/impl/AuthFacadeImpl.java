package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.facade.AuthFacade;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.security.JwtProvider;
import kz.autotask.web.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthFacadeImpl(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ResponseDto.Auth login(RequestDto.Auth request) {
        User user = userService.findActiveByUsernameAndPassword(request.getUsername(), request.getPassword());
        if(user == null || user.getRoles().isEmpty())
            throw new UsernameNotFoundException("User with username: " + request.getUsername() + " - not found or not activated.");
        String token = jwtProvider.generateToken(user.getUsername());
        return ResponseDto.Auth.builder().token(token).build();
    }

    @Override
    public ResponseDto.UserFull getLogin(String authHeader) {
        String username = jwtProvider.getLoginFromToken(JwtProvider.getTokenFromHeader(authHeader));
        User userEntity = userService.findByUsername(username);
        return ResponseMapper.userFullFromEntity(userEntity);
    }
}
