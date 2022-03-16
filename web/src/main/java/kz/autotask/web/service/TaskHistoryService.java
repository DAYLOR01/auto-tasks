package kz.autotask.web.service;

import kz.autotask.web.data.entity.TaskHistory;

import java.util.List;

public interface TaskHistoryService {

    TaskHistory create(TaskHistory taskHistory);

    List<TaskHistory> findAllByTaskId(long taskId);

}
