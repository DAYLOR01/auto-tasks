package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.Task;
import kz.autotask.web.data.entity.enums.TaskStatus;
import kz.autotask.web.data.repository.TaskRepository;
import kz.autotask.web.service.TaskService;
import org.springframework.stereotype.Component;

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
        return taskRepository.findAllByAssignedUser_UsernameAndStatus(username, status);
    }

    @Override
    public List<Task> findAllByAssignedUserAndStatusAndCompletionDateBefore(
            String username,
            TaskStatus status,
            LocalDate completionDate
    ) {
        return taskRepository.findAllByAssignedUser_UsernameAndStatusAndCompletionDateAfter(username, status, completionDate);
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
