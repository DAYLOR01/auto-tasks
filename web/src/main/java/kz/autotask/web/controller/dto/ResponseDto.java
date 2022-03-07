package kz.autotask.web.controller.dto;

import kz.autotask.web.data.entity.enums.TagUsability;
import kz.autotask.web.data.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

public class ResponseDto {

    @Getter @Setter @Builder
    public static class Message {
        private String message;
    }

    @Getter @Setter @Builder
    public static class Auth {
        private String token;
    }

    @Getter @Setter @Builder
    public static class UserShort {
        private long id;
        private String username, name;
        private boolean isActive;
    }

    @Getter @Setter @Builder
    public static class UserFull{
        private long id;
        private String username, name;
        private boolean isActive;
        @Singular private List<TagFull> tags;
        @Singular private List<RoleFull> roles;
    }

    @Getter @Setter @Builder
    public static class TagFull {
        private int id;
        private String name, descriptionRU, usability;
    }

    @Getter @Setter @Builder
    public static class RoleFull {
        private int id;
        private String name, descriptionRU;
        private int userPriority;
    }

    @Getter @Setter @Builder
    public static class TopicFull {
        private long id;
        private UserShort author;
        private String header, content;
    }

    @Getter @Setter @Builder
    public static class TaskShort {
        private long id;
        private String header;
        private UserShort assignedUser;
        private TaskStatus status;
        @Singular private List<TagFull> tags;
    }

    @Getter @Setter @Builder
    public static class Page<T> {
        private int pageSize, pageNumber, totalPages;
        private long totalElements;
        @Singular private List<T> elements;
    }
}
