package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.entity.enums.TagUsability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAllByUsability(TagUsability usability);

}
