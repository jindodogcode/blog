package dev.mkennedy.blog.controller;

import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.model.NewPostForm;
import dev.mkennedy.blog.model.NewUserForm;
import dev.mkennedy.blog.model.UpdateUserForm;
import dev.mkennedy.blog.repository.PostRepository;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.service.UserTransactionService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserTransactionService userService;
    @Autowired
    private PostRepository postRepo;

    @PostMapping
    public User addUser(@Valid @RequestBody NewUserForm userForm) {
        User saved = userService.saveNewUser(userForm);

        return saved;
    }

    @GetMapping
    public Iterable<User> searchUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException());

        return user;
    }

    @PatchMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username,
            @Valid @RequestBody UpdateUserForm userForm) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("No user with name: " + username + " found"));
        user.setEmail(userForm.getEmail());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        return userRepo.save(user);
    }

    @PostMapping("/{username}/posts")
    public Post addUserPost(@PathVariable("username") String username,
            @Valid @RequestBody NewPostForm postForm) {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username + "not found"));
        Post post = postForm.toPost(user);
        Post saved = postRepo.save(post);

        return saved;
    }

    @GetMapping("/{username}/posts")
    public Iterable<Post> getUserPosts(
            @PathVariable("username") String username) {
        return postRepo.findAllByUsername(username);
    }
}

