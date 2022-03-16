package kz.autotask.web.mapper;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.data.entity.Task;
import kz.autotask.web.data.entity.TaskHistory;
import kz.autotask.web.data.entity.Topic;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.data.entity.enums.TaskHistoryType;
import kz.autotask.web.data.entity.enums.TaskStatus;

import java.time.LocalDate;

public class RequestMapper {
    public static Topic entityFromTopicFull(RequestDto.TopicFull dto) {
        Topic topic = new Topic();
        topic.setHeader(dto.getHeader());
        topic.setContent(dto.getContent());
        return topic;
    }

    public static Task entityFromTaskFull(RequestDto.TaskFull dto) {
        Task task = new Task();
        task.setHeader(dto.getHeader());
        task.setText(dto.getText());
        task.setInspirationDate(dto.getInspirationDate());
        task.setAssignDate(LocalDate.now());
        task.setStatus(TaskStatus.OPEN);
        return task;
    }

    public static User entityFromUserFull(RequestDto.UserFull dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setIsActive(true);
        return user;
    }

    public static TaskHistory entityFromTaskChangeStatus(RequestDto.TaskChangeStatus dto) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setType(TaskHistoryType.CHANGE_STATUS);
        taskHistory.setValue(dto.getNewStatus().name());
        return taskHistory;
    }

    public static TaskHistory entityFromTaskAddCommentary(RequestDto.TaskAddCommentary dto) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setType(TaskHistoryType.ADD_COMMENTARY);
        taskHistory.setValue(dto.getCommentary());
        return taskHistory;
    }
}
