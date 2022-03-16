package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.ResponseDto;

import java.util.List;

public interface RoleFacade {

    List<ResponseDto.RoleFull> getRolesWithLessPriority(String username);

    List<ResponseDto.RoleFull> getAll();
}
