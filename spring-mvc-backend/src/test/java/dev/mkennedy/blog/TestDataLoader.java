package dev.mkennedy.blog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;
import dev.mkennedy.blog.service.PostService;
import dev.mkennedy.blog.service.ReplyService;
import dev.mkennedy.blog.service.UserService;

@Component
@Profile({ "posttest", "usertest", "replytest" })
public class TestDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing users");

        List<User> users = Arrays.asList(
            new User("userone@example.com", "userone", "User", "One", "First user"),
            new User("usertwo@example.com", "usertwo", "User", "Two", "Second user"),
            new User("deleteuser@example.com", "deleteuser", "Delete", "User", "Delete user"),
            new User("updateuser@example.com", "updateuser", "Update", "User", "Update user")
        );

        users.stream().map(user -> userService.saveUserAndSecurity(
                    user, new UserSecurity(passwordEncoder.encode("password"), Role.ROLE_USER)))
            .forEach(user -> logPersist("User", user));
        User userOne = userService.findByUsername("userone");
        User userTwo = userService.findByUsername("usertwo");

        log.info("Initializing posts");

        List<Post> posts = Arrays.asList(
                new Post("I had a great day", "it was really good", userOne),
                new Post("Something", "Something something something.", userOne));
        posts = posts.stream()
            .map(postService::save)
            .peek(post -> logPersist("Post", post))
            .collect(Collectors.toList());

        log.info("Initializing Replies");

        Post userOnePost = posts.get(0);
        Reply userTwoReply = new Reply("Something something something", userTwo);
        userTwoReply.setPost(userOnePost);
        userTwoReply = replyService.save(userTwoReply);
        logPersist("Reply", userTwoReply);
        Reply userOneReply = new Reply("Interesting", userOne);
        userOneReply.setPost(userOnePost);
        userOneReply.setReply(userTwoReply);
        userOneReply = replyService.save(userOneReply);
    }

    private void logPersist(String itemType, Object item) {
        log.info('\n' + itemType + " created:\n" + item);
    }
}
