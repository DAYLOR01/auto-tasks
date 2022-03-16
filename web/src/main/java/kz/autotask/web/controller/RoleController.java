package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.RoleFacade;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    private final RoleFacade roleFacade;

    public RoleController(RoleFacade roleFacade) {
        this.roleFacade = roleFacade;
    }

    @GetMapping("/less-than-mine")
    public List<ResponseDto.RoleFull> getRolesWithLessPriority(Principal principal) {
        return roleFacade.getRolesWithLessPriority(principal.getName());
    }

    @Secured("ROLE_USER_MANAGEMENT")
    @GetMapping()
    public List<ResponseDto.RoleFull> getAll() {
        return roleFacade.getAll();
    }
}
