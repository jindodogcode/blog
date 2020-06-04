package dev.mkennedy.blog.controller;

import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.service.UserTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepo;
    private final UserTransactionService userService;

    public UserController(UserRepository userRepo, UserTransactionService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        UserSecurity security = user.getSecurity();
        security.setRole(Role.ROLE_USER);
        User saved = userService.saveUserAndSecurity(user, security);

        return saved;
    }
}

