package kz.autotask.web.mapper;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.User;

public class ResponseMapper {
    public static ResponseDto.UserShort userShortFromEntity(User userEntity) {
        return ResponseDto.UserShort.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .isActive(userEntity.getIsActive())
                .build();
    }
}
