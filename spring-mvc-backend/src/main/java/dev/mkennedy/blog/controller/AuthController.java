package dev.mkennedy.blog.controller;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public User login(Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException(
                "username: " + authentication.getName() + " not found"));
        user.setLastLoggedIn(ZonedDateTime.now());

        return userRepo.save(user);
    }

    @GetMapping("/me")
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
