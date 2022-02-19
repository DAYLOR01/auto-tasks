package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
