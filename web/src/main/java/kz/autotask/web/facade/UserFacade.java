package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;

import java.util.List;

public interface UserFacade {

    void changePassword(String username, RequestDto.UserChangePassword userChangePassword);

    void changeActive(RequestDto.UserChangeActive userChangePassword);

    List<ResponseDto.UserShort> findLeastLoadedUserByTagsAndRole(Integer[] tagIds, int roleId);

    ResponseDto.Page<ResponseDto.UserShort> getPage(int pageNumber, int pageSize);

    ResponseDto.UserFull getByUsername(String username);

    ResponseDto.UserShort createUser(RequestDto.UserFull newUser);

    void updateUser(RequestDto.UserFull newUser);

}
