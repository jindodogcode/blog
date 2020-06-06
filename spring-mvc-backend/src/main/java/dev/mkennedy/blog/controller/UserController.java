package dev.mkennedy.blog.controller;

import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;
import dev.mkennedy.blog.repository.PostRepository;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.service.UserTransactionService;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepo;
    private final UserTransactionService userService;
    private final PostRepository postRepo;

    public UserController(
            UserRepository userRepo,
            UserTransactionService userService,
            PostRepository postRepo) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.postRepo = postRepo;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        UserSecurity security = user.getSecurity();
        security.setRole(Role.ROLE_USER);
        User saved = userService.saveUserAndSecurity(user, security);

        return saved;
    }

    @GetMapping
    public Iterable<User> searchUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());

        return user;
    }

    @GetMapping("/{username}/posts")
    public Iterable<Post> getUserPosts(@PathVariable("username") String username) {
        return postRepo.findAllByUsername(username);
    }
}

