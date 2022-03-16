package kz.autotask.web.service;

import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.entity.enums.TagUsability;

import java.util.List;

public interface TagService {

    List<Tag> findTagsByUsability(TagUsability usability);

    List<Tag> findByIds(Integer[] ids);
    
}
