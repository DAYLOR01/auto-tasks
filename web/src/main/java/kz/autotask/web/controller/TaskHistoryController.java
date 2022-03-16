package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.TaskHistoryFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/history")
public class TaskHistoryController {

    private final TaskHistoryFacade taskHistoryFacade;

    public TaskHistoryController(TaskHistoryFacade taskHistoryFacade) {
        this.taskHistoryFacade = taskHistoryFacade;
    }

    @PostMapping("/add-commentary")
    public ResponseDto.Message addCommentary(
            Principal principal,
            @RequestBody RequestDto.TaskAddCommentary request,
            HttpServletResponse response
    ){
        try {
            taskHistoryFacade.addCommentary(principal.getName(), request);
            return ResponseDto.Message.builder().message("Ok").build();
        } catch (Exception ex) {
            response.setStatus(400);
            return ResponseDto.Message.builder().message(ex.getMessage()).build();
        }
    }

    @GetMapping
    public List<ResponseDto.TaskHistoryFull> getAllByTaskId(@RequestParam long taskId) {
        return taskHistoryFacade.findAllByTaskId(taskId);
    }
}
