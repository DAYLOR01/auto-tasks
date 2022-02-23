package kz.autotask.web.controller.dto;

public class ResponseDto {

    public static class Auth {
        public String token;
    }

    public static class UserShort {
        public String username, name;
        public boolean isActive;
        // TODO
    }
}
