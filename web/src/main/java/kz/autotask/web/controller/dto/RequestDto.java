package kz.autotask.web.controller.dto;

import kz.autotask.web.data.entity.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class RequestDto {

    @Getter @Setter @NoArgsConstructor
    public static class Auth {
        private String username, password;
    }

    @Getter @Setter @NoArgsConstructor
    public static class UserFull {
        private String username, password, name;
        private Integer[] tags;
        private Integer[] roles;
    }

    @Getter @Setter @NoArgsConstructor
    public static class TopicFull {
        private String header, content;
    }

    @Getter @Setter @NoArgsConstructor
    public static class UserChangePassword {
        private String oldPassword, newPassword;
    }

    @Getter @Setter @NoArgsConstructor
    public static class UserChangeActive {
        private String username;
        private boolean isActive;
    }

    @Getter @Setter @NoArgsConstructor
    public static class TaskFull {
        private String header;
        private String text;
        private boolean autoAssignUser;
        private Integer[] assignedUserTagIds;
        private int assignedUserRole;
        private String assignedUser;
        private Integer[] tagIds;
        private LocalDate inspirationDate;
    }

    @Getter @Setter @NoArgsConstructor
    public static class TaskChangeStatus {
        private long taskId;
        private TaskStatus newStatus;
    }

    @Getter @Setter @NoArgsConstructor
    public static class TaskAddCommentary {
        private long taskId;
        private String commentary;
    }
}
