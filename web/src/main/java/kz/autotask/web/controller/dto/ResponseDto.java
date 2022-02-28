package kz.autotask.web.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResponseDto {

    @Getter @Setter @Builder
    public static class Auth {
        private String token;
    }

    @Getter @Setter @Builder
    public static class UserShort {
        private String username, name;
        private boolean isActive;
    }

    @Getter @Setter @Builder
    public static class UserFull{
        private String username, name;
        private boolean isActive;
        private List<TagFull> tags;
        private List<RoleFull> roles;
    }

    @Getter @Setter @Builder
    public static class TagFull {
        private int id;
        private String name;
    }

    @Getter @Setter @Builder
    public static class RoleFull {
        private int id;
        private String name;
        private int userPriority;
    }

    @Getter @Setter @Builder
    public static class TopicFull {
        private long id;
        private UserShort author;
        private String header, content;
    }
}
