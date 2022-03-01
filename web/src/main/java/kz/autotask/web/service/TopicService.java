package kz.autotask.web.service;

import kz.autotask.web.data.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {

    Page<Topic> getPage(Pageable pageable);

    Topic save(Topic topic);
}
