package kz.autotask.web.facade;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;

public interface TopicFacade {

    ResponseDto.Page<ResponseDto.TopicFull> getPage(int pageNumber, int pageSize);

    ResponseDto.TopicFull createTopic(RequestDto.TopicFull topic, String authorUsername);
}
