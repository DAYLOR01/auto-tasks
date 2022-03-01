package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.TopicFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/topics")
public class TopicController {

    private final TopicFacade topicFacade;

    public TopicController(TopicFacade topicFacade) {
        this.topicFacade = topicFacade;
    }

    @GetMapping("/last10")
    public List<ResponseDto.TopicFull> getLast10() {
        return topicFacade.getLast10();
    }

    @GetMapping
    public ResponseDto.Page<ResponseDto.TopicFull> getTopicsPage(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return topicFacade.getPage(pageNumber, pageSize);
    }
}
