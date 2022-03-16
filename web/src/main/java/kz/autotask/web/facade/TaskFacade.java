package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;

import java.util.List;

public interface TaskFacade {

    List<ResponseDto.TaskShort> findAllByAssignedUserAndStatus(String username, String status);

    List<ResponseDto.TaskShort> findAllByAssignedUserAndStatusCompletedMonthAgo(String username, String status);

    ResponseDto.TaskFull findById(long id);

    ResponseDto.TaskShort save(String authorUsername, RequestDto.TaskFull task);

    ResponseDto.TaskShort changeStatus(String username, RequestDto.TaskChangeStatus taskChangeStatus);
}
