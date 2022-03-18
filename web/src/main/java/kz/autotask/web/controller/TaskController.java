package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.TaskFacade;
import kz.autotask.web.facade.TopicFacade;
import kz.autotask.web.mapper.ResponseMapper;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(params = "id")
    public ResponseDto.TaskFull getTaskById(@RequestParam long id) {
        return taskFacade.findById(id);
    }

    @PostMapping
    public ResponseDto.TaskShort saveTask(Principal principal, @RequestBody RequestDto.TaskFull task) {
        return taskFacade.save(principal.getName(), task);
    }

    @PutMapping("/change-status")
    public ResponseDto.TaskShort changeStatus(Principal principal, @RequestBody RequestDto.TaskChangeStatus taskChangeStatus) {
        return taskFacade.changeStatus(principal.getName(), taskChangeStatus);
    }

    @GetMapping(value = "/page", params = {"pageNumber", "pageSize"})
    public ResponseDto.Page<ResponseDto.TaskShort> getPage(
            Principal principal,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return taskFacade.getTasksByUsername(principal.getName(), pageNumber, pageSize);
    }
}
