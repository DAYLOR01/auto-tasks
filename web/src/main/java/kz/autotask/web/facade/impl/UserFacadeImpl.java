package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.facade.UserFacade;
import kz.autotask.web.mapper.RequestMapper;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.RoleService;
import kz.autotask.web.service.TagService;
import kz.autotask.web.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final TagService tagService;
    private final RoleService roleService;

    public UserFacadeImpl(UserService userService, TagService tagService, RoleService roleService) {
        this.userService = userService;
        this.tagService = tagService;
        this.roleService = roleService;
    }

    @Override
    public void changePassword(String username, RequestDto.UserChangePassword userChangePassword) {
        if(userService.findActiveByUsernameAndPassword(username, userChangePassword.getOldPassword()) == null)
            throw new NullPointerException();
        userService.changePassword(username, userChangePassword.getNewPassword());
    }

    @Override
    public void changeActive(RequestDto.UserChangeActive userChangePassword) {
        userService.changeActive(userChangePassword.getUsername(), userChangePassword.isActive());
    }

    @Override
    public List<ResponseDto.UserShort> findLeastLoadedUsers(Integer[] tagIds, int roleId) {
        List<ResponseDto.UserShort> response = new ArrayList<>();
        userService.findLeastLoadedUsers(tagIds, roleId).forEach(user -> {
            response.add(ResponseMapper.userShortFromEntity(user));
        });
        return response;
    }

    @Override
    public long countUsersByTagsAndRole(Integer[] tagIds, int roleId) {
        return userService.countByTagsAndRole(tagIds, roleId);
    }

    @Override
    public ResponseDto.Page<ResponseDto.UserShort> getPage(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> userEntities = userService.getPage(pageRequest);
        return ResponseMapper.userPageFromPageDomain(userEntities);
    }

    @Override
    public ResponseDto.UserFull getByUsername(String username) {
        return ResponseMapper.userFullFromEntity(userService.findByUsername(username));
    }

    @Override
    public ResponseDto.UserShort createUser(RequestDto.UserFull newUser) {
        User userEntity = RequestMapper.entityFromUserFull(newUser);
        userEntity.setTags(tagService.findByIds(newUser.getTags()));
        userEntity.setRoles(roleService.findByIds(newUser.getRoles()));
        userEntity.setIsActive(true);
        return ResponseMapper.userShortFromEntity(userService.createNew(userEntity));
    }

    @Override
    public void updateUser(RequestDto.UserFull updateUser) {
        User userEntity = RequestMapper.entityFromUserFull(updateUser);
        userEntity.setTags(tagService.findByIds(updateUser.getTags()));
        userEntity.setRoles(roleService.findByIds(updateUser.getRoles()));
        ResponseMapper.userFullFromEntity(userService.updateUser(userEntity));
    }


}
