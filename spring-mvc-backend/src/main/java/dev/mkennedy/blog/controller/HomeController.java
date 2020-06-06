package dev.mkennedy.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import dev.mkennedy.blog.entity.User;

@Controller
public class HomeController {

    @GetMapping("/api/v1/login")
    public User login(@AuthenticationPrincipal User user) {
        return user;
    }
}
