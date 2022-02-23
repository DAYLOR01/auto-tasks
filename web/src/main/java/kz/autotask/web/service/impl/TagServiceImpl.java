package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.repository.TagRepository;
import kz.autotask.web.service.TagService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(
            TagRepository tagRepository
    ) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.getById(id);
    }
}
