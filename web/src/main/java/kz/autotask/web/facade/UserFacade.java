package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;

public interface UserFacade {

    void changePassword(String username, RequestDto.UserChangePassword userChangePassword);

}
