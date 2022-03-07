package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.facade.UserFacade;
import kz.autotask.web.service.TagService;
import kz.autotask.web.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(
            UserFacade userFacade) {
        this.userFacade = userFacade;
    }

//    @GetMapping
//    public Iterable<User> getUsers(){
//        return userService.findAll();
//    }

    @PutMapping("/change-password")
    public ResponseDto.Message changePassword(
            @RequestBody RequestDto.UserChangePassword userChangePassword,
            Principal principal,
            HttpServletResponse response
    ) {
        try {
            userFacade.changePassword(principal.getName(), userChangePassword);
            return ResponseDto.Message.builder().message("Ok").build();
        } catch (Exception ignore) {
            response.setStatus(400);
            return ResponseDto.Message.builder().message("Password is invalid").build();
        }
    }

    /*@PostMapping
    public ResponseDto.UserShort createUser(@RequestBody RequestDto.UserFull userInfo) {
        User userEntity = new User();
        userEntity.setUsername(userInfo.getUsername());
        userEntity.setName(userInfo.getName());
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userEntity.setIsActive(true);
        userEntity.getTags().add(tagService.findById(userInfo.getMainTag()));
        // TODO: userService.save
    }*/


}
