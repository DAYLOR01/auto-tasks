package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
