package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.Topic;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.facade.TopicFacade;
import kz.autotask.web.mapper.RequestMapper;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.security.JwtProvider;
import kz.autotask.web.service.TopicService;
import kz.autotask.web.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class TopicFacadeImpl implements TopicFacade {

    private final TopicService topicService;
    private final UserService userService;

    public TopicFacadeImpl(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    public ResponseDto.Page<ResponseDto.TopicFull> getPage(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Topic> topicEntities = topicService.getPage(pageRequest);
        return ResponseMapper.topicsPageFromPageDomain(topicEntities);
    }

    @Override
    public ResponseDto.TopicFull createTopic(RequestDto.TopicFull topic, String authorUsername) {
        Topic topicEntity = RequestMapper.entityFromTopicFull(topic);
        User author = userService.findByUsername(authorUsername);
        topicEntity.setAuthor(author);
        return ResponseMapper.topicFullFromEntity(topicService.save(topicEntity));
    }
}
