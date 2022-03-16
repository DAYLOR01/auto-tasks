package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.TaskHistory;
import kz.autotask.web.data.repository.TaskHistoryRepository;
import kz.autotask.web.service.TaskHistoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;

    public TaskHistoryServiceImpl(TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
    }

    @Override
    public TaskHistory create(TaskHistory taskHistory) {
        return taskHistoryRepository.save(taskHistory);
    }

    @Override
    public List<TaskHistory> findAllByTaskId(long taskId) {
        return taskHistoryRepository.findAllByTask_Id(taskId, Sort.by("created_at"));
    }
}
