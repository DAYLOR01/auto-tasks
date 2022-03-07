package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.UserFacade;
import kz.autotask.web.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void changePassword(String username, RequestDto.UserChangePassword userChangePassword) {
//        if(userService.findByUsernameAndPassword(username, userChangePassword.getOldPassword()) == null)
//            throw new NullPointerException();
        userService.changePassword(username, userChangePassword.getNewPassword());
    }
}
