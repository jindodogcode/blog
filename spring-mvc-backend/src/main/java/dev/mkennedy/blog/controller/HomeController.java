package dev.mkennedy.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.mkennedy.blog.entity.User;

@Controller
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping("/login")
    public User login(@AuthenticationPrincipal User user) {
        if (user == null) {
            System.out.println("null");
        } else {
            System.out.println(user);
        }

        return user;
    }
}
