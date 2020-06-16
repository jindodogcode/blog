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
import dev.mkennedy.blog.service.PostService;
import dev.mkennedy.blog.service.ReplyService;
import dev.mkennedy.blog.service.UserService;
import java.io.IOException;
import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ReplyService replyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody NewUserForm userForm) {
        return userService.saveNewUser(userForm);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PatchMapping("/{username}")
    public User updateUserByUsername(@PathVariable("username") String username,
            @Valid @RequestBody UpdateUserForm userForm) {
        User user = userService.findByUsername(username);
        userForm.updateUser(user);

        return userService.save(user);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByUsername(@PathVariable("username") String username,
            HttpServletResponse response) throws IOException {
        User user = userService.findByUsername(username);
        userService.delete(user);
    }

    @PostMapping("/{username}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addUserPost(@PathVariable("username") String username,
            @Valid @RequestBody NewPostForm postForm) {
        User user = userService.findByUsername(username);
        Post post = postForm.toPost(user);

        return postService.save(post);
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
        // check that User with username exists
        userService.findByUsername(username);
        
        return postService.findAllByUsername(username, page, pageSize);
    }

    @PatchMapping("/{username}/posts/{id}")
    public Post updateUserPost(
            @PathVariable("username") String username,
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePostForm postForm) {
        // check that User with username exists
        userService.findByUsername(username);
        Post post = postService.findById(id);

        postForm.updatePost(post);
        post.setEdited(ZonedDateTime.now());

        return postService.save(post);
    }

    @PostMapping("/{username}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply addUserReply(
            @PathVariable("username") String username,
            @Valid @RequestBody NewReplyForm replyForm) {
        User user = userService.findByUsername(username);
        Post post = postService.findById(replyForm.getPostId());
        Reply repliedTo;
        Long replyId = replyForm.getReplyId();
        if (replyId != null) {
            repliedTo = replyService.findById(replyId);
        } else {
            repliedTo = null;
        }

        Reply reply = replyForm.toReply(user);
        reply.setPost(post);
        reply.setReply(repliedTo);

        return replyService.save(reply);
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
        // check that User with username exists
        userService.findByUsername(username);

        return replyService.findAllByUsername(username, page, pageSize);
    }

    @PatchMapping("/{username}/replies/{id}")
    public Reply updateUserReply(
            @PathVariable("username") String username,
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateReplyForm replyForm) {
        // check that User with username exists
        userService.findByUsername(username);
        Reply reply = replyService.findById(id);

        replyForm.updateReply(reply);
        reply.setEdited(ZonedDateTime.now());

        return replyService.save(reply);
    }
}

