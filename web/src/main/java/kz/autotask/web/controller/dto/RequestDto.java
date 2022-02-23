package kz.autotask.web.controller.dto;

public class RequestDto {

    public static class Auth {
        public String username, password;
    }

    public static class UserFull {
        public String username, password, name;
        public int mainTag;
    }
}
