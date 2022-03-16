package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByUserPriorityLessThan(int userPriority);
}
