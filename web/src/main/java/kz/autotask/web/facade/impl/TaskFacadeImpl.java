package kz.autotask.web.facade.impl;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.data.entity.Task;
import kz.autotask.web.data.entity.TaskHistory;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.data.entity.enums.TaskStatus;
import kz.autotask.web.facade.TaskFacade;
import kz.autotask.web.mapper.RequestMapper;
import kz.autotask.web.mapper.ResponseMapper;
import kz.autotask.web.service.TagService;
import kz.autotask.web.service.TaskHistoryService;
import kz.autotask.web.service.TaskService;
import kz.autotask.web.service.UserService;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskFacadeImpl implements TaskFacade {

    private final TaskService taskService;
    private final UserService userService;
    private final TagService tagService;
    private final TaskHistoryService taskHistoryService;

    public TaskFacadeImpl(TaskService taskService, UserService userService, TagService tagService, TaskHistoryService taskHistoryService) {
        this.taskService = taskService;
        this.userService = userService;
        this.tagService = tagService;
        this.taskHistoryService = taskHistoryService;
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

    @Override
    public ResponseDto.TaskFull findById(long id) {
        return ResponseMapper.taskFullFromEntity(taskService.findById(id));
    }

    @Override
    public ResponseDto.TaskShort save(String authorUsername, RequestDto.TaskFull task) {
        Task taskEntity = RequestMapper.entityFromTaskFull(task);
        taskEntity.setAuthorUser(userService.findByUsername(authorUsername));
        User assignedUser;
        if(task.isAutoAssignUser()) {
            assignedUser = userService.findLeastLoadedUserByTagsAndRole(
                    task.getAssignedUserTagIds(),
                    task.getAssignedUserRole()
            ).get(0);
        }
        else {
            assignedUser = userService.findByUsername(task.getAssignedUser());
        }
        taskEntity.setAssignedUser(assignedUser);
        taskEntity.setTags(tagService.findByIds(task.getTagIds()));
        return ResponseMapper.taskShortFromEntity(taskService.save(taskEntity));
    }

    @Override
    public ResponseDto.TaskShort changeStatus(String username, RequestDto.TaskChangeStatus taskChangeStatus) {
        Task task = taskService.findById(taskChangeStatus.getTaskId());
        task.setStatus(taskChangeStatus.getNewStatus());
        if (taskChangeStatus.getNewStatus() == TaskStatus.COMPLETED || taskChangeStatus.getNewStatus() == TaskStatus.CLOSED)
            task.setCompletionDate(LocalDate.now());
        ResponseDto.TaskShort response = ResponseMapper.taskShortFromEntity(taskService.save(task));
        commitChangeStatus(username, task, taskChangeStatus);
        return response;
    }

    private void commitChangeStatus(String username, Task task, RequestDto.TaskChangeStatus taskChangeStatus) {
        TaskHistory taskHistory = RequestMapper.entityFromTaskChangeStatus(taskChangeStatus);
        taskHistory.setTask(task);
        taskHistory.setCreatedBy(userService.findByUsername(username));
        taskHistory.setCreatedAt(Timestamp.from(Instant.now()));
        taskHistoryService.create(taskHistory);
    }

}
