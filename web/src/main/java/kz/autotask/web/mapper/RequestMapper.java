package kz.autotask.web.mapper;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.data.entity.Topic;

public class RequestMapper {
    public static Topic entityFromTopicFull(RequestDto.TopicFull dto) {
        Topic topic = new Topic();
        topic.setHeader(dto.getHeader());
        topic.setContent(dto.getContent());
        return topic;
    }
}
