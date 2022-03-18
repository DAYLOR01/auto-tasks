package kz.autotask.web.service;

import kz.autotask.web.data.entity.Task;
import kz.autotask.web.data.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    List<Task> findAllByAssignedUserAndStatus(String username, TaskStatus status);

    List<Task> findAllByAssignedUserAndStatusAndCompletionDateBefore(
            String username,
            TaskStatus status,
            LocalDate completionDate
    );

    Task findById(long id);

    Task save(Task task);

    Page<Task> getPageByAuthorOrAssignee(String authorUsername, String assigneeUsername, Pageable pageable);
}
