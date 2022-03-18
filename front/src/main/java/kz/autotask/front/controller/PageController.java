package kz.autotask.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PageController {

    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("home");
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/me")
    public String me() {
        return "me";
    }

    @GetMapping("/task-board")
    public String taskBoard() {
        return "task-board";
    }

    @GetMapping(value = "/tasks", params = "id")
    public String task(@RequestParam long id, Model model) {
        model.addAttribute("taskId", id);
        return "task";
    }

    @GetMapping("/tasks")
    public String taskList() {
        return "task-list";
    }

    @GetMapping("/tasks/new")
    public String newTask() {
        return "new-task";
    }

    @GetMapping("/users")
    public String userManagement() {
        return "user-management";
    }

    @GetMapping(value = "/users/{username}")
    public String getUserInfo(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "user-info";
    }
}
