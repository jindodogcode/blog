package dev.mkennedy.blog.controller;

import dev.mkennedy.blog.PagingDefaults;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.model.NewPostForm;
import dev.mkennedy.blog.model.NewReplyForm;
import dev.mkennedy.blog.model.NewUserForm;
import dev.mkennedy.blog.model.UpdatePostForm;
import dev.mkennedy.blog.model.UpdateReplyForm;
import dev.mkennedy.blog.model.UpdateUserForm;
import dev.mkennedy.blog.repository.PostRepository;
import dev.mkennedy.blog.repository.ReplyRepository;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.service.UserTransactionService;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @Autowired
    private ReplyRepository replyRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public User updateUserByUsername(@PathVariable("username") String username,
            @Valid @RequestBody UpdateUserForm userForm) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> usernameNotFoundExceptionBuilder(username));

        userForm.updateUser(user);

        return userRepo.save(user);
    }

    @DeleteMapping("/{username}")
    public void deleteUserByUsername(@PathVariable("username") String username,
            HttpServletResponse response) throws IOException {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> usernameNotFoundExceptionBuilder(username));
        userService.delete(user);

        response.sendRedirect("/api/v1/logout");
    }

    @PostMapping("/{username}/posts")
    public Post addUserPost(@PathVariable("username") String username,
            @Valid @RequestBody NewPostForm postForm) {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> usernameNotFoundExceptionBuilder(username));
        Post post = postForm.toPost(user);
        Post saved = postRepo.save(post);

        return saved;
    }

    @GetMapping("/{username}/posts")
    public Page<Post> getUserPosts(
            @PathVariable("username") String username,
            @RequestParam(
                value = "page",
                required = false,
                defaultValue = PagingDefaults.PAGE
            ) int page,
            @RequestParam(
                value = "pagesize",
                required = false,
                defaultValue = PagingDefaults.PAGESIZE
            ) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return postRepo.findAllByUsername(username, pageable);
    }

    @PatchMapping("/{username}/posts/{id}")
    public Post updateUserPost(@PathVariable("username") String username,
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePostForm postForm) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> usernameNotFoundExceptionBuilder(username));
        Post post = postRepo.findById(id)
            .orElseThrow(() -> entityNotFoundExceptionBuilder(Post.class, id));

        if (!post.getUser().equals(user)) {
            throw new AuthorizationServiceException("not authorized to edit that post");
        }

        postForm.updatePost(post);
        post.setEdited(LocalDateTime.now());

        return postRepo.save(post);
    }

    @PostMapping("/{username}/replies")
    public Reply addUserReply(
            @PathVariable("username") String username,
            @Valid @RequestBody NewReplyForm replyForm) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> usernameNotFoundExceptionBuilder(username));
        Post post = postRepo.findById(replyForm.getPostId())
            .orElseThrow(() -> entityNotFoundExceptionBuilder(Post.class, replyForm.getPostId()));
        Reply repliedTo;
        Long replyId = replyForm.getReplyId();
        if (replyId != null) {
            repliedTo = replyRepo.findById(replyId)
                .orElseThrow(() -> entityNotFoundExceptionBuilder(Reply.class, replyId));
        } else {
            repliedTo = null;
        }

        Reply reply = replyForm.toReply(user);
        reply.setPost(post);
        reply.setReply(repliedTo);

        return replyRepo.save(reply);
    }

    @GetMapping("/{username}/replies")
    public Page<Reply> getUserReplies(
            @PathVariable("username") String username,
            @RequestParam(
                value = "page",
                required = false,
                defaultValue = PagingDefaults.PAGE
            ) int page,
            @RequestParam(
                value = "pagesize",
                required = false,
                defaultValue = PagingDefaults.PAGESIZE
            ) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return replyRepo.findByUsername(username, pageable);
    }

    @PatchMapping("/{username}/replies/{id}")
    public Reply updateUserReply(
            @PathVariable("username") String username,
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateReplyForm replyForm) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> usernameNotFoundExceptionBuilder(username));
        Reply reply = replyRepo.findById(id)
            .orElseThrow(() -> entityNotFoundExceptionBuilder(Reply.class, id));

        if (!reply.getUser().equals(user)) {
            throw new AuthorizationServiceException("not authorized to edit that reply");
        }

        replyForm.updateReply(reply);
        reply.setEdited(LocalDateTime.now());

        return replyRepo.save(reply);
    }

    private UsernameNotFoundException usernameNotFoundExceptionBuilder(String username) {
        return new UsernameNotFoundException("username " + username + " not found");
    }

    private EntityNotFoundException entityNotFoundExceptionBuilder(Class<?> clazz, Object id) {
        return new EntityNotFoundException(
            "no entity of type " + clazz.getSimpleName() + " with id " + id + " found");
    }
}

