package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.Task;
import kz.autotask.web.data.entity.enums.TaskStatus;
import kz.autotask.web.data.repository.TaskRepository;
import kz.autotask.web.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Component
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAllByAssignedUserAndStatus(String username, TaskStatus status) {
        return taskRepository.findAllByAssignedUser_UsernameAndStatus(
                username, status,
                Sort.by(Sort.Direction.DESC, "updatedAt", "id")
        );
    }

    @Override
    public List<Task> findAllByAssignedUserAndStatusAndCompletionDateBefore(
            String username,
            TaskStatus status,
            LocalDate completionDate
    ) {
        return taskRepository.findAllByAssignedUser_UsernameAndStatusAndCompletionDateAfter(
                username, status, completionDate,
                Sort.by(Sort.Direction.DESC, "updatedAt", "id")
        );
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        task.setUpdatedAt(Timestamp.from(Instant.now()));
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getPageByAuthorOrAssignee(String authorUsername, String assigneeUsername, Pageable pageable) {
        return taskRepository.getPageByAuthorUser_UsernameOrAssignedUser_Username(authorUsername, assigneeUsername, pageable);
    }
}
