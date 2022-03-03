package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;

public interface AuthFacade {
    ResponseDto.Auth login(RequestDto.Auth request);

    ResponseDto.UserFull getLogin(String authHeader);
}
