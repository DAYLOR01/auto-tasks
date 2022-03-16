package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;

import java.util.List;

public interface TaskHistoryFacade {

    void addCommentary(String username, RequestDto.TaskAddCommentary commentary);

    List<ResponseDto.TaskHistoryFull> findAllByTaskId(long taskId);

}
