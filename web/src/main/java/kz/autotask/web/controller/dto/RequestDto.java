package kz.autotask.web.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

public class RequestDto {

    @Getter @Setter @NoArgsConstructor
    public static class Auth {
        private String username, password;
    }

    @Getter @Setter @NoArgsConstructor
    public static class UserFull {
        private String username, password, name;
        private Set<Integer> tags;
    }

    @Getter @Setter @NoArgsConstructor
    public static class TopicFull {
        private ResponseDto.UserShort author;
        private String header, content;
    }
}
