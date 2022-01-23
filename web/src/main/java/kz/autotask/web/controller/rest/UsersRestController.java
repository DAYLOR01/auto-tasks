package kz.autotask.web.controller.rest;

import kz.autotask.web.data.entity.User;
import kz.autotask.web.data.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRestController {

    private final UsersRepository usersRepository;

    public UsersRestController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all-users")
    public Iterable<User> getUsers(){
        return usersRepository.findAll();
    }

}
