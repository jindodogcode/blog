package dev.mkennedy.blog.controller;

import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.repository.UserRepository;

@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/api/v1/login")
    public User login(Authentication authentication) {
        // System.out.println(authentication);
        // if (authentication == null) {
        //     throw new UnauthorizedException();
        // }
        User user = userRepo.findByUsername(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException(
                "username: " + authentication.getName() + " not found"));
        user.setLastLoggedIn(ZonedDateTime.now());

        return userRepo.save(user);
    }

    @GetMapping("/api/v1/me")
    public User me(Authentication authentication) {
        System.out.println(authentication);
        if (authentication == null) {
            throw new UnauthorizedException();
        }
        User user = userRepo.findByUsername(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException(
                "username: " + authentication.getName() + " not found"));
        user.setLastLoggedIn(ZonedDateTime.now());

        return userRepo.save(user);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
