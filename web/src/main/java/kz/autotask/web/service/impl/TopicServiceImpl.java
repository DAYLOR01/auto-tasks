package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.Topic;
import kz.autotask.web.data.repository.TopicRepository;
import kz.autotask.web.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Page<Topic> getPage(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }
}
