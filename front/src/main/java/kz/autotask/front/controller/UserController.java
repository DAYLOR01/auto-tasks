package kz.autotask.front.controller;

import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    public String getMe() {
        return "me";
    }
}
