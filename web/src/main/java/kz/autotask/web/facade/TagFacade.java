package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.ResponseDto;

import java.util.List;

public interface TagFacade {

    List<ResponseDto.TagFull> findTagsByUsability(String usability);

    List<ResponseDto.TagFull> getMainTagsByUsername(String username);

}
