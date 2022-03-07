package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.enums.TaskStatus;
import kz.autotask.web.facade.TaskFacade;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.TaskService;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskFacadeImpl implements TaskFacade {

    private final TaskService taskService;

    public TaskFacadeImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<ResponseDto.TaskShort> findAllByAssignedUserAndStatus(String username, String status) {
        List<ResponseDto.TaskShort> response = new ArrayList<>();
        taskService.findAllByAssignedUserAndStatus(username, TaskStatus.valueOf(status)).forEach( task -> {
            response.add(ResponseMapper.taskShortFromEntity(task));
        });
        return response;
    }

    @Override
    public List<ResponseDto.TaskShort> findAllByAssignedUserAndStatusCompletedMonthAgo(String username, String status) {
        List<ResponseDto.TaskShort> response = new ArrayList<>();
        taskService.findAllByAssignedUserAndStatusAndCompletionDateBefore(
                username,
                TaskStatus.valueOf(status),
                LocalDate.now().minusMonths(1)
        ).forEach(task -> {
            response.add(ResponseMapper.taskShortFromEntity(task));
        });
        return response;
    }
}
