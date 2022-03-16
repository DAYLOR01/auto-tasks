package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.TaskHistory;
import kz.autotask.web.facade.TaskHistoryFacade;
import kz.autotask.web.mapper.RequestMapper;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.TaskHistoryService;
import kz.autotask.web.service.TaskService;
import kz.autotask.web.service.UserService;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskHistoryFacadeImpl implements TaskHistoryFacade {

    private final TaskHistoryService taskHistoryService;
    private final TaskService taskService;
    private final UserService userService;

    public TaskHistoryFacadeImpl(TaskHistoryService taskHistoryService, TaskService taskService, UserService userService) {
        this.taskHistoryService = taskHistoryService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public void addCommentary(String username, RequestDto.TaskAddCommentary commentary) {
        TaskHistory taskHistory = RequestMapper.entityFromTaskAddCommentary(commentary);
        taskHistory.setTask(taskService.findById(commentary.getTaskId()));
        taskHistory.setCreatedBy(userService.findByUsername(username));
        taskHistory.setCreatedAt(Timestamp.from(Instant.now()));
        taskHistoryService.create(taskHistory);
    }

    @Override
    public List<ResponseDto.TaskHistoryFull> findAllByTaskId(long taskId) {
        List<ResponseDto.TaskHistoryFull> response = new ArrayList<>();
        taskHistoryService.findAllByTaskId(taskId).forEach(taskHistory -> {
            response.add(ResponseMapper.taskHistoryFullFromEntity(taskHistory));
        });
        return response;
    }
}
