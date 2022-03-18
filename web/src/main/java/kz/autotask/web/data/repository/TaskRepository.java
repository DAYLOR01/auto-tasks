package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.Task;
import kz.autotask.web.data.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAssignedUser_UsernameAndStatus(String username, TaskStatus status, Sort sort);

    List<Task> findAllByAssignedUser_UsernameAndStatusAndCompletionDateAfter(String username, TaskStatus status, LocalDate completionDateMax, Sort sort);

    Task findById(long id);

    Page<Task> getPageByAuthorUser_UsernameOrAssignedUser_Username(
            String authorUsername,
            String assigneeUsername,
            Pageable pageable
    );
}
