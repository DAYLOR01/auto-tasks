package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.UserFacade;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(
            UserFacade userFacade) {
        this.userFacade = userFacade;
    }

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

    @Secured("ROLE_USER_MANAGEMENT")
    @PutMapping("/change-active")
    public ResponseDto.Message changeActive(
            @RequestBody RequestDto.UserChangeActive userChangeActive,
            HttpServletResponse response
    ) {
        try {
            userFacade.changeActive(userChangeActive);
            return ResponseDto.Message.builder().message("Ok").build();
        } catch (Exception ignore) {
            response.setStatus(404);
            return ResponseDto.Message.builder().message("User not found").build();
        }
    }

    @GetMapping("/least-loaded")
    public List<ResponseDto.UserShort> getLeastLoadedByTagsAndRole(@RequestParam Integer[] tagIds, @RequestParam int roleId) {
        return userFacade.findLeastLoadedUsers(tagIds, roleId);
    }

    @GetMapping

    @Secured("ROLE_USER_MANAGEMENT")
    @PostMapping
    public ResponseDto.UserShort createUser(
            @RequestBody RequestDto.UserFull userInfo,
            HttpServletResponse response
    ) {
        try {
            return userFacade.createUser(userInfo);
        }
        catch (Exception ignore) {
            response.setStatus(400);
            return null;
        }
    }

    @GetMapping(params={"pageNumber", "pageSize"})
    public ResponseDto.Page<ResponseDto.UserShort> getUsersPage(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return userFacade.getPage(pageNumber, pageSize);
    }

    @Secured("ROLE_USER_MANAGEMENT")
    @GetMapping("/{username}")
    public ResponseDto.UserFull getUserFullInfo(@PathVariable String username) {
        return userFacade.getByUsername(username);
    }

    @Secured("ROLE_USER_MANAGEMENT")
    @PutMapping()
    public ResponseDto.Message updateUser(
            @RequestBody RequestDto.UserFull userInfo,
            HttpServletResponse response
    ) {
        try {
            userFacade.updateUser(userInfo);
            return ResponseDto.Message.builder().message("Ok").build();
        }
        catch (Exception ignore) {
            response.setStatus(404);
            return ResponseDto.Message.builder().message("User not found").build();
        }
    }
}
