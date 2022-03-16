package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.Role;
import kz.autotask.web.facade.RoleFacade;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.RoleService;
import kz.autotask.web.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleFacadeImpl implements RoleFacade {

    private final RoleService roleService;
    private final UserService userService;

    public RoleFacadeImpl(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public List<ResponseDto.RoleFull> getRolesWithLessPriority(String username) {
        int maxPriority = -1;
        for (Role role : userService.findByUsername(username).getRoles()) {
            if (role.getUserPriority() != null && maxPriority < role.getUserPriority())
                maxPriority = role.getUserPriority();
        }
        List<ResponseDto.RoleFull> response = new ArrayList<>();
        roleService.findByUserPriorityLessThan(maxPriority).forEach(role -> {
            response.add(ResponseMapper.roleFullFromEntity(role));
        });
        return response;
    }

    @Override
    public List<ResponseDto.RoleFull> getAll() {
        List<ResponseDto.RoleFull> response = new ArrayList<>();
        roleService.findAll().forEach(role -> {
            response.add(ResponseMapper.roleFullFromEntity(role));
        });
        return response;
    }
}
