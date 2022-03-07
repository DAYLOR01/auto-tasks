package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.TaskFacade;
import kz.autotask.web.facade.TopicFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskFacade taskFacade;

    public TaskController(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    @GetMapping("/my")
    public List<ResponseDto.TaskShort> getMyTasksByStatus(
            Principal principal,
            @RequestParam String status,
            @RequestParam(required = false, defaultValue = "false") boolean lastMonth) {
        if (lastMonth)
            return taskFacade.findAllByAssignedUserAndStatusCompletedMonthAgo(principal.getName(), status);
        return taskFacade.findAllByAssignedUserAndStatus(principal.getName(), status);
    }
}
