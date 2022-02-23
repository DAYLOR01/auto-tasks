package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
