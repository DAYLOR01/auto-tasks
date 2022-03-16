package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.enums.TagUsability;
import kz.autotask.web.facade.TagFacade;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.TagService;
import kz.autotask.web.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagFacadeImpl implements TagFacade {

    private final TagService tagService;
    private final UserService userService;

    public TagFacadeImpl(TagService tagService, UserService userService) {
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public List<ResponseDto.TagFull> findTagsByUsability(String usability) {
        List<ResponseDto.TagFull> response = new ArrayList<>();
        tagService.findTagsByUsability(TagUsability.valueOf(usability)).forEach(tag -> {
            response.add(ResponseMapper.tagFullFromEntity(tag));
        });
        return response;
    }

    @Override
    public List<ResponseDto.TagFull> getMainTagsByUsername(String username) {
        List<ResponseDto.TagFull> response = new ArrayList<>();
        userService.findMainTagsByUsername(username).forEach(tag -> {
            response.add(ResponseMapper.tagFullFromEntity(tag));
        });
        return response;
    }
}
