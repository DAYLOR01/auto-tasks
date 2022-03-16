package kz.autotask.web.controller;

import kz.autotask.web.controller.dto.ResponseDto;
import kz.autotask.web.facade.TagFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

    private final TagFacade tagFacade;

    public TagController(TagFacade tagFacade) {
        this.tagFacade = tagFacade;
    }

    @GetMapping(params = "usability")
    public List<ResponseDto.TagFull> getByUsability(@RequestParam String usability) {
        return tagFacade.findTagsByUsability(usability);
    }

    @GetMapping("/main")
    public List<ResponseDto.TagFull> getMyMainTags(Principal principal) {
        return tagFacade.getMainTagsByUsername(principal.getName());
    }
}
