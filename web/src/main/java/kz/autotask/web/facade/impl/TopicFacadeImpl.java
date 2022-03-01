package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.Topic;
import kz.autotask.web.facade.TopicFacade;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class TopicFacadeImpl implements TopicFacade {

    private final TopicService topicService;

    public TopicFacadeImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public ResponseDto.Page<ResponseDto.TopicFull> getPage(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Topic> topicEntities = topicService.getPage(pageRequest);
        return ResponseMapper.topicsPageFromPageDomain(topicEntities);
    }

    @Override
    public ResponseDto.TopicFull saveTopic(RequestDto.TopicFull topic) {
        return ;
    }
}
