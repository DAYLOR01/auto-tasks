package kz.autotask.web.service;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;

public interface UserService {

    Iterable<User> findAll();

    User findByUsername(String username);

    User findActiveUserByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    void changePassword(String username, String newPassword);
}
