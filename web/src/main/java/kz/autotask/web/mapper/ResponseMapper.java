package kz.autotask.web.mapper;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.Role;
import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.entity.Topic;
import kz.autotask.web.data.entity.User;
import org.springframework.data.domain.Page;

public class ResponseMapper {

    public static ResponseDto.UserShort userShortFromEntity(User userEntity) {
        return ResponseDto.UserShort.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .isActive(userEntity.getIsActive())
                .build();
    }

    public static ResponseDto.UserFull userFullFromEntity(User userEntity) {
        ResponseDto.UserFull.UserFullBuilder responseBuilder = ResponseDto.UserFull.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .isActive(userEntity.getIsActive());
        userEntity.getRoles().forEach(role -> {
            responseBuilder.role(roleFullFromEntity(role));
        });
        userEntity.getTags().forEach(tag -> {
            responseBuilder.tag(tagFullFromEntity(tag));
        });
        return responseBuilder.build();
    }

    public static ResponseDto.RoleFull roleFullFromEntity(Role roleEntity) {
        return ResponseDto.RoleFull.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .userPriority(roleEntity.getUserPriority())
                .build();
    }

    public static ResponseDto.TagFull tagFullFromEntity(Tag tag) {
        return ResponseDto.TagFull.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public static ResponseDto.TopicFull topicFullFromEntity(Topic topicEntity) {
        return ResponseDto.TopicFull.builder()
                .id(topicEntity.getId())
                .author(userShortFromEntity(topicEntity.getAuthor()))
                .header(topicEntity.getHeader())
                .content(topicEntity.getContent())
                .build();
    }

    public static ResponseDto.Page<ResponseDto.TopicFull> topicsPageFromPageDomain(Page<Topic> page) {
        ResponseDto.Page.PageBuilder<ResponseDto.TopicFull> responseBuilder =
                buildPageInfo(page, ResponseDto.TopicFull.class);
        page.getContent().forEach(topic -> {
            responseBuilder.element(topicFullFromEntity(topic));
        });
        return responseBuilder.build();
    }

    private static <T> ResponseDto.Page.PageBuilder<T> buildPageInfo(Page<?> page, Class<T> type) {
        return ResponseDto.Page.<T>builder()
                .pageNumber(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements());
    }

}
