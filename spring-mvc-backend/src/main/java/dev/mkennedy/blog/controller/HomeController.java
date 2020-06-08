package dev.mkennedy.blog.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.repository.UserRepository;

@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/api/v1/login")
    public User login(Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException(
                "username: " + authentication.getName() + " not found"));
        user.setLastLoggedIn(LocalDateTime.now());

        return userRepo.save(user);
    }
}
