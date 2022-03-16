package kz.autotask.web.service;

import kz.autotask.web.data.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findByUserPriorityLessThan(int userPriority);

    List<Role> findByIds(Integer[] ids);

    List<Role> findAll();
}
