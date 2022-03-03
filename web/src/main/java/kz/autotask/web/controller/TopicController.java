package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.RequestDto;
import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.TopicFacade;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/topics")
public class TopicController {

    private final TopicFacade topicFacade;

    public TopicController(TopicFacade topicFacade) {
        this.topicFacade = topicFacade;
    }

    @GetMapping
    public ResponseDto.Page<ResponseDto.TopicFull> getTopicsPage(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return topicFacade.getPage(pageNumber, pageSize);
    }

    @PostMapping
    public ResponseDto.TopicFull createTopic(@RequestBody RequestDto.TopicFull topic, Principal principal) {
        return topicFacade.createTopic(topic, principal.getName());
    }
}
