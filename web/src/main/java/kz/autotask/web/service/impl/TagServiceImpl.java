package kz.autotask.web.service.impl;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.entity.enums.TagUsability;
import kz.autotask.web.data.repository.TagRepository;
import kz.autotask.web.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findTagsByUsability(TagUsability usability) {
        return tagRepository.findAllByUsability(usability);
    }

    @Override
    public List<Tag> findByIds(Integer[] ids) {
        return tagRepository.findAllById(Arrays.asList(ids));
    }
}
