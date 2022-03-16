package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.Role;
import kz.autotask.web.data.repository.RoleRepository;
import kz.autotask.web.service.RoleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> findByUserPriorityLessThan(int userPriority) {
        return roleRepository.findByUserPriorityLessThan(userPriority);
    }

    @Override
    public List<Role> findByIds(Integer[] ids) {
        return roleRepository.findAllById(Arrays.asList(ids));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
